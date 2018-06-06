package com.jiaye.cashloan.view.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.login.source.LoginRepository;
import com.jiaye.cashloan.widget.LoanEditText;

/**
 * LoginFragment
 *
 * @author 贾博瑄
 */

public class LoginFragment extends BaseFragment implements LoginContract.View, TextWatcher {

    private LoginContract.Presenter mPresenter;

    private LoanEditText mEditPhone;

    private LoanEditText mEditCode;

    private Button mBtnLogin;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_fragment, container, false);
        root.getBackground().setAlpha(26);
        mEditPhone = root.findViewById(R.id.edit_phone);
        mEditPhone.addTextChangedListener(this);
        mEditPhone.setVerificationBtnEnabled(false);
        mEditPhone.setOnClickVerificationCode(() -> {
            clearError();
            mPresenter.verificationCode();
        });
        mEditCode = root.findViewById(R.id.edit_code);
        mEditCode.addTextChangedListener(this);
        mBtnLogin = root.findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(v -> {
            clearError();
            mPresenter.login();
        });
        mPresenter = new LoginPresenter(this, new LoginRepository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void showToastById(int resId) {
        switch (resId) {
            case R.string.error_auth_phone:
                mEditPhone.setError(getString(R.string.error_auth_phone));
                break;
            case R.string.error_auth_sms_verification:
                mEditCode.setError(getString(R.string.error_auth_sms_verification));
                break;
            default:
                super.showToastById(resId);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mPresenter.checkInput();
    }

    @Override
    public void setLoginBtnEnable(boolean enable) {
        mBtnLogin.setEnabled(enable);
    }

    @Override
    public void setSmsBtnEnable(boolean enable) {
        mEditPhone.setVerificationBtnEnabled(enable);
    }

    @Override
    public String getPhone() {
        return mEditPhone.getText().toString();
    }

    @Override
    public String getCode() {
        return mEditCode.getText().toString();
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void smsVerificationCodeCountDown() {
        mEditPhone.startCountDown();
    }

    /*清除错误信息*/
    private void clearError() {
        mEditPhone.setError("");
        mEditCode.setError("");
    }
}
