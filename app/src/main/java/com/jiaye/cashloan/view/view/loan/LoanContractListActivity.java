package com.jiaye.cashloan.view.view.loan;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.ContractList;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.data.loan.source.LoanContractListRepository;

/**
 * LoanContractListActivity
 *
 * @author 贾博瑄
 */

public class LoanContractListActivity extends BaseActivity implements LoanContractListContract.View {

    private LoanContractListContract.Presenter mPresenter;

    private Adapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_contract_list_activity);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TextView textId = findViewById(R.id.text_id);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new Adapter();
        recyclerView.setAdapter(mAdapter);
        mPresenter = new LoanContractListPresenter(this, new LoanContractListRepository());
        mPresenter.subscribe();
        //noinspection ConstantConditions
        textId.setText(String.format(getString(R.string.loan_contract_list_id), getIntent().getExtras().getString("loanId")));
        mPresenter.requestContractList(getIntent().getExtras().getString("loanId"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void setContracts(ContractList.Contract[] contracts) {
        mAdapter.setContracts(contracts);
    }

    private void showContractView(String contractId) {
        Intent intent = new Intent(this, LoanContractActivity.class);
        intent.putExtra("contractId", contractId);
        startActivity(intent);
    }

    public interface OnClickViewHolderListener {

        void OnClickViewHolder(ViewHolder viewHolder);
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private ContractList.Contract[] mContracts;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(LoanContractListActivity.this).inflate(R.layout.loan_contract_list_item, parent, false);
            return new ViewHolder(view, new OnClickViewHolderListener() {
                @Override
                public void OnClickViewHolder(ViewHolder viewHolder) {
                    showContractView(mContracts[viewHolder.getLayoutPosition()].getId());
                }
            });
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setBackground(position);
            holder.setName(mContracts[position].getName());
        }

        @Override
        public int getItemCount() {
            if (mContracts == null) {
                return 0;
            } else {
                return mContracts.length;
            }
        }

        public void setContracts(ContractList.Contract[] contracts) {
            mContracts = contracts;
            notifyDataSetChanged();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private OnClickViewHolderListener mListener;

        private RelativeLayout mLayout;

        private TextView mTextName;

        public ViewHolder(View itemView, OnClickViewHolderListener listener) {
            super(itemView);
            mListener = listener;
            mLayout = itemView.findViewById(R.id.layout);
            mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.OnClickViewHolder(ViewHolder.this);
                    }
                }
            });
            mTextName = itemView.findViewById(R.id.text_name);
        }

        public void setBackground(int position) {
            if (position % 2 == 0) {
                mLayout.setBackgroundColor(Color.WHITE);
            } else {
                mLayout.setBackgroundColor(Color.parseColor("#E6E6E6"));
            }
        }

        public void setName(String name) {
            mTextName.setText(name);
        }
    }
}
