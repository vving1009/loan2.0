package com.jiaye.cashloan.view.view.my.certificate.idcard;

import com.jiaye.cashloan.http.data.my.IDCardAuth;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.my.certificate.idcard.source.IdCardDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * IdCardPresenter
 *
 * @author 贾博瑄
 */

public class IdCardPresenter extends BasePresenterImpl implements IdCardContract.Presenter {

    private final IdCardContract.View mView;

    private final IdCardDataSource mDataSource;

    public IdCardPresenter(IdCardContract.View view, IdCardDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.idCardAuth()
                .compose(new ViewTransformer<IDCardAuth>(){
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<IDCardAuth>() {
                    @Override
                    public void accept(IDCardAuth idCardAuth) throws Exception {
                        mView.dismissProgressDialog();
                        mView.setName(idCardAuth.getName());
                        String number = idCardAuth.getNumber();
                        String start = number.substring(0, 3);
                        String end = number.substring(14, 18);
                        mView.setNumber(start + "***********" + end);
                        mView.setDate(idCardAuth.getDate());
                    }
                },new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
