package com.jiaye.cashloan.view.view.home;

import com.jiaye.cashloan.http.data.home.Product;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.home.source.HomeDataSource;

import io.reactivex.Flowable;
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
        mView.setList(null);
    }

    @Override
    public void selectProduct(Product product) {
//        if (product.getIsOpen().equals("1")) {
//            Disposable disposable = mDataSource.addProduct(product)
//                    .compose(new ViewTransformer<Product>() {
//                        @Override
//                        public void accept() {
//                            mView.showProgressDialog();
//                        }
//                    })
//                    .subscribe(new Consumer<Product>() {
//                        @Override
//                        public void accept(Product product) throws Exception {
//                            mView.dismissProgressDialog();
//                            mView.showLoanView();
//                        }
//                    }, new ThrowableConsumer(mView));
//            mCompositeDisposable.add(disposable);
//        }
        Disposable disposable = Flowable.just(product)
                .compose(new ViewTransformer<Product>() {
                    @Override
                    public void accept() {
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<Product>() {
                    @Override
                    public void accept(Product p) throws Exception {
                        mView.dismissProgressDialog();
                        if (p.getName().equals("消费分期")) {
                            mView.showWishView();
                        } else if (p.getName().equals("信贷产品")) {
                            mView.showCreditView();
                        } else {
                            mView.showLoanProductView();
                        }
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
