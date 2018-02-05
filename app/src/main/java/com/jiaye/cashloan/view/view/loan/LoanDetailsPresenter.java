package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.http.data.loan.LoanApply;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.source.LoanDetailsDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * LoanDetailsPresenter
 *
 * @author 贾博瑄
 */

public class LoanDetailsPresenter extends BasePresenterImpl implements LoanDetailsContract.Presenter {

    private final LoanDetailsContract.View mView;

    private final LoanDetailsDataSource mDataSource;

    public LoanDetailsPresenter(LoanDetailsContract.View view, LoanDetailsDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void requestDetails(String state) {
        Disposable disposable = mDataSource.requestLoanApply(state)
                .compose(new ViewTransformer<LoanApply>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<LoanApply>() {
                    @Override
                    public void accept(LoanApply loanApply) throws Exception {
                        mView.setList(loanApply.getCards());
                        mView.dismissProgressDialog();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
