package com.jiaye.cashloan.view.forgetpassword;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFunctionFragment;
import com.jiaye.cashloan.view.forgetpassword.source.ForgetPasswordRepository;
import com.jiaye.cashloan.widget.LoanEditText;

/**
 * ForgetPasswordFragment
 *
 * @author 贾博瑄
 */

public class ForgetPasswordFragment extends BaseFunctionFragment implements ForgetPasswordContract.View {

    private ForgetPasswordContract.Presenter mPresenter;

    private LoanEditText mEditPhone;

    private LoanEditText mEditCode;

    private LoanEditText mEditPassword;

    private Button mBtnPassword;

    private boolean phoneReady = false;

    private boolean codeReady = false;

    private boolean pwReady = false;

    public static ForgetPasswordFragment newInstance() {
        Bundle args = new Bundle();
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getTitleId() {
        return R.string.forget_password;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.forget_password_fragment, frameLayout, true);
        mEditPhone = rootView.findViewById(R.id.edit_phone);
        mEditPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {
                    phoneReady = true;
                } else {
                    phoneReady = false;
                }
                setBtnEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditCode = rootView.findViewById(R.id.edit_code);
        mEditCode.setVerificationBtnEnabled(false);
        mEditCode.setOnClickVerificationCode(() -> mPresenter.verificationCode());
        mEditCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 4) {
                    codeReady = true;
                } else {
                    codeReady = false;
                }
                setBtnEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditPassword = rootView.findViewById(R.id.edit_password);
        mEditPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 6) {
                    pwReady = true;
                } else {
                    pwReady = false;
                }
                setBtnEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBtnPassword = rootView.findViewById(R.id.btn_confirm);
        mBtnPassword.setOnClickListener(v -> mPresenter.password());
        mPresenter = new ForgetPasswordPresenter(this, new ForgetPasswordRepository());
        mPresenter.subscribe();
        return rootView;
    }

    private void setBtnEnable() {
        if (phoneReady && !mEditCode.isCountDownNow()) {
            mEditCode.setVerificationBtnEnabled(true);
        } else {
            mEditCode.setVerificationBtnEnabled(false);
        }
        if (phoneReady && codeReady && pwReady) {
            mBtnPassword.setEnabled(true);
        } else {
            mBtnPassword.setEnabled(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
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
    public String getPassword() {
        return mEditPassword.getText().toString();
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void smsVerificationCodeCountDown() {
        mEditCode.startCountDown();
    }
}
