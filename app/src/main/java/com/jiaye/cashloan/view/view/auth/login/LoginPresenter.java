package com.jiaye.cashloan.view.view.auth.login;

import android.text.TextUtils;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.auth.login.Login;
import com.jiaye.cashloan.http.data.auth.login.LoginRequest;
import com.jiaye.cashloan.utils.RSAUtil;
import com.jiaye.cashloan.utils.RegexUtil;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.auth.login.source.LoginDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * LoginPresenter
 *
 * @author 贾博瑄
 */

public class LoginPresenter extends BasePresenterImpl implements LoginContract.Presenter {

    private final LoginContract.View mView;

    private final LoginDataSource mDataSource;

    public LoginPresenter(LoginContract.View view, LoginDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void checkInput() {
        if (TextUtils.isEmpty(mView.getPhone())) {/*检测手机号*/
            mView.setEnable(false);
        } else if (TextUtils.isEmpty(mView.getPassword())) {/*检测密码规则*/
            mView.setEnable(false);
        } else {
            mView.setEnable(true);
        }
    }

    @Override
    public void login() {
        if (TextUtils.isEmpty(mView.getPhone()) || mView.getPhone().length() != 11 || !mView.getPhone().matches(RegexUtil.phone())) {/*检测手机号*/
            mView.showToastById(R.string.error_auth_phone);
        } else if (TextUtils.isEmpty(mView.getPassword()) || !mView.getPassword().matches(RegexUtil.password())) {/*检测密码规则*/
            mView.showToastById(R.string.error_auth_password);
        } else {
            LoginRequest request = new LoginRequest();
            request.setPhone(mView.getPhone());
            request.setPassword(RSAUtil.encryptByPublicKeyToBase64(mView.getPassword(), BuildConfig.PUBLIC_KEY));
            Disposable disposable = mDataSource.requestLogin(mView.getPhone(), mView.getPassword())
                    .compose(new ViewTransformer<Login>() {
                        @Override
                        public void accept() {
                            mView.showProgressDialog();
                        }
                    })
                    .subscribe(new Consumer<Login>() {
                        @Override
                        public void accept(Login login) throws Exception {
                            mView.dismissProgressDialog();
                            mView.finish();
                        }
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        }
    }
}
