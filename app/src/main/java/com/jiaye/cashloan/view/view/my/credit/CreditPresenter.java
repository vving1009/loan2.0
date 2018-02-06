package com.jiaye.cashloan.view.view.my.credit;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.my.CreditBalance;
import com.jiaye.cashloan.http.data.my.CreditPasswordRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordResetRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordStatus;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.my.credit.source.CreditDataSource;

import org.reactivestreams.Publisher;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * CreditPresenter
 *
 * @author 贾博瑄
 */

public class CreditPresenter extends BasePresenterImpl implements CreditContract.Presenter {

    private final CreditContract.View mView;

    private final CreditDataSource mDataSource;

    private String mPasswordStatus = "0";

    private CreditBalance mBalance;

    public CreditPresenter(CreditContract.View view, CreditDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.passwordStatus()
                .compose(new ViewTransformer<CreditPasswordStatus>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .map(new Function<CreditPasswordStatus, CreditPasswordStatus>() {
                    @Override
                    public CreditPasswordStatus apply(CreditPasswordStatus creditPasswordStatus) throws Exception {
                        if (creditPasswordStatus.getOpen().equals("0")) {
                            mPasswordStatus = "0";
                            mView.setPasswordText(LoanApplication.getInstance().getResources().getString(R.string.my_credit_password));
                        } else if (creditPasswordStatus.getOpen().equals("1")) {
                            if (creditPasswordStatus.getStatus().equals("0")) {
                                mPasswordStatus = "0";
                                mView.setPasswordText(LoanApplication.getInstance().getResources().getString(R.string.my_credit_password));
                            } else if (creditPasswordStatus.getStatus().equals("1")) {
                                mPasswordStatus = "1";
                                mView.setPasswordText(LoanApplication.getInstance().getResources().getString(R.string.my_credit_password_reset));
                            }
                        }
                        return creditPasswordStatus;
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<CreditPasswordStatus, Publisher<CreditBalance>>() {
                    @Override
                    public Publisher<CreditBalance> apply(CreditPasswordStatus creditPasswordStatus) throws Exception {
                        return mDataSource.balance();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CreditBalance>() {
                    @Override
                    public void accept(CreditBalance creditBalance) throws Exception {
                        mBalance = creditBalance;
                        mView.dismissProgressDialog();
                        mView.showBalance(creditBalance.getAvailBal());
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void password() {
        switch (mPasswordStatus) {
            case "0":
                passwordInit();
                break;
            case "1":
                passwordReset();
                break;
        }
    }

    @Override
    public void cash() {
        if (mBalance != null && mBalance.getIsSupportCash().equals("1")) {
            mView.showCashView(mBalance);
        } else {
            mView.showToastById(R.string.error_my_credit);
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
                .subscribe(new Consumer<CreditPasswordRequest>() {
                    @Override
                    public void accept(CreditPasswordRequest request) throws Exception {
                        mView.dismissProgressDialog();
                        mView.showPasswordView(request);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    private void passwordReset() {
        Disposable disposable = mDataSource.passwordReset()
                .compose(new ViewTransformer<CreditPasswordResetRequest>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<CreditPasswordResetRequest>() {
                    @Override
                    public void accept(CreditPasswordResetRequest request) throws Exception {
                        mView.dismissProgressDialog();
                        mView.showPasswordResetView(request);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
