package com.jiaye.cashloan.view.home;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.home.CheckCompany;
import com.jiaye.cashloan.http.data.loan.Loan;
import com.jiaye.cashloan.persistence.User;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.home.source.HomeDataSource;

import org.reactivestreams.Publisher;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * HomePresenter
 *
 * @author 贾博瑄
 */

public class HomePresenter extends BasePresenterImpl implements HomeContract.Presenter {

    private final HomeContract.View mView;

    private final HomeDataSource mDataSource;

    public HomePresenter(HomeContract.View view, HomeDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void loan() {
        Disposable disposable = mDataSource.queryUser()
                .concatMap((Function<User, Publisher<EmptyResponse>>) user -> mDataSource.checkLoan())
                .concatMap((Function<EmptyResponse, Publisher<Loan>>) emptyResponse -> mDataSource.requestLoan())
                .concatMap((Function<Loan, Publisher<EmptyResponse>>) loan -> mDataSource.uploadRiskAppList())
                .concatMap((Function<EmptyResponse, Publisher<CheckCompany>>) o -> mDataSource.checkCompany())
                .compose(new ViewTransformer<CheckCompany>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(checkCompany -> {
                    mView.dismissProgressDialog();
                    if (checkCompany.isNeed()) {
                        //mView.showCompanyView();
                    } else {
                        mView.showCertificationView();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
