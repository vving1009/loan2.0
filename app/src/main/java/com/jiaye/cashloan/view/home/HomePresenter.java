package com.jiaye.cashloan.view.home;

import com.jiaye.cashloan.http.data.loan.UploadRiskAppList;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.home.source.HomeDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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
    public void subscribe() {
        super.subscribe();
    }

    @Override
    public void loan(final String loanId) {
        Disposable disposable = mDataSource.requestLoan(loanId)
                .compose(new ViewTransformer<UploadRiskAppList>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<UploadRiskAppList>() {
                    @Override
                    public void accept(UploadRiskAppList checkLoan) throws Exception {
                        mView.dismissProgressDialog();
                        mView.showLoanAuthView();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
