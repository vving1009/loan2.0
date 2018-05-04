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

    private ProductList.Product[] mProducts;

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
                        mProducts = products;
                        mView.setProduct(products);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposableProductList);
    }

    @Override
    public void loan(int position) {
        if (mProducts == null || mProducts.length == 0 || mProducts[0] == null) {
            return;
        }
        Disposable disposable = mDataSource.requestCheckLoan(mProducts[position].getId())
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
                        mView.showLoanAuthView(mProducts[0].getId());
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
