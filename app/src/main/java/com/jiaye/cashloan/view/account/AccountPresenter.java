package com.jiaye.cashloan.view.account;

import android.text.TextUtils;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.my.CreditBalance;
import com.jiaye.cashloan.http.data.my.CreditInfo;
import com.jiaye.cashloan.http.data.my.CreditPasswordRequest;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.account.source.CreditDataSource;

import org.reactivestreams.Publisher;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * AccountPresenter
 *
 * @author 贾博瑄
 */

public class AccountPresenter extends BasePresenterImpl implements AccountContract.Presenter {

    private final AccountContract.View mView;

    private final CreditDataSource mDataSource;

    private CreditBalance mBalance;

    /**
     * -1：未开户
     * 0：已开户，未设密码
     * 1：已开户，已设密码
     */
    private String mPasswordStatus;

    /**
     * 是否绑定银行卡
     */
    private boolean isBindCard;

    /**
     * 是否授权
     */
    private boolean isAuth;

    public AccountPresenter(AccountContract.View view, CreditDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void init() {
        Disposable disposable = mDataSource.creditInfo()
                .compose(new ViewTransformer<CreditInfo>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .doOnNext(creditInfo -> {
                    switch (creditInfo.getBankStatus()) {
                        case "01": // 未开户
                            mPasswordStatus = "-1";
                            mView.setPasswordText(LoanApplication.getInstance().getResources().getString(R.string.my_credit_password));
                            break;
                        case "02": // 已开户
                        case "03": // 已开户，绑卡
                            if (creditInfo.getPwdStatus().equals("0")) {// 初始密码
                                mPasswordStatus = "0";
                                mView.setPasswordText(LoanApplication.getInstance().getResources().getString(R.string.my_credit_password));
                            } else if (creditInfo.getPwdStatus().equals("1")) {// 重置密码
                                mPasswordStatus = "1";
                                mView.setPasswordText(LoanApplication.getInstance().getResources().getString(R.string.my_credit_password_reset));
                            }
                            if (creditInfo.getBankStatus().equals("03")) {
                                isBindCard = true;  //已绑卡
                            }
                            if (!TextUtils.isEmpty(creditInfo.getPaymentDeadline()) &&
                                    !TextUtils.isEmpty(creditInfo.getPaymentMaxamt()) &&
                                    !TextUtils.isEmpty(creditInfo.getRepaymentDeadline()) &&
                                    !TextUtils.isEmpty(creditInfo.getRepaymentMaxamt())) {
                                isAuth = true;  //已授权
                            }
                            break;
                    }
                })
                // 过滤未开户的情况
                .filter(creditInfo -> !creditInfo.getBankStatus().equals("01"))
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // 过滤accountId为空的情况
                .filter(creditInfo -> creditInfo != null && !TextUtils.isEmpty(creditInfo.getAccountId()))
                .doOnNext(creditInfo -> {
                    mView.showAccountId(creditInfo.getAccountId());
                })
                // 过滤未开户和开户未绑卡的情况
                .filter(creditInfo -> creditInfo.getBankStatus().equals("03"))
                // 上传银行卡信息给Satcatche
                .observeOn(Schedulers.io())
                .flatMap((Function<CreditInfo, Publisher<EmptyResponse>>) mDataSource::saveBankCard)
                // 查询余额
                .flatMap((Function<EmptyResponse, Publisher<CreditBalance>>) creditInfo -> mDataSource.balance())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(balance -> {
                    mView.dismissProgressDialog();
                    mBalance = balance;
                    mView.showBalance(balance.getAvailBal());
                }, new ThrowableConsumer(mView), mView::dismissProgressDialog);
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void account() {
        Disposable disposable = mDataSource.creditInfo()
                .compose(new ViewTransformer<CreditInfo>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(creditInfo -> {
                    mView.dismissProgressDialog();
                    //01-未开户;02-已开户未绑卡;03-已开户已绑卡
                    switch (creditInfo.getBankStatus()) {
                        case "01":
                            mView.showBindBankView();
                            break;
                        default:
                            mView.showToastById(R.string.my_credit_account_error);
                            break;
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void password() {
        if (!TextUtils.isEmpty(mPasswordStatus)) {
            switch (mPasswordStatus) {
                case "-1":
                    mView.showToastById(R.string.my_credit_password_error);
                    break;
                case "0":
                    passwordInit();
                    break;
                case "1":
                    if (isBindCard) {
                        mView.showPasswordResetView();
                    } else {
                        mView.showToastById(R.string.my_credit_no_bind_card);
                    }
                    break;
            }
        }
    }

    @Override
    public void auth() {
        if (!TextUtils.isEmpty(mPasswordStatus)) {
            switch (mPasswordStatus) {
                case "-1":
                    mView.showToastById(R.string.my_credit_password_error);
                    break;
                case "0":
                    mView.showToastById(R.string.my_credit_no_password);
                    break;
                case "1":
                    mView.showAuthView();
                    break;
            }
        }
    }

    @Override
    public void cash() {
        if (isAuth) {
            if (mBalance != null && mBalance.getIsSupportCash().equals("1") && mPasswordStatus.equals("1")) {
                mView.showCashView(mBalance);
            } else {
                mView.showToastById(R.string.error_my_credit);
            }
        } else {
            mView.showToastById(R.string.error_my_credit_no_auth);
        }
    }

    @Override
    public void bank() {
        if (mPasswordStatus.equals("-1")) {
            mView.showOpenDialog();
        } else {
            mView.showBankView();
        }
    }

    private void passwordInit() {
        Disposable disposable = mDataSource.password()
                .compose(new ViewTransformer<CreditPasswordRequest>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(request -> {
                    mView.dismissProgressDialog();
                    mView.showPasswordView(request);
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
