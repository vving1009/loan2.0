package com.jiaye.cashloan.view.register;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.login.Login;
import com.satcatche.library.utils.RegexUtil;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.register.source.RegisterDataSource;

import io.reactivex.disposables.Disposable;

/**
 * RegisterPresenter
 *
 * @author 贾博瑄
 */

public class RegisterPresenter extends BasePresenterImpl implements RegisterContract.Presenter {

    private final RegisterContract.View mView;

    private final RegisterDataSource mDataSource;

    public RegisterPresenter(RegisterContract.View view, RegisterDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void register() {
        if (TextUtils.isEmpty(mView.getPhone()) || !mView.getPhone().matches(RegexUtil.phone())) {/*检测手机号*/
            mView.showToastById(R.string.error_auth_phone);
        } else if (TextUtils.isEmpty(mView.getCode()) || !mView.getCode().matches(RegexUtil.smsVerification())) {/*检测验证码规则*/
            mView.showToastById(R.string.error_auth_sms_verification);
        } else {
            if (TextUtils.isEmpty(mView.getPassword()) || !mView.getPassword().matches(RegexUtil.password())) {/*检测密码规则*/
                mView.showToastById(R.string.error_auth_password);
            } else {
                register(mView.getPhone(), mView.getCode(), mView.getPassword());
            }
        }
    }

    @Override
    public void verificationCode() {
        if (TextUtils.isEmpty(mView.getPhone()) || !mView.getPhone().matches(RegexUtil.phone())) {/*检测手机号*/
            mView.showToastById(R.string.error_auth_phone);
        } else {
            Disposable disposable = mDataSource.requestRegisterCode(mView.getPhone())
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

    private void register(String phone, String code, String password) {
        Disposable disposable = mDataSource.requestRegister(phone, code, password)
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
