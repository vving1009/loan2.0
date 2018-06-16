package com.jiaye.cashloan.view.plan;

import com.jiaye.cashloan.http.data.plan.Plan;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.plan.source.PlanDataSource;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * PlanPresenter
 *
 * @author 贾博瑄
 */

public class PlanPresenter extends BasePresenterImpl implements PlanContract.Presenter {

    private PlanContract.View mView;

    private PlanDataSource mDataSource;

    public PlanPresenter(PlanContract.View view, PlanDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.plan()
                .compose(new ViewTransformer<List<Plan.Details>>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(details -> {
                    mView.dismissProgressDialog();
                    mView.setPlans(details);
                });
        mCompositeDisposable.add(disposable);
    }
}
