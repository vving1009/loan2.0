package com.jiaye.cashloan.view.view.market;

import com.jiaye.cashloan.http.data.my.User;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.market.MarketDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by guozihua on 2018/1/4.
 */

public class MarketPresenter extends BasePresenterImpl implements MarketContract.Presenter {


    private final MarketContract.View mView;

    private final MarketDataSource mDataSource;

    public MarketPresenter(MarketContract.View view, MarketDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void checklogin() {
        Disposable disposable = mDataSource
                .checkLogin()
                .compose(new ViewTransformer<Boolean>())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mView.isLogin(aBoolean);
                    }
                });

        mCompositeDisposable.add(disposable);
    }

    @Override
    public void queryUser() {
        Disposable disposable = mDataSource
                .queryUser()
                .compose(new ViewTransformer<User>())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        mView.getUserInfo(user);
                    }
                });
    }


}
