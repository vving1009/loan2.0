package com.jiaye.cashloan.view.view.loan.auth.sesame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.data.loan.auth.source.sesame.LoanAuthSesameRepository;

/**
 * LoanAuthSesameActivity
 *
 * @author 贾博瑄
 */

public class LoanAuthSesameActivity extends BaseActivity implements LoanAuthSesameContract.View {

    private LoanAuthSesameContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_auth_sesame_activity);
        findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.request();
            }
        });
        mPresenter = new LoanAuthSesamePresenter(this, new LoanAuthSesameRepository());
        mPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void result() {
        finish();
    }
}
