package com.jiaye.cashloan.view.view.auth.password;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.auth.VerificationCode;
import com.jiaye.cashloan.http.data.auth.VerificationCodeRequest;
import com.jiaye.cashloan.http.data.auth.password.CheckForgetPasswordVerificationCode;
import com.jiaye.cashloan.http.data.auth.password.CheckForgetPasswordVerificationCodeRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.auth.password.source.ForgetPasswordDataSource;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * ForgetPasswordPresenter
 *
 * @author 贾博瑄
 */

public class ForgetPasswordPresenter extends BasePresenterImpl implements ForgetPasswordContract.Presenter {

    private final ForgetPasswordContract.View mView;

    private final ForgetPasswordDataSource mDataSource;

    /*0 忘记密码 1 修改密码*/
    private int mType;

    public ForgetPasswordPresenter(ForgetPasswordContract.View view, ForgetPasswordDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void checkInput() {
        if (TextUtils.isEmpty(mView.getPhone())) {/*检测手机号*/
            mView.setEnable(false);
        } else if (TextUtils.isEmpty(mView.getInputSmsVerificationCode())) {/*检测是否填写验证码*/
            mView.setEnable(false);
        } else {
            mView.setEnable(true);
        }
    }

    @Override
    public void setType(int type) {
        mType = type;
        if (type == 1) {
            Disposable disposable = mDataSource.queryPhone()
                    .compose(new ViewTransformer<String>())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String phone) throws Exception {
                            mView.setPhone(phone);
                        }
                    },new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        }
    }

    @Override
    public void verificationCode() {
        if (TextUtils.isEmpty(mView.getPhone()) || mView.getPhone().length() != 11) {/*检测手机号*/
            mView.showToastById(R.string.error_auth_phone);
        } else {
            VerificationCodeRequest request = new VerificationCodeRequest();
            request.setPhone(mView.getPhone());
            switch (mType) {
                case 0:
                    request.setStatus("2");
                    break;
                case 1:
                    request.setStatus("1");
                    break;
            }
            Disposable disposable = Flowable.just(request)
                    .compose(new ResponseTransformer<VerificationCodeRequest, VerificationCode>("verificationCode"))
                    .compose(new ViewTransformer<VerificationCode>() {
                        @Override
                        public void accept() {
                            mView.showProgressDialog();
                        }
                    })
                    .subscribe(new Consumer<VerificationCode>() {
                        @Override
                        public void accept(VerificationCode verificationCode) throws Exception {
                            mView.smsVerificationCodeCountDown();
                            mView.dismissProgressDialog();
                        }
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        }
    }

    @Override
    public void next() {
        if (TextUtils.isEmpty(mView.getPhone()) || mView.getPhone().length() != 11) {/*检测手机号*/
            mView.showToastById(R.string.error_auth_phone);
        } else if (TextUtils.isEmpty(mView.getInputSmsVerificationCode())) {/*检测是否填写验证码*/
            mView.showToastById(R.string.error_auth_sms_verification);
        } else {
            CheckForgetPasswordVerificationCodeRequest request = new CheckForgetPasswordVerificationCodeRequest();
            request.setPhone(mView.getPhone());
            request.setSmsVerificationCode(mView.getInputSmsVerificationCode());
            Disposable disposable = Flowable.just(request)
                    .compose(new ResponseTransformer<CheckForgetPasswordVerificationCodeRequest, CheckForgetPasswordVerificationCode>("checkForgetPasswordVerificationCode"))
                    .compose(new ViewTransformer<CheckForgetPasswordVerificationCode>() {
                        @Override
                        public void accept() {
                            mView.showProgressDialog();
                        }
                    })
                    .subscribe(new Consumer<CheckForgetPasswordVerificationCode>() {
                        @Override
                        public void accept(CheckForgetPasswordVerificationCode checkForgetPasswordVerificationCode) throws Exception {
                            mView.dismissProgressDialog();
                            mView.showChangePasswordView();
                        }
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        }
    }
}
