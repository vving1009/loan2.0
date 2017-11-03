package com.jiaye.loan.cashloan.view.view.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiaye.loan.cashloan.R;
import com.jiaye.loan.cashloan.view.BaseFragment;
import com.jiaye.loan.cashloan.view.data.auth.register.source.RegisterRepository;
import com.jiaye.loan.cashloan.view.view.auth.password.PasswordActivity;
import com.jiaye.loan.cashloan.view.view.auth.register.RegisterFragment;
import com.jiaye.loan.cashloan.view.view.auth.register.RegisterPresenter;
import com.jiaye.loan.cashloan.widget.LoanEditText;

/**
 * LoginFragment
 *
 * @author 贾博瑄
 */

public class LoginFragment extends BaseFragment implements LoginContract.View {

    private LoginContract.Presenter mPresenter;

    private LoanEditText mEditPhone;

    private LoanEditText mEditPassword;

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
        mEditPhone = (LoanEditText) root.findViewById(R.id.edit_phone);
        mEditPassword = (LoanEditText) root.findViewById(R.id.edit_password);
        root.findViewById(R.id.text_register).setOnClickListener(new View.OnClickListener() {
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
        });
        root.findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearError();
                mPresenter.login();
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

    /*显示注册页面*/
    private void showRegisterView() {
        FragmentManager fragmentManager = getFragmentManager();
        RegisterFragment fragment = RegisterFragment.newInstance();
        fragmentManager.beginTransaction().replace(R.id.layout_content, fragment).commit();
        new RegisterPresenter(fragment, new RegisterRepository());
    }

    /*显示忘记密码页面*/
    private void showForgetPasswordView() {
        Intent intent = new Intent(getContext(), PasswordActivity.class);
        startActivity(intent);
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
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
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

    /*清除错误信息*/
    private void clearError() {
        mEditPhone.setError("");
        mEditPassword.setError("");
    }
}
