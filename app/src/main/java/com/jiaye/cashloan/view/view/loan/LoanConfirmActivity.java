package com.jiaye.cashloan.view.view.loan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.data.loan.source.LoanConfirmRepository;

/**
 * LoanConfirmActivity
 *
 * @author 贾博瑄
 */

public class LoanConfirmActivity extends BaseActivity implements LoanConfirmContract.View {

    private LoanConfirmContract.Presenter mPresenter;

    private TextView mTextLoan;

    private TextView mTextService;

    private TextView mTextConsult;

    private TextView mTextInterest;

    private TextView mTextDeadline;

    private TextView mTextPaymentMethod;

    private TextView mTextAmount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_confirm_activity);
        mTextLoan = findViewById(R.id.text_loan);
        mTextService = findViewById(R.id.text_service);
        mTextConsult = findViewById(R.id.text_consult);
        mTextInterest = findViewById(R.id.text_interest);
        mTextDeadline = findViewById(R.id.text_deadline);
        mTextPaymentMethod = findViewById(R.id.text_payment_method);
        mTextAmount = findViewById(R.id.text_amount);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoanConfirmActivity.this, LoanDetailsActivity.class);
                intent.putExtra("title", getString(R.string.loan_approve_title));
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.confirm();
            }
        });
        mPresenter = new LoanConfirmPresenter(this, new LoanConfirmRepository());
        mPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void setLoan(String text) {
        mTextLoan.setText(text);
    }

    @Override
    public void setService(String text) {
        mTextService.setText(text);
    }

    @Override
    public void setConsult(String text) {
        mTextConsult.setText(text);
    }

    @Override
    public void setInterest(String text) {
        mTextInterest.setText(text);
    }

    @Override
    public void setDeadline(String text) {
        mTextDeadline.setText(text);
    }

    @Override
    public void setPaymentMethod(String text) {
        mTextPaymentMethod.setText(text);
    }

    @Override
    public void setAmount(String text) {
        mTextAmount.setText(text);
    }

    @Override
    public void showLoanProgressView(String loanId) {
        Intent intent = new Intent(this, LoanProgressActivity.class);
        intent.putExtra("loanId", loanId);
        startActivity(intent);
        finish();
    }
}
