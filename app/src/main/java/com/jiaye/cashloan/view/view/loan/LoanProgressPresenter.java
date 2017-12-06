package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.http.data.loan.LoanProgress;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.source.LoanProgressDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * LoanProgressPresenter
 *
 * @author 贾博瑄
 */

public class LoanProgressPresenter extends BasePresenterImpl implements LoanProgressContract.Presenter {

    private final LoanProgressContract.View mView;

    private final LoanProgressDataSource mDataSource;

    public LoanProgressPresenter(LoanProgressContract.View view, LoanProgressDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void requestLoanProgress(String loanId) {
        Disposable disposable = mDataSource.requestLoanProgress(loanId)
                .compose(new ViewTransformer<LoanProgress>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<LoanProgress>() {
                    @Override
                    public void accept(LoanProgress loanProgress) throws Exception {
                        mView.setList(loanProgress.getList());
                        mView.dismissProgressDialog();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
