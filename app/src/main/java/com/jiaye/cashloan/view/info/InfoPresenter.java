package com.jiaye.cashloan.view.info;

import com.jiaye.cashloan.http.data.loan.SaveContact;
import com.jiaye.cashloan.http.data.loan.SavePerson;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * InfoPresenter
 *
 * @author 贾博瑄
 */
public class InfoPresenter extends BasePresenterImpl implements InfoContact.Presenter {

    private InfoContact.View mView;

    public InfoPresenter(InfoContact.View view) {
        mView = view;
    }

    @Override
    public void submit(Flowable<Boolean> bPerson, Flowable<Boolean> bContact, Flowable<SavePerson> person, Flowable<SaveContact> contact) {
        Disposable disposable = bPerson
                .compose(new ViewTransformer<Boolean>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .flatMap((Function<Boolean, Publisher<Boolean>>) aBoolean -> bContact)
                .observeOn(Schedulers.io())
                .flatMap((Function<Boolean, Publisher<SavePerson>>) aBoolean -> person)
                .flatMap((Function<SavePerson, Publisher<SaveContact>>) savePerson -> contact)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(saveContact -> {
                    mView.dismissProgressDialog();
                    mView.finish();
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
