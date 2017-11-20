package com.jiaye.cashloan.view.view.my;

import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.data.auth.User;
import com.jiaye.cashloan.view.data.my.source.MyDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * MyPresenter
 *
 * @author 贾博瑄
 */

public class MyPresenter extends BasePresenterImpl implements MyContract.Presenter {

    private final MyContract.View mView;

    private final MyDataSource mDataSource;

    public MyPresenter(MyContract.View view, MyDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.requestUser().subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                mView.showUserInfo(user);
            }
        }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onClickMyCertificate() {
        Disposable disposable = mDataSource.queryUser().subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                mView.startMyCertificateView(user);
            }
        }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
