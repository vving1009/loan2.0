package com.jiaye.cashloan.view.view.loan.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jiaye.cashloan.R;

/**
 * LoanAuthInfoActivity
 *
 * @author 贾博瑄
 */

public class LoanAuthInfoActivity extends AppCompatActivity {

    public static final int REQUEST_INFO = 203;

    private Button mBtnNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_auth_info_activity);
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
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
    }

    private void startPersonView() {
        Intent intent = new Intent(this,LoanAuthPersonInfoActivity.class);
        startActivity(intent);
    }

    private void startContactView() {
        Intent intent = new Intent(this,LoanAuthContactInfoActivity.class);
        startActivity(intent);
    }

    private void next() {

    }
}
