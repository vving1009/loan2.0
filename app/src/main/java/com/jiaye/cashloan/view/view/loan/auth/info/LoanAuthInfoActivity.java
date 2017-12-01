package com.jiaye.cashloan.view.view.loan.auth.info;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.data.loan.auth.source.info.LoanAuthInfoRepository;
import com.jiaye.cashloan.view.view.help.LoanAuthHelpActivity;

/**
 * LoanAuthInfoActivity
 *
 * @author 贾博瑄
 */

public class LoanAuthInfoActivity extends BaseActivity implements LoanAuthInfoContract.View {

    private LoanAuthInfoContract.Presenter mPresenter;

    private TextView mTextPerson;

    private TextView mTextContact;

    private Button mBtnNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_auth_info_activity);
        mTextPerson = findViewById(R.id.text_person);
        mTextContact = findViewById(R.id.text_contact);
        mBtnNext = findViewById(R.id.btn_next);
        findViewById(R.id.layout_person_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPersonView();
            }
        });
        findViewById(R.id.layout_contact_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startContactView();
            }
        });
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.img_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoanAuthHelpActivity.show(LoanAuthInfoActivity.this, R.string.loan_auth_info, "personalInfo/personalData");
            }
        });
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        mPresenter = new LoanAuthInfoPresenter(this, new LoanAuthInfoRepository());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPerson(String text) {
        mTextPerson.setText(text);
    }

    @Override
    public void setContact(String text) {
        mTextContact.setText(text);
    }

    @Override
    public void setEnabled() {
        mBtnNext.setEnabled(true);
    }

    private void startPersonView() {
        Intent intent = new Intent(this, LoanAuthPersonInfoActivity.class);
        startActivity(intent);
    }

    private void startContactView() {
        Intent intent = new Intent(this, LoanAuthContactInfoActivity.class);
        startActivity(intent);
    }

    private void next() {
        finish();
    }
}
