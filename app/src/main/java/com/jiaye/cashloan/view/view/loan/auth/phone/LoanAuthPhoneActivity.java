package com.jiaye.cashloan.view.view.loan.auth.phone;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.BaseDialog;
import com.jiaye.cashloan.view.data.loan.auth.source.phone.LoanAuthPhoneRepository;
import com.jiaye.cashloan.view.view.help.LoanAuthHelpActivity;
import com.jiaye.cashloan.widget.CustomProgressDialog;
import com.jiaye.cashloan.widget.LoanEditText;

import java.util.ArrayList;

/**
 * LoanAuthPhoneActivity
 *
 * @author 贾博瑄
 */

public class LoanAuthPhoneActivity extends BaseActivity implements LoanAuthPhoneContract.View {

    private LoanAuthPhoneContract.Presenter mPresenter;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_auth_phone_activity);
        mTextPhone = findViewById(R.id.text_phone);
        mTextOperators = findViewById(R.id.text_operators);
        mEditPassword = findViewById(R.id.edit_code);
        mLayoutEdit = findViewById(R.id.layout_edit);
        mTextForgetPassword = findViewById(R.id.text_forget_password);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.img_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoanAuthHelpActivity.show(LoanAuthPhoneActivity.this, R.string.loan_auth_phone, "phoneOperator/operator");
            }
        });
        mTextForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mForgetPasswordDialog.show();
            }
        });
        findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog = true;
                mPresenter.submit();
            }
        });
        mForgetPasswordDialog = new BaseDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.forget_password_dialog_layout, null);
        view.findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mForgetPasswordDialog.dismiss();
            }
        });
        mForgetPasswordDialog.setContentView(view);
        mSmsArray = new ArrayList<>();
        mImgArray = new ArrayList<>();
        mPresenter = new LoanAuthPhonePresenter(this, new LoanAuthPhoneRepository());
        mPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
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
        LoanEditText editSms = (LoanEditText) LayoutInflater.from(this)
                .inflate(R.layout.loan_auth_phone_sms, null, false);
        mLayoutEdit.addView(editSms);
        editSms.setOnClickVerificationCode(new LoanEditText.OnClickVerificationCode() {
            @Override
            public void onClickVerificationCode() {
                mPresenter.requestSMSVerification();
            }
        });
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
        LoanEditText editImg = (LoanEditText) LayoutInflater.from(this)
                .inflate(R.layout.loan_auth_phone_img, null, false);
        mLayoutEdit.addView(editImg);
        editImg.setOnClickVerificationCode(new LoanEditText.OnClickVerificationCode() {
            @Override
            public void onClickVerificationCode() {
                mPresenter.requestIMGVerification();
            }
        });
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
        finish();
    }

    @Override
    public void showProgressDialog() {
        if (showCustomDialog) {
            if (mCustomProgressDialog == null) {
                mCustomProgressDialog = new CustomProgressDialog(this);
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
}
