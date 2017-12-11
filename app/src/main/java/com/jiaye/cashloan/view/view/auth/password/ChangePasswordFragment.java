package com.jiaye.cashloan.view.view.auth.password;

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
import com.jiaye.cashloan.widget.LoanEditText;

/**
 * ChangePasswordFragment
 *
 * @author 贾博瑄
 */

public class ChangePasswordFragment extends BaseFragment implements ChangePasswordContract.View, TextWatcher {

    private ChangePasswordContract.Presenter mPresenter;

    private LoanEditText mEditPassword;

    private LoanEditText mEditPasswordSecond;

    private Button mBtnSave;

    public static ChangePasswordFragment newInstance(int type, String phone) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putString("phone", phone);
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.change_password_fragment, container, false);
        mEditPassword = root.findViewById(R.id.edit_password);
        mEditPassword.addTextChangedListener(this);
        mEditPasswordSecond = root.findViewById(R.id.edit_password_second);
        mEditPasswordSecond.addTextChangedListener(this);
        mBtnSave = root.findViewById(R.id.btn_save);
        root.findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearError();
                mPresenter.save();
            }
        });
        mPresenter = new ChangePasswordPresenter(this);
        mPresenter.subscribe();
        mPresenter.setType(getArguments().getInt("type"));
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
            case R.string.error_auth_password:
                mEditPassword.setError(getString(R.string.error_auth_password));
                break;
            case R.string.error_auth_password_different:
                mEditPasswordSecond.setError(getString(R.string.error_auth_password_different));
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
    public void setEnable(boolean enable) {
        mBtnSave.setEnabled(enable);
    }

    @Override
    public String getPhone() {
        return getArguments().getString("phone");
    }

    @Override
    public String getPassword() {
        return mEditPassword.getText().toString();
    }

    @Override
    public String getPasswordSecond() {
        return mEditPasswordSecond.getText().toString();
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    /*清除错误信息*/
    private void clearError() {
        mEditPassword.setError("");
        mEditPasswordSecond.setError("");
    }
}
