package com.jiaye.cashloan.view.loancredit.startpage;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.loan.Loan;
import com.jiaye.cashloan.persistence.User;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.loancredit.startpage.source.StartPageDataSource;

import org.reactivestreams.Publisher;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * StartPagePresenter
 *
 * @author 贾博瑄
 */

public class StartPagePresenter extends BasePresenterImpl implements StartPageContract.Presenter {

    private final StartPageContract.View mView;

    private final StartPageDataSource mDataSource;

    public StartPagePresenter(StartPageContract.View view, StartPageDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void loan() {
        Disposable disposable = mDataSource.queryUser()
                .concatMap((Function<User, Publisher<EmptyResponse>>) user -> mDataSource.checkLoan())
                .concatMap((Function<EmptyResponse, Publisher<Loan>>) emptyResponse -> mDataSource.requestLoan())
                .concatMap((Function<Loan, Publisher<EmptyResponse>>) loan -> mDataSource.uploadRiskAppList())
                .compose(new ViewTransformer<EmptyResponse>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(response -> {
                    mView.dismissProgressDialog();
                    mView.showCertificationView();
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

}
