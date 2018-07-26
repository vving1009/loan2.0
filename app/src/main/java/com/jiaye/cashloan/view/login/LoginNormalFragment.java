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
import com.jiaye.cashloan.view.FunctionActivity;
import com.jiaye.cashloan.view.login.source.LoginNormalRepository;
import com.jiaye.cashloan.widget.LoanEditText;

/**
 * LoginNormalFragment
 *
 * @author 贾博瑄
 */
public class LoginNormalFragment extends BaseFragment implements LoginNormalContract.View {

    private LoginNormalContract.Presenter mPresenter;

    private LoanEditText mEditPhone;

    private LoanEditText mEditPassword;

    private Button mBtnLogin;

    private boolean phoneReady = false;

    private boolean pwReady = false;

    public static LoginNormalFragment newInstance() {
        Bundle args = new Bundle();
        LoginNormalFragment fragment = new LoginNormalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_normal_fragment, container, false);
        mEditPhone = root.findViewById(R.id.edit_phone);
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
        mEditPassword = root.findViewById(R.id.edit_password);
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
        Button btnForget = root.findViewById(R.id.btn_forget_password);
        btnForget.setOnClickListener(v -> {
            showForgetPasswordView();
        });
        mBtnLogin = root.findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(v -> {
            mPresenter.login();
        });
        mPresenter = new LoginNormalPresenter(this, new LoginNormalRepository());
        mPresenter.subscribe();
        return root;
    }

    private void setBtnEnable() {
        if (phoneReady && pwReady) {
            mBtnLogin.setEnabled(true);
        } else {
            mBtnLogin.setEnabled(false);
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
    public String getPassword() {
        return mEditPassword.getText().toString();
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    private void showForgetPasswordView() {
        FunctionActivity.function(getActivity(), "ForgetPassword");
    }
}
