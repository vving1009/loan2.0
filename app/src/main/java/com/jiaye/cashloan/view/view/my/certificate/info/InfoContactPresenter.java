package com.jiaye.cashloan.view.view.my.certificate.info;

import com.jiaye.cashloan.http.data.loan.Contact;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.my.certificate.info.contact.source.InfoContactDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * InfoContactPresenter
 *
 * @author 贾博瑄
 */

public class InfoContactPresenter extends BasePresenterImpl implements InfoContactContract.Presenter {

    private final InfoContactContract.View mView;

    private final InfoContactDataSource mDataSource;

    public InfoContactPresenter(InfoContactContract.View view, InfoContactDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.requestContact()
                .compose(new ViewTransformer<Contact>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<Contact>() {
                    @Override
                    public void accept(Contact contact) throws Exception {
                        for (int i = 0; i < contact.getData().length; i++) {
                            if (i == 0) {
                                mView.setFamilyName(contact.getData()[i].getName());
                                mView.setFamily(contact.getData()[i].getRelation());
                                mView.setFamilyPhone(contact.getData()[i].getPhone());
                            } else if (i == 1) {
                                mView.setFamilyName2(contact.getData()[i].getName());
                                mView.setFamily2(contact.getData()[i].getRelation());
                                mView.setFamilyPhone2(contact.getData()[i].getPhone());
                            } else if (i == 2) {
                                mView.setFriendName(contact.getData()[i].getName());
                                mView.setFriend(contact.getData()[i].getRelation());
                                mView.setFriendPhone(contact.getData()[i].getPhone());
                            } else if (i == 3) {
                                mView.setFriendName2(contact.getData()[i].getName());
                                mView.setFriend2(contact.getData()[i].getRelation());
                                mView.setFriendPhone2(contact.getData()[i].getPhone());
                            }
                            mView.dismissProgressDialog();
                        }
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
