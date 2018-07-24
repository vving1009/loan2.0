package com.jiaye.cashloan.view.forgetpassword;

import android.os.Bundle;
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
        mEditCode = rootView.findViewById(R.id.edit_code);
        mEditCode.setOnClickVerificationCode(() -> mPresenter.verificationCode());
        mEditPassword = rootView.findViewById(R.id.edit_password);
        Button btnPassword = rootView.findViewById(R.id.btn_confirm);
        btnPassword.setOnClickListener(v -> mPresenter.password());
        mPresenter = new ForgetPasswordPresenter(this, new ForgetPasswordRepository());
        mPresenter.subscribe();
        return rootView;
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
