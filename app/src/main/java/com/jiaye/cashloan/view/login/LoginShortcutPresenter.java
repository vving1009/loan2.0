package com.jiaye.cashloan.view.login;

import android.net.Uri;
import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.base.NetworkException;
import com.jiaye.cashloan.http.data.login.Login;
import com.jiaye.cashloan.utils.RegexUtil;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.login.source.LoginShortcutDataSource;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * LoginShortcutPresenter
 *
 * @author 贾博瑄
 */

public class LoginShortcutPresenter extends BasePresenterImpl implements LoginShortcutContract.Presenter {

    private final LoginShortcutContract.View mView;

    private final LoginShortcutDataSource mDataSource;

    public LoginShortcutPresenter(LoginShortcutContract.View view, LoginShortcutDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void login() {
        if (TextUtils.isEmpty(mView.getPhone()) || !mView.getPhone().matches(RegexUtil.phone())) {/*检测手机号*/
            mView.showToastById(R.string.error_auth_phone);
        } else if (TextUtils.isEmpty(mView.getCode()) || !mView.getCode().matches(RegexUtil.smsVerification())) {/*检测验证码规则*/
            mView.showToastById(R.string.error_auth_sms_verification);
        } else {
            if (mView.isShowPasswordView()) {
                if (TextUtils.isEmpty(mView.getPassword()) || !mView.getPassword().matches(RegexUtil.password())) {/*检测密码规则*/
                    mView.showToastById(R.string.error_auth_password);
                } else {
                    // 通过快捷登录触发注册
                    login(mView.getPhone(), mView.getCode(), mView.getPassword());
                }
            } else {
                // 快捷登录
                login(mView.getPhone(), mView.getCode(), "");
            }

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
                        mView.hidePasswordView();
                    }, new LoginThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        }
    }

    @Override
    public void getSmsCode(Uri uri) {
        Disposable disposable = mDataSource.querySmsCode(uri)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView::writeSmsCode, new ThrowableConsumer());
        mCompositeDisposable.add(disposable);
    }

    private void login(String phone, String code, String password) {
        Disposable disposable = mDataSource.requestLogin(phone, code, password)
                .compose(new ViewTransformer<Login>() {
                    @Override
                    public void accept() {
                        mView.showProgressDialog();
                    }
                })
                .subscribe(login -> {
                    mView.dismissProgressDialog();
                    mView.finish();
                }, new LoginThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    private class LoginThrowableConsumer extends ThrowableConsumer {

        public LoginThrowableConsumer(BaseViewContract contract) {
            super(contract);
        }

        @Override
        public void accept(Throwable t) throws Exception {
            if (t instanceof NetworkException) {
                if (((NetworkException) t).getErrorCode().equals("0002")) {
                    mView.dismissProgressDialog();
                    mView.smsVerificationCodeCountDown();
                    mView.showHintDialog();
                    mView.showPasswordView();
                } else {
                    super.accept(t);
                }
            } else {
                super.accept(t);
            }
        }
    }
}
