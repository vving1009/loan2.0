package com.jiaye.loan.cashloan.view.view.auth.password;

import android.text.TextUtils;

import com.jiaye.loan.cashloan.R;
import com.jiaye.loan.cashloan.http.data.auth.VerificationCode;
import com.jiaye.loan.cashloan.http.data.auth.VerificationCodeRequest;
import com.jiaye.loan.cashloan.http.data.auth.password.CheckForgetPasswordVerificationCode;
import com.jiaye.loan.cashloan.http.data.auth.password.CheckForgetPasswordVerificationCodeRequest;
import com.jiaye.loan.cashloan.http.utils.NetworkTransformer;
import com.jiaye.loan.cashloan.view.BasePresenterImpl;
import com.jiaye.loan.cashloan.view.ThrowableConsumer;
import com.jiaye.loan.cashloan.view.data.BaseDataSource;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * ForgetPasswordPresenter
 *
 * @author 贾博瑄
 */

public class ForgetPasswordPresenter extends BasePresenterImpl implements ForgetPasswordContract.Presenter {

    private final ForgetPasswordContract.View mView;

    public ForgetPasswordPresenter(ForgetPasswordContract.View view, BaseDataSource dataSource) {
        super(dataSource);
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void verificationCode() {
        if (TextUtils.isEmpty(mView.getPhone()) || mView.getPhone().length() != 11) {/*检测手机号*/
            mView.showToastById(R.string.error_auth_phone);
        } else {
            VerificationCodeRequest request = new VerificationCodeRequest();
            request.setPhone(mView.getPhone());
            request.setStatus("2");
            Disposable disposable = Observable.just(request)
                    .compose(new NetworkTransformer<VerificationCodeRequest, VerificationCode>(mView, "verificationCode"))
                    .subscribe(new Consumer<VerificationCode>() {
                        @Override
                        public void accept(VerificationCode verificationCode) throws Exception {
                            mView.smsVerificationCodeCountDown();
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
            Disposable disposable = Observable.just(request)
                    .compose(new NetworkTransformer<CheckForgetPasswordVerificationCodeRequest, CheckForgetPasswordVerificationCode>(mView, "checkForgetPasswordVerificationCode"))
                    .subscribe(new Consumer<CheckForgetPasswordVerificationCode>() {
                        @Override
                        public void accept(CheckForgetPasswordVerificationCode checkForgetPasswordVerificationCode) throws Exception {
                            mView.showChangePasswordView();
                        }
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        }
    }
}
