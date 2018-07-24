package com.jiaye.cashloan.view.register;

import android.os.Bundle;
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
        mEditCode = rootView.findViewById(R.id.edit_code);
        mEditCode.setOnClickVerificationCode(() -> mPresenter.verificationCode());
        mEditPassword = rootView.findViewById(R.id.edit_password);
        Button btnRegister = rootView.findViewById(R.id.btn_confirm);
        btnRegister.setOnClickListener(v -> mPresenter.register());
        mPresenter = new RegisterPresenter(this, new RegisterRepository());
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
        LoginFragment.finish(getActivity());
    }

    @Override
    public void smsVerificationCodeCountDown() {
        mEditCode.startCountDown();
    }
}
