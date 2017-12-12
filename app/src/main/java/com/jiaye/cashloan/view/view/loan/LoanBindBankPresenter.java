package com.jiaye.cashloan.view.view.loan;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.LoanBindBank;
import com.jiaye.cashloan.http.data.loan.LoanBindBankRequest;
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
    public void submit() {
        if (TextUtils.isEmpty(mView.getPhone()) || !mView.getPhone().matches(RegexUtil.phone())) {
            mView.showToastById(R.string.error_loan_bank_phone);
        } else if (TextUtils.isEmpty(mView.getBank())) {
            mView.showToastById(R.string.error_loan_bank_bank);
        } else if (TextUtils.isEmpty(mView.getNumber()) || mView.getNumber().length() != 16) {
            mView.showToastById(R.string.error_loan_bank_number);
        } else {
            LoanBindBankRequest request = new LoanBindBankRequest();
            request.setPhone(mView.getPhone());
            request.setBank(mView.getBank());
            request.setNumber(mView.getNumber());
            Disposable disposable = mDataSource.requestBindBank(request)
                    .compose(new ViewTransformer<LoanBindBank>() {
                        @Override
                        public void accept() {
                            super.accept();
                            mView.showProgressDialog();
                        }
                    })
                    .subscribe(new Consumer<LoanBindBank>() {
                        @Override
                        public void accept(LoanBindBank loanBindBank) throws Exception {
                            mView.dismissProgressDialog();
                            mView.result();
                        }
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        }
    }
}
