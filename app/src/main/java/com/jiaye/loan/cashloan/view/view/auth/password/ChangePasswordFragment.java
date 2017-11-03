package com.jiaye.loan.cashloan.view.view.auth.password;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiaye.loan.cashloan.R;
import com.jiaye.loan.cashloan.view.BaseFragment;
import com.jiaye.loan.cashloan.widget.LoanEditText;

/**
 * ChangePasswordFragment
 *
 * @author 贾博瑄
 */

public class ChangePasswordFragment extends BaseFragment implements ChangePasswordContract.View {

    private ChangePasswordContract.Presenter mPresenter;

    private LoanEditText mEditPassword;

    private LoanEditText mEditPasswordSecond;

    public static ChangePasswordFragment newInstance(String phone) {
        Bundle args = new Bundle();
        args.putString("phone", phone);
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.change_password_fragment, container, false);
        mEditPassword = (LoanEditText) root.findViewById(R.id.edit_password);
        mEditPasswordSecond = (LoanEditText) root.findViewById(R.id.edit_password_second);
        root.findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        root.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearError();
                mPresenter.save();
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
    public void setPresenter(ChangePasswordContract.Presenter presenter) {
        mPresenter = presenter;
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
