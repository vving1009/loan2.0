package com.jiaye.cashloan.view.view.loan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.data.loan.source.LoanAuthRepository;

/**
 * LoanAuthActivity
 *
 * @author 贾博瑄
 */

public class LoanAuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_auth_activity);
        FragmentManager fragmentManager = getSupportFragmentManager();
        LoanAuthFragment fragment = LoanAuthFragment.newInstance();
        fragmentManager.beginTransaction().replace(R.id.layout_content, fragment).commit();
        new LoanAuthPresenter(fragment, new LoanAuthRepository());
    }
}
