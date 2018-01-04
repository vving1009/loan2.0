package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.http.data.loan.DefaultProduct;
import com.jiaye.cashloan.http.data.loan.LoanAuth;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
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
                .compose(new ViewTransformer<DefaultProduct>(){
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .first(new DefaultProduct())
                .subscribe(new Consumer<DefaultProduct>() {
                    @Override
                    public void accept(DefaultProduct defaultProduct) throws Exception {
                        mView.dismissProgressDialog();
                        mView.setDefaultProduct(defaultProduct);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void loan() {
        Disposable disposable = mDataSource
                .requestCheck()
                .compose(new ViewTransformer<LoanAuth>(){
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<LoanAuth>() {
                    @Override
                    public void accept(LoanAuth loanAuth) throws Exception {
                        mView.dismissProgressDialog();
                        mView.showLoanAuthView();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
