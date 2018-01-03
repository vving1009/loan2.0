package com.jiaye.cashloan.view.view.main;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.LoanAuth;
import com.jiaye.cashloan.http.data.my.User;
import com.jiaye.cashloan.view.AuthView;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.LocalException;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.main.MainDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by guozihua on 2018/1/3.
 */

public class MainPresenter extends BasePresenterImpl implements MainContract.Presenter {

    private final MainContract.View mView;

    private final MainDataSource mDataSource;

    public MainPresenter(MainContract.View view, MainDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }



    @Override
    public void requestCheck() {

        Disposable disposable = mDataSource
                .queryUser()
                .compose(new ViewTransformer<User>())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                            mView.isLogin();
                    }
                }, new ThrowableConsumer(mView){
                    @Override
                    public void accept(Throwable t) throws Exception {
                        if (t instanceof LocalException) {
                            switch (((LocalException) t).getErrorId()) {
                                case R.string.error_auth_not_log_in:
                                    mView.startAuthView();
                                    break;
                            }
                        }
                    }
                });
        mCompositeDisposable.add(disposable);
    }
}
