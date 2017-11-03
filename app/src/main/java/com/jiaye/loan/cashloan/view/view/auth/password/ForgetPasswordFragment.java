package com.jiaye.loan.cashloan.view.view.auth.password;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiaye.loan.cashloan.R;
import com.jiaye.loan.cashloan.view.BaseFragment;
import com.jiaye.loan.cashloan.widget.LoanEditText;

/**
 * ForgetPassword
 *
 * @author 贾博瑄
 */

public class ForgetPasswordFragment extends BaseFragment implements ForgetPasswordContract.View {

    private ForgetPasswordContract.Presenter mPresenter;

    private LoanEditText mEditPhone;

    private LoanEditText mEditSmsVerificationCode;

    public static ForgetPasswordFragment newInstance() {
        Bundle args = new Bundle();
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.forget_password_fragment, container, false);
        mEditPhone = (LoanEditText) root.findViewById(R.id.edit_phone);
        mEditSmsVerificationCode = (LoanEditText) root.findViewById(R.id.edit_sms_verification_code);
        root.findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        root.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearError();
                mPresenter.next();
            }
        });
        mEditSmsVerificationCode.setOnClickVerificationCode(new LoanEditText.OnClickVerificationCode() {
            @Override
            public void onClickCaptcha() {
                clearError();
                mPresenter.verificationCode();
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
            case R.string.error_auth_phone:
                mEditPhone.setError(getString(R.string.error_auth_phone));
                break;
            case R.string.error_auth_sms_verification:
                mEditSmsVerificationCode.setError(getString(R.string.error_auth_sms_verification));
                break;
            default:
                super.showToastById(resId);
                break;
        }
    }

    @Override
    public void setPresenter(ForgetPasswordContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public String getPhone() {
        return mEditPhone.getText().toString();
    }

    @Override
    public String getInputSmsVerificationCode() {
        return mEditSmsVerificationCode.getText().toString();
    }

    @Override
    public void smsVerificationCodeCountDown() {
        mEditSmsVerificationCode.startCountDown();
    }

    @Override
    public void showChangePasswordView() {
        FragmentManager fragmentManager = getFragmentManager();
        ChangePasswordFragment fragment = ChangePasswordFragment.newInstance(mEditPhone.getText().toString());
        fragmentManager.beginTransaction().replace(R.id.layout_content, fragment).commit();
        new ChangePasswordPresenter(fragment, null);
    }

    /*清除错误信息*/
    private void clearError() {
        mEditPhone.setError("");
        mEditSmsVerificationCode.setError("");
    }
}
