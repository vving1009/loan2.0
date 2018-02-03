package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.http.data.loan.LoanPlan;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.source.LoanPlanDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * LoanPlanPresenter
 *
 * @author 贾博瑄
 */

public class LoanPlanPresenter extends BasePresenterImpl implements LoanPlanContract.Presenter {

    private LoanPlanContract.View mView;

    private LoanPlanDataSource mDataSource;

    public LoanPlanPresenter(LoanPlanContract.View view, LoanPlanDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
    }

    @Override
    public void requestLoanPlan(String loanId) {
        Disposable disposable = mDataSource.requestLoanPlan(loanId)
                .compose(new ViewTransformer<LoanPlan.Plan[]>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<LoanPlan.Plan[]>() {
                    @Override
                    public void accept(LoanPlan.Plan[] plans) throws Exception {
                        mView.dismissProgressDialog();
                        mView.setPlans(plans);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
