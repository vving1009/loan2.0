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
import com.jiaye.cashloan.http.data.loan.LoanPlan;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.data.loan.source.LoanPlanRepository;

/**
 * LoanPlanActivity
 *
 * @author 贾博瑄
 */

public class LoanPlanActivity extends BaseActivity implements LoanPlanContract.View {

    private LoanPlanContract.Presenter mPresenter;

    private Adapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_plan_activity);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new Adapter();
        recyclerView.setAdapter(mAdapter);
        mPresenter = new LoanPlanPresenter(this, new LoanPlanRepository());
        mPresenter.subscribe();
        //noinspection ConstantConditions
        mPresenter.requestLoanPlan(getIntent().getExtras().getString("loanId"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPlans(LoanPlan.Plan[] plans) {
        mAdapter.setPlans(plans);
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private LoanPlan.Plan[] mPlans;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(LoanPlanActivity.this).inflate(R.layout.loan_plan_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setDate(mPlans[position].getDate());
            holder.setMoney(mPlans[position].getMoney());
            holder.setState(mPlans[position].getStatus());
        }

        @Override
        public int getItemCount() {
            if (mPlans == null) {
                return 0;
            } else {
                return mPlans.length;
            }
        }

        public void setPlans(LoanPlan.Plan[] plans) {
            mPlans = plans;
            notifyDataSetChanged();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextDate;

        private TextView mTextMoney;

        private TextView mTextState;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextState = itemView.findViewById(R.id.text_state);
            mTextMoney = itemView.findViewById(R.id.text_money);
            mTextDate = itemView.findViewById(R.id.text_date);
        }

        public void setDate(String date) {
            mTextDate.setText(date);
        }

        public void setState(String state) {
            mTextState.setText(state);
        }

        public void setMoney(String money) {
            mTextMoney.setText(money);
        }
    }
}
