package com.jiaye.cashloan.view.my;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.my.CheckAccount;
import com.jiaye.cashloan.http.data.plan.Plan;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.my.source.MyDataSource;

import io.reactivex.disposables.Disposable;

/**
 * MyPresenter
 *
 * @author 贾博瑄
 */

public class MyPresenter extends BasePresenterImpl implements MyContract.Presenter {

    private final MyContract.View mView;

    private final MyDataSource mDataSource;

    private boolean mIsLogin;

    public MyPresenter(MyContract.View view, MyDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void update() {
        Disposable disposable = mDataSource.queryUser()
                .compose(new ViewTransformer<>())
                .subscribe(user -> {
                    if (TextUtils.isEmpty(user.getToken())) {
                        mView.setBtnText("登录");
                        mIsLogin = false;
                    } else {
                        mView.setBtnText("注销");
                        mIsLogin = true;
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void loginOrLogout() {
        if (mIsLogin) {
            Disposable disposable = mDataSource.exit()
                    .compose(new ViewTransformer<>())
                    .subscribe(aBoolean -> {
                        mView.setBtnText("登录");
                        mIsLogin = false;
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        } else {
            mView.startAuthView();
        }
    }

    @Override
    public void bank() {
        Disposable disposable = mDataSource.checkBank()
                .compose(new ViewTransformer<CheckAccount>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(checkAccount -> {
                    mView.dismissProgressDialog();
                    if (checkAccount.getIsOpen() == 1) {
                        mView.showAccountView();
                    } else {
                        mView.showToastById(R.string.my_error_account);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void plan() {
        Disposable disposable = mDataSource.checkPlan()
                .compose(new ViewTransformer<Plan>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(plan -> {
                    mView.dismissProgressDialog();
                    if (plan.getList() != null && plan.getList().size() > 0) {
                        mView.showPlanView();
                    } else {
                        mView.showToastById(R.string.my_error_plan);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
