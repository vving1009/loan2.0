package com.jiaye.cashloan.view.view.auth.login;

import android.text.TextUtils;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.auth.login.Login;
import com.jiaye.cashloan.http.data.auth.login.LoginRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.utils.RSAUtil;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.auth.login.source.LoginDataSource;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
        mView.setPresenter(this);
        mDataSource = dataSource;
    }

    @Override
    public void login() {
        if (TextUtils.isEmpty(mView.getPhone()) || mView.getPhone().length() != 11) {/*检测手机号*/
            mView.showToastById(R.string.error_auth_phone);
        } else if (TextUtils.isEmpty(mView.getPassword()) || !mView.getPassword().matches("^[a-zA-Z0-9]{6,12}$")) {/*检测密码规则*/
            mView.showToastById(R.string.error_auth_password);
        } else {
            LoginRequest request = new LoginRequest();
            request.setPhone(mView.getPhone());
            request.setPassword(RSAUtil.encryptByPublicKeyToBase64(mView.getPassword(), BuildConfig.PUBLIC_KEY));
            Disposable disposable = Flowable.just(request)
                    .compose(new ResponseTransformer<LoginRequest, Login>("login"))
                    .compose(new ViewTransformer<Login>(mView))
                    .map(new Function<Login, Login>() {
                        @Override
                        public Login apply(Login login) throws Exception {
                            login.setPhone(mView.getPhone());
                            return login;
                        }
                    })
                    .observeOn(Schedulers.io())
                    .map(new Function<Login, Login>() {
                        @Override
                        public Login apply(Login login) throws Exception {
                            mDataSource.addUser(login);
                            return login;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Login>() {
                        @Override
                        public void accept(Login login) throws Exception {
                            mView.finish();
                        }
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        }
    }
}
