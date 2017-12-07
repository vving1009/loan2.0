package com.jiaye.cashloan.view.view.my.certificate.bank;

import com.jiaye.cashloan.http.data.my.Bank;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.my.certificate.bank.source.BankDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * BankPresenter
 *
 * @author 贾博瑄
 */

public class BankPresenter extends BasePresenterImpl implements BankContract.Presenter {

    private final BankContract.View mView;

    private final BankDataSource mDataSource;

    public BankPresenter(BankContract.View view, BankDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.bank()
                .compose(new ViewTransformer<Bank>(){
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<Bank>() {
                    @Override
                    public void accept(Bank bank) throws Exception {
                        mView.dismissProgressDialog();
                        mView.setName(bank.getName());
                        mView.setPhone(bank.getPhone());
                        mView.setBank(bank.getBank());
                        mView.setNumber(bank.getNumber());
                    }
                });
        mCompositeDisposable.add(disposable);
    }
}
