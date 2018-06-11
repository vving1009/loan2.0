package com.jiaye.cashloan.view.plan;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.LoanPlan;
import com.jiaye.cashloan.view.BaseFunctionFragment;
import com.jiaye.cashloan.view.plan.source.PlanRepository;

/**
 * PlanFragment
 *
 * @author 贾博瑄
 */

public class PlanFragment extends BaseFunctionFragment implements PlanContract.View {

    private PlanContract.Presenter mPresenter;

    private Adapter mAdapter;

    public static PlanFragment newInstance() {
        Bundle args = new Bundle();
        PlanFragment fragment = new PlanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getTitleId() {
        return R.string.loan_plan_title;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View root = inflater.inflate(R.layout.loan_plan_activity, frameLayout, true);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new Adapter();
        recyclerView.setAdapter(mAdapter);
        mPresenter = new PlanPresenter(this, new PlanRepository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.loan_plan_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setBackground(position);
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

        private LinearLayout mLayout;

        private TextView mTextDate;

        private TextView mTextMoney;

        private TextView mTextState;

        public ViewHolder(View itemView) {
            super(itemView);
            mLayout = itemView.findViewById(R.id.layout);
            mTextState = itemView.findViewById(R.id.text_state);
            mTextMoney = itemView.findViewById(R.id.text_money);
            mTextDate = itemView.findViewById(R.id.text_date);
        }

        public void setBackground(int position) {
            if (position % 2 == 0) {
                mLayout.setBackgroundColor(Color.WHITE);
            } else {
                mLayout.setBackgroundColor(Color.parseColor("#E6E6E6"));
            }
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
