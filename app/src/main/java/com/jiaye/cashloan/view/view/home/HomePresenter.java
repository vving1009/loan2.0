package com.jiaye.cashloan.view.view.home;

import com.jiaye.cashloan.http.data.home.BannerList;
import com.jiaye.cashloan.http.data.home.ProductList;
import com.jiaye.cashloan.http.data.loan.CheckLoan;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.home.HomeDataSource;

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
        Disposable disposableBannerList = mDataSource.requestBannerList()
                .compose(new ViewTransformer<BannerList.Banner[]>())
                .subscribe(new Consumer<BannerList.Banner[]>() {
                    @Override
                    public void accept(BannerList.Banner[] banners) throws Exception {
                        mView.setBanners(banners);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposableBannerList);
        Disposable disposableProductList = mDataSource.requestProductList()
                .compose(new ViewTransformer<ProductList.Product[]>())
                .subscribe(new Consumer<ProductList.Product[]>() {
                    @Override
                    public void accept(ProductList.Product[] products) throws Exception {
                        mView.setProduct(products);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposableProductList);
    }

    @Override
    public void loan(final String loanId) {
        Disposable disposable = mDataSource.requestCheckLoan(loanId)
                .compose(new ViewTransformer<CheckLoan>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<CheckLoan>() {
                    @Override
                    public void accept(CheckLoan checkLoan) throws Exception {
                        mView.dismissProgressDialog();
                        mView.showLoanAuthView(loanId);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
