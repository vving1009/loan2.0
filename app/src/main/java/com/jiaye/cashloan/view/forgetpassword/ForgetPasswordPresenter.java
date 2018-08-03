package com.jiaye.cashloan.view.forgetpassword;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.forgetpassword.source.ForgetPasswordDataSource;
import com.satcatche.library.utils.RegexUtil;

import io.reactivex.disposables.Disposable;

/**
 * ForgetPasswordPresenter
 *
 * @author 贾博瑄
 */

public class ForgetPasswordPresenter extends BasePresenterImpl implements ForgetPasswordContract.Presenter {

    private final ForgetPasswordContract.View mView;

    private final ForgetPasswordDataSource mDataSource;

    public ForgetPasswordPresenter(ForgetPasswordContract.View view, ForgetPasswordDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void password() {
        if (TextUtils.isEmpty(mView.getPhone()) || !mView.getPhone().matches(RegexUtil.phone())) {/*检测手机号*/
            mView.showToastById(R.string.error_auth_phone);
        } else if (TextUtils.isEmpty(mView.getCode()) || !mView.getCode().matches(RegexUtil.smsVerification())) {/*检测验证码规则*/
            mView.showToastById(R.string.error_auth_sms_verification);
        } else {
            if (TextUtils.isEmpty(mView.getPassword()) || !mView.getPassword().matches(RegexUtil.password())) {/*检测密码规则*/
                mView.showToastById(R.string.error_auth_password);
            } else {
                password(mView.getPhone(), mView.getCode(), mView.getPassword());
            }
        }
    }

    @Override
    public void verificationCode() {
        if (TextUtils.isEmpty(mView.getPhone()) || !mView.getPhone().matches(RegexUtil.phone())) {/*检测手机号*/
            mView.showToastById(R.string.error_auth_phone);
        } else {
            Disposable disposable = mDataSource.requestForgetPasswordCode(mView.getPhone())
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

    private void password(String phone, String code, String password) {
        Disposable disposable = mDataSource.requestForgetPassword(phone, code, password)
                .compose(new ViewTransformer<EmptyResponse>() {
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
