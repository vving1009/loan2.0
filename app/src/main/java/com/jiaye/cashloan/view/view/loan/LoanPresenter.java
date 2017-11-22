package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.http.data.home.Product;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.auth.User;
import com.jiaye.cashloan.view.data.loan.source.LoanDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * LoanPresenter
 *
 * @author 贾博瑄
 */

public class LoanPresenter extends BasePresenterImpl implements LoanContract.Presenter {

    private final LoanContract.View mView;

    private final LoanDataSource mDataSource;

    public LoanPresenter(LoanContract.View view, LoanDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void queryProduct() {
        // 因为LoanFragment退出应用前不会销毁,所以要手动的clear CompositeDisposable
        mCompositeDisposable.clear();
        Disposable disposable = mDataSource.queryProduct()
                .compose(new ViewTransformer<Product>() {
                    @Override
                    public void accept() {
                        mView.cleanProduct();
                    }
                })
                .subscribe(new Consumer<Product>() {
                    @Override
                    public void accept(Product product) throws Exception {
                        mView.setProduct(product);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void requestProduct() {
        // 因为LoanFragment退出应用前不会销毁,所以要手动的clear CompositeDisposable
        mCompositeDisposable.clear();
        Disposable disposable = mDataSource.requestProduct()
                .compose(new ViewTransformer<Product>() {
                    @Override
                    public void accept() {
                        mView.cleanProduct();
                    }
                })
                .subscribe(new Consumer<Product>() {
                    @Override
                    public void accept(Product product) throws Exception {
                        mView.setProduct(product);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void loan() {
        // 因为LoanFragment退出应用前不会销毁,所以要手动的clear CompositeDisposable
        mCompositeDisposable.clear();
        Disposable disposable = mDataSource
                .queryUser()
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        mView.showLoanAuthView();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
