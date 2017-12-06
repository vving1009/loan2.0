package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.http.data.loan.DefaultProduct;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.http.data.my.User;
import com.jiaye.cashloan.view.data.loan.source.LoanDataSource;

import io.reactivex.Flowable;
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
        Disposable disposable = mDataSource.queryProduct()
                .compose(new ViewTransformer<DefaultProduct>() {
                    @Override
                    public void accept() {
                        mView.cleanProduct();
                    }
                })
                .subscribe(new Consumer<DefaultProduct>() {
                    @Override
                    public void accept(DefaultProduct product) throws Exception {
                        mView.setDefaultProduct(product);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void requestProduct() {
        Disposable disposable = Flowable.concat(mDataSource.queryDefaultProduct(), mDataSource.requestProduct())
                .compose(new ViewTransformer<DefaultProduct>())
                .first(new DefaultProduct())
                .subscribe(new Consumer<DefaultProduct>() {
                    @Override
                    public void accept(DefaultProduct defaultProduct) throws Exception {
                        mView.setDefaultProduct(defaultProduct);
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
                        mView.showLoanAuthView();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
