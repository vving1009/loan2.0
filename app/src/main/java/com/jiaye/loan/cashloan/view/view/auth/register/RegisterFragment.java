package com.jiaye.loan.cashloan.view.view.auth.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jiaye.loan.cashloan.R;
import com.jiaye.loan.cashloan.http.data.auth.register.RegisterRequest;
import com.jiaye.loan.cashloan.http.data.auth.register.RegisterVerificationCodeRequest;
import com.jiaye.loan.cashloan.view.BaseFragment;
import com.jiaye.loan.cashloan.view.view.auth.login.LoginFragment;
import com.jiaye.loan.cashloan.widget.LoanEditText;

/**
 * RegisterFragment
 *
 * @author 贾博瑄
 */

public class RegisterFragment extends BaseFragment implements RegisterContract.View {

    private RegisterContract.Presenter mPresenter;

    private LoanEditText mEditPhone;

    private LoanEditText mEditImgVerificationCode;

    private LoanEditText mEditPassword;

    private LoanEditText mEditSmsVerificationCode;

    private LoanEditText mEditReferralCode;

    private CheckBox mCheckBox;

    public static RegisterFragment newInstance() {
        Bundle args = new Bundle();
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.register_fragment, container, false);
        mEditPhone = (LoanEditText) root.findViewById(R.id.edit_phone);
        mEditImgVerificationCode = (LoanEditText) root.findViewById(R.id.edit_img_verification_code);
        mEditPassword = (LoanEditText) root.findViewById(R.id.edit_password);
        mEditSmsVerificationCode = (LoanEditText) root.findViewById(R.id.edit_sms_verification_code);
        mEditReferralCode = (LoanEditText) root.findViewById(R.id.edit_referral_code);
        mCheckBox = (CheckBox) root.findViewById(R.id.checkbox);
        TextView textAgree = (TextView) root.findViewById(R.id.text_agree);
        SpannableString string = new SpannableString(getString(R.string.read_protocol));
        string.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_orange)), 7, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textAgree.setText(string);
        root.findViewById(R.id.text_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginView();
            }
        });
        root.findViewById(R.id.text_forget_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearError();
                showForgetPasswordView();
            }
        });
        root.findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearError();
                register();
            }
        });
        mEditSmsVerificationCode.setOnClickVerificationCode(new LoanEditText.OnClickVerificationCode() {
            @Override
            public void onClickCaptcha() {
                verificationCode();
            }
        });
        textAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProtocolView();
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.subscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showToastById(int resId) {
        switch (resId) {
            case R.string.error_auth_phone:
                mEditPhone.setError(getString(R.string.error_auth_phone));
                break;
            case R.string.error_auth_img_verification:
                mEditImgVerificationCode.setError(getString(R.string.error_auth_img_verification));
                break;
            case R.string.error_auth_password:
                mEditPassword.setError(getString(R.string.error_auth_password));
                break;
            case R.string.error_auth_sms_verification:
                mEditSmsVerificationCode.setError(getString(R.string.error_auth_sms_verification));
                break;
            default:
                super.showToastById(resId);
                break;
        }
    }

    @Override
    public String getInputImgVerificationCode() {
        return mEditImgVerificationCode.getText().toString();
    }

    @Override
    public String getImgVerificationCode() {
        return mEditImgVerificationCode.getCode();
    }

    @Override
    public void smsVerificationCodeCountDown() {
        mEditSmsVerificationCode.startCountDown();
    }

    @Override
    public boolean isAgree() {
        return mCheckBox.isChecked();
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    /*显示登录页面*/
    private void showLoginView() {
        FragmentManager fragmentManager = getFragmentManager();
        LoginFragment fragment = LoginFragment.newInstance();
        fragmentManager.beginTransaction().replace(R.id.layout_content, fragment).commit();
    }

    /*显示忘记密码页面*/
    private void showForgetPasswordView() {

    }

    /*显示注册协议页面*/
    private void showProtocolView() {

    }

    /*获取验证码*/
    private void verificationCode() {
        RegisterVerificationCodeRequest request = new RegisterVerificationCodeRequest();
        request.setPhone(mEditPhone.getText().toString());
        mPresenter.verificationCode(request);
    }

    /*注册*/
    private void register() {
        RegisterRequest request = new RegisterRequest();
        request.setPhone(mEditPhone.getText().toString());
        request.setImgVerificationCode(mEditImgVerificationCode.getText().toString());
        request.setPassword(mEditPassword.getText().toString());
        request.setSmsVerificationCode(mEditSmsVerificationCode.getText().toString());
        request.setReferralCode(mEditReferralCode.getText().toString());
        mPresenter.register(request);
    }

    /*清除错误信息*/
    private void clearError() {
        mEditPhone.setError("");
        mEditImgVerificationCode.setError("");
        mEditPassword.setError("");
        mEditSmsVerificationCode.setError("");
    }
}
