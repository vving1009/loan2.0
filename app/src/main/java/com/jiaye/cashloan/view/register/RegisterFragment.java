package com.jiaye.cashloan.view.register;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFunctionFragment;
import com.jiaye.cashloan.view.login.LoginFragment;
import com.jiaye.cashloan.view.register.source.RegisterRepository;
import com.jiaye.cashloan.widget.LoanEditText;

/**
 * RegisterFragment
 *
 * @author 贾博瑄
 */

public class RegisterFragment extends BaseFunctionFragment implements RegisterContract.View {

    private RegisterContract.Presenter mPresenter;

    private LoanEditText mEditPhone;

    private LoanEditText mEditCode;

    private LoanEditText mEditPassword;

    private Button mBtnRegister;

    private boolean phoneReady = false;

    private boolean codeReady = false;

    private boolean pwReady = false;

    public static RegisterFragment newInstance() {
        Bundle args = new Bundle();
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getTitleId() {
        return R.string.register;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.register_fragment, frameLayout, true);
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
        mEditCode.setEnabled(false);
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
        mBtnRegister = rootView.findViewById(R.id.btn_confirm);
        mBtnRegister.setOnClickListener(v -> mPresenter.register());
        mPresenter = new RegisterPresenter(this, new RegisterRepository());
        mPresenter.subscribe();
        return rootView;
    }

    private void setBtnEnable() {
        if (phoneReady && !mEditCode.isCountDownNow()) {
            mEditCode.setEnabled(true);
        } else {
            mEditCode.setEnabled(false);
        }
        if (phoneReady && codeReady && pwReady) {
            mBtnRegister.setEnabled(true);
        } else {
            mBtnRegister.setEnabled(false);
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
        LoginFragment.finish(getActivity());
    }

    @Override
    public void smsVerificationCodeCountDown() {
        mEditCode.startCountDown();
    }
}
