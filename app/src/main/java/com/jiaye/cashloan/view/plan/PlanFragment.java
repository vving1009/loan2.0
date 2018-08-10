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
import com.jiaye.cashloan.http.data.plan.Plan;
import com.jiaye.cashloan.view.BaseFunctionFragment;
import com.jiaye.cashloan.view.plan.source.PlanRepository;

import java.util.List;

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
        View root = inflater.inflate(R.layout.plan_fragment, frameLayout, true);
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
    public void setPlans(List<Plan.Details> list) {
        mAdapter.setPlans(list);
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private List<Plan.Details> mList;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.plan_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setBackground(position);
            holder.setDate(mList.get(position).getDate());
            holder.setPrincipal(mList.get(position).getPrincipal());
            holder.setInterest(mList.get(position).getFinancialCustomerInterest());
            holder.setCharge(mList.get(position).getCharge());
            holder.setRepayment(mList.get(position).getRepayment());
        }

        @Override
        public int getItemCount() {
            if (mList == null || mList.size() == 0) {
                return 0;
            } else {
                return mList.size();
            }
        }

        public void setPlans(List<Plan.Details> list) {
            mList = list;
            notifyDataSetChanged();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mLayout;

        private TextView mTextDate;

        private TextView mTextPrincipal;

        private TextView mTextInterest;

        private TextView mTextCharge;

        private TextView mTextRepayment;

        public ViewHolder(View itemView) {
            super(itemView);
            mLayout = itemView.findViewById(R.id.layout);
            mTextDate = itemView.findViewById(R.id.text_date);
            mTextPrincipal = itemView.findViewById(R.id.text_principal);
            mTextInterest = itemView.findViewById(R.id.text_interest);
            mTextCharge = itemView.findViewById(R.id.text_charge);
            mTextRepayment = itemView.findViewById(R.id.text_repayment);
        }

        public void setBackground(int position) {
            if (position % 2 == 0) {
                mLayout.setBackgroundColor(Color.WHITE);
            } else {
                mLayout.setBackgroundColor(Color.parseColor("#F2F2F2"));
            }
        }

        public void setDate(String date) {
            mTextDate.setText(date);
        }

        public void setPrincipal(String principal) {
            mTextPrincipal.setText(principal);
        }

        public void setInterest(String interest) {
            mTextInterest.setText(interest);
        }

        public void setCharge(String charge) {
            mTextCharge.setText(charge);
        }

        public void setRepayment(String repayment) {
            mTextRepayment.setText(repayment);
        }
    }
}
