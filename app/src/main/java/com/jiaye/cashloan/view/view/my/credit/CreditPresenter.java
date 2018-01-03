package com.jiaye.cashloan.view.view.my.credit;

import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.my.credit.CreditBalanceRequest;
import com.jiaye.cashloan.view.data.my.credit.CreditCashRequest;
import com.jiaye.cashloan.view.data.my.credit.CreditPasswordRequest;
import com.jiaye.cashloan.view.data.my.credit.source.CreditDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * CreditPresenter
 *
 * @author 贾博瑄
 */

public class CreditPresenter extends BasePresenterImpl implements CreditContract.Presenter {

    private final CreditContract.View mView;

    private final CreditDataSource mDataSource;

    public CreditPresenter(CreditContract.View view, CreditDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void password() {
        Disposable disposable = mDataSource.password()
                .compose(new ViewTransformer<CreditPasswordRequest>())
                .subscribe(new Consumer<CreditPasswordRequest>() {
                    @Override
                    public void accept(CreditPasswordRequest request) throws Exception {
                        mView.showPasswordView(request);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void cash() {
        Disposable disposable = mDataSource.cash()
                .compose(new ViewTransformer<CreditCashRequest>())
                .subscribe(new Consumer<CreditCashRequest>() {
                    @Override
                    public void accept(CreditCashRequest request) throws Exception {
                        mView.showCashView(request);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void balance() {
        Disposable disposable = mDataSource.balance()
                .compose(new ViewTransformer<CreditBalanceRequest>())
                .subscribe(new Consumer<CreditBalanceRequest>() {
                    @Override
                    public void accept(CreditBalanceRequest request) throws Exception {
                        mView.showBalanceView(request);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
