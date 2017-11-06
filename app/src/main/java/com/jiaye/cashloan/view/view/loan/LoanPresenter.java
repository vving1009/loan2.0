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
        mView.setPresenter(this);
        mDataSource = dataSource;
    }

    @Override
    public void queryProduct() {
        Disposable disposable = mDataSource.queryProduct()
                .compose(new ViewTransformer<Product>(mView))
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
        Disposable disposable = mDataSource.requestProduct()
                .compose(new ViewTransformer<Product>(mView))
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
        Disposable disposable = mDataSource
                .queryUser()
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        mView.startLoanAuthView();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
