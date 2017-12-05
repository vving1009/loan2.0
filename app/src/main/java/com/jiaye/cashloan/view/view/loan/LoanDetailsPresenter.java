package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.http.data.loan.LoanDetails;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.source.LoanDetailsDataSource;

import java.util.ArrayList;
import java.util.List;

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
    public void requestDetails() {
        Disposable disposable = mDataSource.requestLoanDetails()
                .compose(new ViewTransformer<LoanDetails>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<LoanDetails>() {
                    @Override
                    public void accept(LoanDetails loanDetails) throws Exception {
                        List<LoanDetails> list = new ArrayList<>();
                        list.add(loanDetails);
                        mView.setList(list);
                        mView.dismissProgressDialog();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
