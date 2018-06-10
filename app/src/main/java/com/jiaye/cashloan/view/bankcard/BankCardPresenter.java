package com.jiaye.cashloan.view.bankcard;

import com.jiaye.cashloan.http.data.my.CreditUnBindBank;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.bankcard.source.BankCardDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * BankCardPresenter
 *
 * @author 贾博瑄
 */

public class BankCardPresenter extends BasePresenterImpl implements BankCardContract.Presenter {

    private BankCardContract.View mView;

    private BankCardDataSource mDataSource;

    public BankCardPresenter(BankCardContract.View view, BankCardDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void unBind() {
        Disposable disposable = mDataSource.unBind()
                .compose(new ViewTransformer<CreditUnBindBank>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<CreditUnBindBank>() {
                    @Override
                    public void accept(CreditUnBindBank creditUnBindBank) throws Exception {
                        mView.dismissProgressDialog();
                        mView.complete();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
