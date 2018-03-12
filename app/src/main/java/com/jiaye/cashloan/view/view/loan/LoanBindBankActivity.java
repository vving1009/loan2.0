package com.jiaye.cashloan.view.view.loan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.data.loan.source.LoanBindBankRepository;
import com.jiaye.cashloan.widget.LoanEditText;

/**
 * LoanBindBankActivity
 *
 * @author 贾博瑄
 */

public class LoanBindBankActivity extends BaseActivity implements LoanBindBankContract.View {

    private LoanBindBankContract.Presenter mPresenter;

    private TextView mTextName;

    private EditText mEditPhone;

    private EditText mEditBank;

    private EditText mEditNumber;

    private LoanEditText mEditSMS;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_bind_bank_activity);
        mTextName = findViewById(R.id.text_person);
        mEditPhone = findViewById(R.id.edit_phone);
        mEditBank = findViewById(R.id.edit_bank);
        mEditNumber = findViewById(R.id.edit_number);
        mEditSMS = findViewById(R.id.edit_sms_verification_code);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.submit();
            }
        });
        mEditSMS.setOnClickVerificationCode(new LoanEditText.OnClickVerificationCode() {
            @Override
            public void onClickVerificationCode() {
                mPresenter.requestSMS();
            }
        });
        findViewById(R.id.text_support).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSupportBankView();
            }
        });
        mPresenter = new LoanBindBankPresenter(this, new LoanBindBankRepository());
        mPresenter.subscribe();
        //noinspection ConstantConditions
        mPresenter.setSource(getIntent().getExtras().getString("source", ""));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void setName(String text) {
        mTextName.setText(text);
    }

    @Override
    public String getPhone() {
        return mEditPhone.getText().toString();
    }

    @Override
    public String getBank() {
        return mEditBank.getText().toString();
    }

    @Override
    public String getNumber() {
        return mEditNumber.getText().toString();
    }

    @Override
    public String getSMS() {
        return mEditSMS.getText().toString();
    }

    @Override
    public void startCountDown() {
        mEditSMS.startCountDown();
    }

    @Override
    public void result() {
        finish();
    }

    private void showSupportBankView() {
        Intent intent = new Intent(this, LoanSupportBankActivity.class);
        startActivity(intent);
    }
}
