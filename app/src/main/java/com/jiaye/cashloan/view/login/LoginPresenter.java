package com.jiaye.cashloan.view.login;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.login.Login;
import com.jiaye.cashloan.utils.RegexUtil;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.login.source.LoginDataSource;

import io.reactivex.disposables.Disposable;

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
        /*监听获取验证码按钮是否可用*/
        if (!TextUtils.isEmpty(mView.getPhone())) {
            mView.setSmsBtnEnable(true);
        } else {
            mView.setSmsBtnEnable(false);
        }
        /*监听登录按钮是否可用*/
        if (TextUtils.isEmpty(mView.getPhone())) {
            mView.setLoginBtnEnable(false);
        } else if (TextUtils.isEmpty(mView.getCode())) {
            mView.setLoginBtnEnable(false);
        } else {
            mView.setLoginBtnEnable(true);
        }

    }

    @Override
    public void login() {
        if (TextUtils.isEmpty(mView.getPhone()) || !mView.getPhone().matches(RegexUtil.phone())) {/*检测手机号*/
            mView.showToastById(R.string.error_auth_phone);
        } else if (TextUtils.isEmpty(mView.getCode()) || !mView.getCode().matches(RegexUtil.smsVerification())) {/*检测密码规则*/
            mView.showToastById(R.string.error_auth_sms_verification);
        } else {
            Disposable disposable = mDataSource.requestLogin(mView.getPhone(), mView.getCode())
                    .compose(new ViewTransformer<Login>() {
                        @Override
                        public void accept() {
                            mView.showProgressDialog();
                        }
                    })
                    .subscribe(login -> {
                        mView.dismissProgressDialog();
                        mView.finish();
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        }
    }

    @Override
    public void verificationCode() {
        if (TextUtils.isEmpty(mView.getPhone()) || !mView.getPhone().matches(RegexUtil.phone())) {/*检测手机号*/
            mView.showToastById(R.string.error_auth_phone);
        } else {
            Disposable disposable = mDataSource.requestVerificationCode(mView.getPhone())
                    .compose(new ViewTransformer<EmptyResponse>() {
                        @Override
                        public void accept() {
                            super.accept();
                            mView.showProgressDialog();
                        }
                    })
                    .subscribe(verificationCode -> {
                        mView.dismissProgressDialog();
                        mView.smsVerificationCodeCountDown();
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        }
    }
}
