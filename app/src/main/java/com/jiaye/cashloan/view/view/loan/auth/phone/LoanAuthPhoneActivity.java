package com.jiaye.cashloan.view.view.loan.auth.phone;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.data.loan.auth.source.phone.LoanAuthPhoneRepository;
import com.jiaye.cashloan.view.view.help.LoanAuthHelpActivity;
import com.jiaye.cashloan.widget.LoanEditText;

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

    private TextView mTextForgetPassword;

    private LoanEditText mEditSmsVerificationCode;

    private LoanEditText mEditImgVerificationCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_auth_phone_activity);
        mTextPhone = findViewById(R.id.text_phone);
        mTextOperators = findViewById(R.id.text_operators);
        mEditPassword = findViewById(R.id.edit_password);
        mTextForgetPassword = findViewById(R.id.text_forget_password);
        mEditSmsVerificationCode = findViewById(R.id.edit_sms_verification_code);
        mEditImgVerificationCode = findViewById(R.id.edit_img_verification_code);
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
                mPresenter.forgetPassword();
            }
        });
        mEditSmsVerificationCode.setOnClickVerificationCode(new LoanEditText.OnClickVerificationCode() {
            @Override
            public void onClickVerificationCode() {
                mPresenter.requestSMSVerification();
            }
        });
        mEditImgVerificationCode.setOnClickVerificationCode(new LoanEditText.OnClickVerificationCode() {
            @Override
            public void onClickVerificationCode() {
                mPresenter.requestIMGVerification();
            }
        });
        findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.submit();
            }
        });
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
    public void setImgVerificationCodeVisibility(int visibility) {
        mEditImgVerificationCode.setVisibility(visibility);
    }

    @Override
    public void setSmsVerificationCodeVisibility(int visibility) {
        mEditSmsVerificationCode.setVisibility(visibility);
    }

    @Override
    public void setImgVerificationCode(Bitmap bitmap) {
        mEditImgVerificationCode.setCode(bitmap);
    }

    @Override
    public void setSmsVerificationCodeCountDown() {
        mEditSmsVerificationCode.startCountDown();
    }

    @Override
    public void cleanImgVerificationCodeText() {
        mEditImgVerificationCode.setText("");
    }

    @Override
    public void cleanSmsVerificationCodeText() {
        mEditSmsVerificationCode.setText("");
    }

    @Override
    public String getPassword() {
        return mEditPassword.getText().toString();
    }

    @Override
    public String getSmsVerificationCode() {
        return mEditSmsVerificationCode.getText().toString();
    }

    @Override
    public String getImgVerificationCode() {
        return mEditImgVerificationCode.getText().toString();
    }

    @Override
    public boolean isSmsVerificationCodeVisibility() {
        return mEditSmsVerificationCode.getVisibility() == View.VISIBLE;
    }

    @Override
    public boolean isImgVerificationCodeVisibility() {
        return mEditImgVerificationCode.getVisibility() == View.VISIBLE;
    }

    @Override
    public void result() {
        finish();
    }
}
