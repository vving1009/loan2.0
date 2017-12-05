package com.jiaye.cashloan.view.view.loan;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseActivity;

/**
 * LoanProgressActivity
 *
 * @author 贾博瑄
 */

public class LoanProgressActivity extends BaseActivity implements LoanProgressContract.View {

    private LoanProgressContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_progress_activity);
        mPresenter = new LoanProgressPresenter(this);
        mPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }
}
