package com.jiaye.cashloan.view.view.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.auth.login.source.LoginRepository;
import com.jiaye.cashloan.view.view.auth.password.PasswordActivity;
import com.jiaye.cashloan.view.view.auth.register.RegisterFragment;
import com.jiaye.cashloan.widget.LoanEditText;

/**
 * LoginFragment
 *
 * @author 贾博瑄
 */

public class LoginFragment extends BaseFragment implements LoginContract.View, TextWatcher {

    private LoginContract.Presenter mPresenter;

    private LoanEditText mEditPhone;

    private LoanEditText mEditPassword;

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
        mEditPassword = root.findViewById(R.id.edit_password);
        mEditPassword.addTextChangedListener(this);
        mEditPassword.setEnabled(false);
        mBtnLogin = root.findViewById(R.id.btn_login);
        /*root.findViewById(R.id.text_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterView();
            }
        });
        root.findViewById(R.id.text_forget_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgetPasswordView();
            }
        });*/
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearError();
                mPresenter.login();
            }
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
            case R.string.error_auth_password:
                mEditPassword.setError(getString(R.string.error_auth_password));
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
        mEditPassword.setEnabled(enable);
    }

    @Override
    public String getPhone() {
        return mEditPhone.getText().toString();
    }

    @Override
    public String getPassword() {
        return mEditPassword.getText().toString();
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    /*显示注册页面*/
    private void showRegisterView() {
        FragmentManager fragmentManager = getFragmentManager();
        RegisterFragment fragment = RegisterFragment.newInstance();
        fragmentManager.beginTransaction().replace(R.id.layout_content, fragment).commit();
    }

    /*显示忘记密码页面*/
    private void showForgetPasswordView() {
        Intent intent = new Intent(getContext(), PasswordActivity.class);
        intent.putExtra("type",0);
        startActivity(intent);
    }

    /*清除错误信息*/
    private void clearError() {
        mEditPhone.setError("");
        mEditPassword.setError("");
    }
}
