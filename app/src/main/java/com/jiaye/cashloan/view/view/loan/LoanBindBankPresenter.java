package com.jiaye.cashloan.view.view.loan;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.LoanBindBankRequest;
import com.jiaye.cashloan.http.data.loan.LoanOpenSMS;
import com.jiaye.cashloan.http.data.loan.LoanOpenSMSRequest;
import com.jiaye.cashloan.utils.RegexUtil;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.source.LoanBindBankDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * LoanBindBankPresenter
 *
 * @author 贾博瑄
 */

public class LoanBindBankPresenter extends BasePresenterImpl implements LoanBindBankContract.Presenter {

    private final LoanBindBankContract.View mView;

    private final LoanBindBankDataSource mDataSource;

    private String mSource;

    public LoanBindBankPresenter(LoanBindBankContract.View view, LoanBindBankDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.queryName()
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String name) throws Exception {
                        int l = name.length();
                        if (l > 1) {
                            StringBuilder s = new StringBuilder();
                            for (int i = 0; i < l - 1; i++) {
                                s.append("*");
                            }
                            name = s + name.substring(s.length());
                        }
                        return name;
                    }
                })
                .compose(new ViewTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String name) throws Exception {
                        mView.setName(name);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void setSource(String source) {
        mSource = source;
    }

    @Override
    public void requestSMS() {
        if (TextUtils.isEmpty(mView.getPhone()) || !mView.getPhone().matches(RegexUtil.phone())) {
            mView.showToastById(R.string.error_loan_bank_phone);
        } else if (TextUtils.isEmpty(mView.getNumber()) || mView.getNumber().length() > 19) {
            mView.showToastById(R.string.error_loan_bank_number);
        } else {
            LoanOpenSMSRequest request = new LoanOpenSMSRequest();
            request.setPhone(mView.getPhone());
            request.setCard(mView.getNumber());
            // 如果没有传source代表是重新绑卡
            if (TextUtils.isEmpty(mSource)) {
                request.setCode("cardBindPlus");
            } else {
                request.setCode("accountOpenPlus");
            }
            Disposable disposable = mDataSource.requestBindBankSMS(request)
                    .compose(new ViewTransformer<LoanOpenSMS>())
                    .subscribe(new Consumer<LoanOpenSMS>() {
                        @Override
                        public void accept(LoanOpenSMS loanBindBankSMS) throws Exception {
                            mView.startCountDown();
                        }
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        }
    }

    @Override
    public void submit() {
        if (TextUtils.isEmpty(mView.getPhone()) || !mView.getPhone().matches(RegexUtil.phone())) {
            mView.showToastById(R.string.error_loan_bank_phone);
        } else if (TextUtils.isEmpty(mView.getBank())) {
            mView.showToastById(R.string.error_loan_bank_bank);
        } else if (TextUtils.isEmpty(mView.getNumber()) || mView.getNumber().length() > 19) {
            mView.showToastById(R.string.error_loan_bank_number);
        } else if (TextUtils.isEmpty(mView.getSMS())) {
            mView.showToastById(R.string.error_loan_bank_sms);
        } else {
            LoanBindBankRequest bankRequest = new LoanBindBankRequest();
            bankRequest.setPhone(mView.getPhone());
            bankRequest.setBank(mView.getBank());
            bankRequest.setNumber(mView.getNumber());
            bankRequest.setSms(mView.getSMS());
            if (!TextUtils.isEmpty(mSource)) {
                bankRequest.setSource(mSource);
                Disposable disposable = mDataSource.requestBindBank(bankRequest)
                        .compose(new ViewTransformer<Object>() {
                            @Override
                            public void accept() {
                                super.accept();
                                mView.showProgressDialog();
                            }
                        })
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                mView.dismissProgressDialog();
                                mView.complete();
                            }
                        }, new ThrowableConsumer(mView));
                mCompositeDisposable.add(disposable);
            } else {
                Disposable disposable = mDataSource.requestBindBankAgain(bankRequest)
                        .compose(new ViewTransformer<Object>() {
                            @Override
                            public void accept() {
                                super.accept();
                                mView.showProgressDialog();
                            }
                        })
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                mView.dismissProgressDialog();
                                mView.result();
                            }
                        }, new ThrowableConsumer(mView));
                mCompositeDisposable.add(disposable);
            }
        }
    }
}
