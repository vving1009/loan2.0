package com.jiaye.cashloan.view.view.my.certificate.bank;

import com.jiaye.cashloan.http.data.my.Bank;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
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
                .compose(new ViewTransformer<Bank>() {
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
                        /*name*/
                        String name = bank.getName();
                        int l = name.length();
                        if (l > 1) {
                            StringBuilder s = new StringBuilder();
                            for (int i = 0; i < l - 1; i++) {
                                s.append("*");
                            }
                            name = s + name.substring(s.length());
                        }
                        mView.setName(name);
                        /*phone*/
                        String phone = bank.getPhone();
                        String start = phone.substring(0, 3);
                        String end = phone.substring(7, 11);
                        mView.setPhone(start + "****" + end);
                        mView.setBank(bank.getBank());
                        /*number*/
                        String number = bank.getNumber();
                        String startNumber = number.substring(0, 3);
                        String endNumber = number.substring(12, 16);
                        mView.setNumber(startNumber + "*********" + endNumber);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
