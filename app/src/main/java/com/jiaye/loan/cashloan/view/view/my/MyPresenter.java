package com.jiaye.loan.cashloan.view.view.my;

import com.jiaye.loan.cashloan.view.BasePresenterImpl;
import com.jiaye.loan.cashloan.view.ThrowableConsumer;
import com.jiaye.loan.cashloan.view.data.auth.User;
import com.jiaye.loan.cashloan.view.data.my.source.MyDataSource;

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
        super(dataSource);
        mView = view;
        mView.setPresenter(this);
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        mDataSource.requestUser().subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                mView.showUserInfo(user);
            }
        }, new ThrowableConsumer(mView));
    }

    @Override
    public void onClickMyCertificate() {
        mDataSource.queryUser().subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                mView.startMyCertificateView(user);
            }
        }, new ThrowableConsumer(mView));
    }
}
