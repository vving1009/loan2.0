package com.jiaye.cashloan.view.view.loan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.LoanApply;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.data.loan.source.LoanDetailsRepository;

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
        if (title.equals(getString(R.string.loan_details_approve_title))) {
            mPresenter.requestDetails("01");
        } else if (title.equals(getString(R.string.loan_details_progress_title))) {
            mPresenter.requestDetails("02");
        } else if (title.equals(getString(R.string.loan_details_history_title))) {
            mPresenter.requestDetails("03");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void setList(LoanApply.Card[] cards) {
        mAdapter.setList(cards);
    }

    private void showLoanProgressView(String loanId) {
        Intent intent = new Intent(this, LoanProgressActivity.class);
        intent.putExtra("loanId", loanId);
        startActivity(intent);
    }

    private void showLoanPlanView(String loanId) {
        Intent intent = new Intent(this, LoanPlanActivity.class);
        intent.putExtra("loanId", loanId);
        startActivity(intent);
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private LoanApply.Card[] mCards;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
            View view = LayoutInflater.from(LoanDetailsActivity.this).inflate(R.layout.loan_details_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.setListener(new ViewHolder.OnClickViewHolderListener() {
                @Override
                public void onClickProgress(ViewHolder viewHolder) {
                    showLoanProgressView(mCards[viewHolder.getLayoutPosition()].getId());
                }

                @Override
                public void onClickPlan(ViewHolder viewHolder) {
                    showLoanPlanView(mCards[viewHolder.getLayoutPosition()].getId());
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setTitle(mCards[position].getId());
            holder.setDate(mCards[position].getDate());
            holder.setName(mCards[position].getName());
            holder.setApprove(mCards[position].getApproveNum());
            holder.setTotal(mCards[position].getCompactNum());
            holder.setLoan(mCards[position].getLoanNum());
            holder.setOther(mCards[position].getFee());
            holder.setDeadline(mCards[position].getReturnDate());
            holder.setStatus(mCards[position].getReturnState());
            holder.setPlanEnabled(mCards[position].getPlan().equals("1"));
        }

        @Override
        public int getItemCount() {
            if (mCards != null) {
                return mCards.length;
            } else {
                return 0;
            }
        }

        public void setList(LoanApply.Card[] cards) {
            mCards = cards;
            notifyDataSetChanged();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private interface OnClickViewHolderListener {

            void onClickProgress(ViewHolder viewHolder);

            void onClickPlan(ViewHolder viewHolder);
        }

        private OnClickViewHolderListener mListener;

        private TextView mTextTitle;

        private TextView mTextDate;

        private TextView mTextName;

        private TextView mTextApprove;

        private TextView mTextTotal;

        private TextView mTextLoan;

        private TextView mTextOther;

        private TextView mTextDeadline;

        private TextView mTextStatus;

        private Button mBtnPlan;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextTitle = itemView.findViewById(R.id.text_title);
            mTextDate = itemView.findViewById(R.id.text_date);
            mTextName = itemView.findViewById(R.id.text_name);
            mTextApprove = itemView.findViewById(R.id.text_approve);
            mTextTotal = itemView.findViewById(R.id.text_total);
            mTextLoan = itemView.findViewById(R.id.text_loan);
            mTextOther = itemView.findViewById(R.id.text_other);
            mTextDeadline = itemView.findViewById(R.id.text_deadline);
            mTextStatus = itemView.findViewById(R.id.text_status);
            mBtnPlan = itemView.findViewById(R.id.btn_plan);
            itemView.findViewById(R.id.btn_progress).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onClickProgress(ViewHolder.this);
                    }
                }
            });
            mBtnPlan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onClickPlan(ViewHolder.this);
                    }
                }
            });
        }

        public void setListener(OnClickViewHolderListener listener) {
            mListener = listener;
        }

        public void setTitle(String text) {
            mTextTitle.setText(text);
        }

        public void setDate(String text) {
            mTextDate.setText(text);
        }

        public void setName(String text) {
            mTextName.setText(text);
        }

        public void setApprove(String text) {
            mTextApprove.setText(text);
        }

        public void setTotal(String text) {
            mTextTotal.setText(text);
        }

        public void setLoan(String text) {
            mTextLoan.setText(text);
        }

        public void setOther(String text) {
            mTextOther.setText(text);
        }

        public void setDeadline(String text) {
            mTextDeadline.setText(text);
        }

        public void setStatus(String text) {
            mTextStatus.setText(text);
        }

        public void setPlanEnabled(boolean enabled) {
            if (enabled) {
                mBtnPlan.setEnabled(true);
            } else {
                mBtnPlan.setEnabled(false);
            }
        }
    }
}
