package com.jiaye.cashloan.view.view.loan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.LoanDetails;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.data.loan.source.LoanDetailsRepository;

import java.util.List;

/**
 * LoanApproveActivity
 *
 * @author 贾博瑄
 */

public class LoanDetailsActivity extends BaseActivity implements LoanDetailsContract.View {

    private LoanDetailsContract.Presenter mPresenter;

    private Adapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getExtras().getString("title");
        setContentView(R.layout.loan_details_activity);
        TextView textTitle = findViewById(R.id.text_title);
        textTitle.setText(title);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new Adapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mPresenter = new LoanDetailsPresenter(this, new LoanDetailsRepository());
        mPresenter.subscribe();
        if (title.equals(getString(R.string.loan_approve_title))) {
            mPresenter.requestDetails();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void setList(List<LoanDetails> list) {
        mAdapter.setList(list);
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private List<LoanDetails> mList;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(LoanDetailsActivity.this).inflate(R.layout.loan_details_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setTitle(mList.get(position).getName());
            holder.setDate(mList.get(position).getDate());
            holder.setLoan(mList.get(position).getLoan());
            holder.setOther(mList.get(position).getOther());
            holder.setPaymentMethod(mList.get(position).getPaymentMethod());
            holder.setAmount(mList.get(position).getAmount());
            holder.setCurrentAmount(mList.get(position).getCurrentAmount());
            holder.setDeadline(mList.get(position).getDeadline());
            holder.setStatus(mList.get(position).getStatus());
        }

        @Override
        public int getItemCount() {
            if (mList != null) {
                return mList.size();
            } else {
                return 0;
            }
        }

        public void setList(List<LoanDetails> list) {
            mList = list;
            notifyDataSetChanged();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextTitle;

        private TextView mTextDate;

        private TextView mTextLoan;

        private TextView mTextOther;

        private TextView mTextPaymentMethod;

        private TextView mTextAmount;

        private TextView mTextCurrentAmount;

        private TextView mTextDeadline;

        private TextView mTextStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextTitle = itemView.findViewById(R.id.text_title);
            mTextDate = itemView.findViewById(R.id.text_date);
            mTextLoan = itemView.findViewById(R.id.text_loan);
            mTextOther = itemView.findViewById(R.id.text_other);
            mTextPaymentMethod = itemView.findViewById(R.id.text_payment_method);
            mTextAmount = itemView.findViewById(R.id.text_amount);
            mTextCurrentAmount = itemView.findViewById(R.id.text_current_amount);
            mTextDeadline = itemView.findViewById(R.id.text_deadline);
            mTextStatus = itemView.findViewById(R.id.text_status);
        }

        public void setTitle(String text) {
            mTextTitle.setText(text);
        }

        public void setDate(String text) {
            mTextDate.setText(text);
        }

        public void setLoan(String text) {
            mTextLoan.setText(text);
        }

        public void setOther(String text) {
            mTextOther.setText(text);
        }

        public void setPaymentMethod(String text) {
            mTextPaymentMethod.setText(text);
        }

        public void setAmount(String text) {
            mTextAmount.setText(text);
        }

        public void setCurrentAmount(String text) {
            mTextCurrentAmount.setText(text);
        }

        public void setDeadline(String text) {
            mTextDeadline.setText(text);
        }

        public void setStatus(String text) {
            mTextStatus.setText(text);
        }
    }
}
