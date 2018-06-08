package com.jiaye.cashloan.view.phone;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseDialog;
import com.jiaye.cashloan.view.BaseFunctionFragment;
import com.jiaye.cashloan.view.phone.source.PhoneRepository;
import com.jiaye.cashloan.widget.CustomProgressDialog;
import com.jiaye.cashloan.widget.LoanEditText;

import java.util.ArrayList;

/**
 * PhoneFragment
 *
 * @author 贾博瑄
 */

public class PhoneFragment extends BaseFunctionFragment implements PhoneContract.View {

    private PhoneContract.Presenter mPresenter;

    private TextView mTextPhone;

    private TextView mTextOperators;

    private LoanEditText mEditPassword;

    private LinearLayout mLayoutEdit;

    private TextView mTextForgetPassword;

    private BaseDialog mForgetPasswordDialog;

    private ArrayList<LoanEditText> mSmsArray;

    private ArrayList<LoanEditText> mImgArray;

    private CustomProgressDialog mCustomProgressDialog;

    private int mSmsIndex = -1;

    private int mImgIndex = -1;

    private boolean showCustomDialog = false;

    public static PhoneFragment newInstance() {
        Bundle args = new Bundle();
        PhoneFragment fragment = new PhoneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.phone_fragment, frameLayout, true);
        mTextPhone = rootView.findViewById(R.id.text_phone);
        mTextOperators = rootView.findViewById(R.id.text_operators);
        mEditPassword = rootView.findViewById(R.id.edit_code);
        mLayoutEdit = rootView.findViewById(R.id.layout_edit);
        mTextForgetPassword = rootView.findViewById(R.id.text_forget_password);
        mTextForgetPassword.setOnClickListener(v -> mForgetPasswordDialog.show());
        rootView.findViewById(R.id.btn_commit).setOnClickListener(v -> {
            showCustomDialog = true;
            mPresenter.submit();
        });
        mForgetPasswordDialog = new BaseDialog(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.forget_password_dialog_layout, null);
        view.findViewById(R.id.text).setOnClickListener(v -> mForgetPasswordDialog.dismiss());
        mForgetPasswordDialog.setContentView(view);
        mSmsArray = new ArrayList<>();
        mImgArray = new ArrayList<>();
        mPresenter = new PhonePresenter(this, new PhoneRepository());
        mPresenter.subscribe();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPhone(String text) {
        mTextPhone.setText(text);
    }

    @Override
    public void setOperators(String text) {
        mTextOperators.setText(text);
    }

    @Override
    public void setPasswordVisibility(int visibility) {
        mEditPassword.setVisibility(visibility);
    }

    @Override
    public void setForgetPasswordVisibility(int visibility) {
        mTextForgetPassword.setVisibility(visibility);
    }

    @Override
    public void addSms() {
        mSmsIndex++;
        LoanEditText editSms = (LoanEditText) LayoutInflater.from(getContext())
                .inflate(R.layout.loan_auth_phone_sms, null, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,
                (int) (16 * getResources().getDisplayMetrics().density),
                0,
                0);
        editSms.setLayoutParams(layoutParams);
        mLayoutEdit.addView(editSms);
        editSms.setOnClickVerificationCode(() -> mPresenter.requestSMSVerification());
        mSmsArray.add(editSms);
        for (int i = 0; i <= mSmsIndex; i++) {
            if (i - 1 > 0) {
                if (!TextUtils.isEmpty(mSmsArray.get(i - 1).getText().toString())) {
                    mSmsArray.get(i - 1).setEnabled(false);
                }
            }
        }
        for (int i = 0; i <= mImgIndex; i++) {
            if (!TextUtils.isEmpty(mImgArray.get(i).getText().toString())) {
                mImgArray.get(i).setEnabled(false);
            }
        }
    }

    @Override
    public void addImg() {
        mImgIndex++;
        LoanEditText editImg = (LoanEditText) LayoutInflater.from(getContext())
                .inflate(R.layout.loan_auth_phone_img, null, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,
                (int) (16 * getResources().getDisplayMetrics().density),
                0,
                0);
        editImg.setLayoutParams(layoutParams);
        mLayoutEdit.addView(editImg);
        editImg.setOnClickVerificationCode(() -> mPresenter.requestIMGVerification());
        mImgArray.add(editImg);
        for (int i = 0; i <= mImgIndex; i++) {
            if (i - 1 > 0) {
                if (!TextUtils.isEmpty(mImgArray.get(i - 1).getText().toString())) {
                    mImgArray.get(i - 1).setEnabled(false);
                }
            }
        }
        for (int i = 0; i <= mSmsIndex; i++) {
            if (!TextUtils.isEmpty(mSmsArray.get(i).getText().toString())) {
                mSmsArray.get(i).setEnabled(false);
            }
        }
    }

    @Override
    public void firstSubmit() {
        mEditPassword.setEnabled(false);
    }

    @Override
    public void setImgVerificationCode(Bitmap bitmap) {
        mImgArray.get(mImgIndex).setCode(bitmap);
    }

    @Override
    public void setSmsVerificationCodeCountDown() {
        mSmsArray.get(mSmsIndex).startCountDown();
    }

    @Override
    public String getPassword() {
        return mEditPassword.getText().toString();
    }

    @Override
    public String getSmsVerificationCode() {
        if (mSmsIndex == -1) {
            return "";
        } else {
            return mSmsArray.get(mSmsIndex).getText().toString();
        }
    }

    @Override
    public String getImgVerificationCode() {
        if (mImgIndex == -1) {
            return "";
        } else {
            return mImgArray.get(mImgIndex).getText().toString();
        }
    }

    @Override
    public boolean isSmsVerificationCodeVisibility() {
        return mSmsIndex != -1 && mSmsArray.get(mSmsIndex).getVisibility() == View.VISIBLE;
    }

    @Override
    public boolean isImgVerificationCodeVisibility() {
        return mImgIndex != -1 && mImgArray.get(mImgIndex).getVisibility() == View.VISIBLE;
    }

    @Override
    public void result() {
        getActivity().finish();
    }

    @Override
    public void showProgressDialog() {
        if (showCustomDialog) {
            if (mCustomProgressDialog == null) {
                mCustomProgressDialog = new CustomProgressDialog(getContext());
            }
            mCustomProgressDialog.show("认证中, 预计等待2-3分钟");
        } else {
            super.showProgressDialog();
        }
    }

    @Override
    public void dismissProgressDialog() {
        if (mCustomProgressDialog != null && mCustomProgressDialog.isShowing()) {
            mCustomProgressDialog.dismiss();
            showCustomDialog = false;
        } else {
            super.dismissProgressDialog();
        }
    }

    @Override
    protected int getTitleId() {
        return R.string.phone_title;
    }
}
