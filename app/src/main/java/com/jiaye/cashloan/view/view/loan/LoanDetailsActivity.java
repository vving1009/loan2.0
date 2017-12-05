package com.jiaye.cashloan.view.view.loan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.data.loan.source.LoanDetailsRepository;

/**
 * LoanApproveActivity
 *
 * @author 贾博瑄
 */

public class LoanDetailsActivity extends BaseActivity implements LoanDetailsContract.View {

    private LoanDetailsContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_details_activity);
        TextView textTitle = findViewById(R.id.text_title);
        textTitle.setText(getIntent().getExtras().getString("title"));
        mPresenter = new LoanDetailsPresenter(this, new LoanDetailsRepository());
        mPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }
}
