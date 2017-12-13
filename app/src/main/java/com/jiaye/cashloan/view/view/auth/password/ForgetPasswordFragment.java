package com.jiaye.cashloan.view.view.auth.password;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.auth.password.source.ForgetPasswordRepository;
import com.jiaye.cashloan.widget.LoanEditText;

/**
 * ForgetPassword
 *
 * @author 贾博瑄
 */

public class ForgetPasswordFragment extends BaseFragment implements ForgetPasswordContract.View, TextWatcher {

    private ForgetPasswordContract.Presenter mPresenter;

    private LoanEditText mEditPhone;

    private LoanEditText mEditSmsVerificationCode;

    private Button mBtmNext;

    public static ForgetPasswordFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.forget_password_fragment, container, false);
        TextView textView = root.findViewById(R.id.text_title);
        switch (getArguments().getInt("type")) {
            case 0:
                textView.setText(getString(R.string.forget_password_title));
                break;
            case 1:
                textView.setText(getString(R.string.settings_password_title));
                break;
        }
        mEditPhone = root.findViewById(R.id.edit_phone);
        mEditPhone.addTextChangedListener(this);
        mEditSmsVerificationCode = root.findViewById(R.id.edit_sms_verification_code);
        mEditSmsVerificationCode.addTextChangedListener(this);
        mBtmNext = root.findViewById(R.id.btn_next);
        root.findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        mBtmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearError();
                mPresenter.next();
            }
        });
        mEditSmsVerificationCode.setOnClickVerificationCode(new LoanEditText.OnClickVerificationCode() {
            @Override
            public void onClickVerificationCode() {
                clearError();
                mPresenter.verificationCode();
            }
        });
        mPresenter = new ForgetPasswordPresenter(this, new ForgetPasswordRepository());
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
    public void setPhone(String text) {
        mEditPhone.setText(text);
    }

    @Override
    public void setEnable(boolean enable) {
        mBtmNext.setEnabled(enable);
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
        ChangePasswordFragment fragment = ChangePasswordFragment.newInstance(getArguments().getInt("type"), mEditPhone.getText().toString());
        fragmentManager.beginTransaction().replace(R.id.layout_content, fragment).commit();
    }

    /*清除错误信息*/
    private void clearError() {
        mEditPhone.setError("");
        mEditSmsVerificationCode.setError("");
    }
}
