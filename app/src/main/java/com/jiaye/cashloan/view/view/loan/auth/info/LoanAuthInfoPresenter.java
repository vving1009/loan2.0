package com.jiaye.cashloan.view.view.loan.auth.info;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.LoanInfoAuth;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.auth.source.info.LoanAuthInfoDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * LoanAuthInfoPresenter
 *
 * @author 贾博瑄
 */

public class LoanAuthInfoPresenter extends BasePresenterImpl implements LoanAuthInfoContract.Presenter {

    private final LoanAuthInfoContract.View mView;

    private final LoanAuthInfoDataSource mDataSource;

    public LoanAuthInfoPresenter(LoanAuthInfoContract.View view, LoanAuthInfoDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.requestLoanInfoAuth()
                .compose(new ViewTransformer<LoanInfoAuth>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<LoanInfoAuth>() {
                    @Override
                    public void accept(LoanInfoAuth loanInfoAuth) throws Exception {
                        String submit = LoanApplication.getInstance().getResources()
                                .getString(R.string.loan_auth_submit);
                        String unSubmit = LoanApplication.getInstance().getResources()
                                .getString(R.string.loan_auth_un_submit);
                        mView.dismissProgressDialog();
                        mView.setPerson(loanInfoAuth.getPerson() == 1 ? submit : unSubmit);
                        mView.setContact(loanInfoAuth.getContact() == 1 ? submit : unSubmit);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
