package com.jiaye.cashloan.view.view.my.credit.bank;

import com.jiaye.cashloan.http.data.my.CreditUnBindBank;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.my.credit.bank.source.CreditBankDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * CreditBankPresenter
 *
 * @author 贾博瑄
 */

public class CreditBankPresenter extends BasePresenterImpl implements CreditBankContract.Presenter {

    private CreditBankContract.View mView;

    private CreditBankDataSource mDataSource;

    public CreditBankPresenter(CreditBankContract.View view, CreditBankDataSource dataSource) {
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
