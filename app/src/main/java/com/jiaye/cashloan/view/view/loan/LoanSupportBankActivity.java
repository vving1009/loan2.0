package com.jiaye.cashloan.view.view.loan;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.SupportBankList;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.data.loan.source.LoanSupportBankRepository;

import java.util.List;

/**
 * LoanSupportBankActivity
 *
 * @author 贾博瑄
 */

public class LoanSupportBankActivity extends BaseActivity implements LoanSupportBankContract.View {

    private LoanSupportBankContract.Presenter mPresenter;

    private Adapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_support_bank_activity);
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
        mPresenter = new LoanSupportBankPresenter(this, new LoanSupportBankRepository());
        mPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void setList(List<SupportBankList.Data> list) {
        mAdapter.setData(list);
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private List<SupportBankList.Data> mList;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(LoanSupportBankActivity.this).inflate(R.layout.loan_support_bank_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setBackground(position);
            holder.setId(mList.get(position).getId());
            holder.setBank(mList.get(position).getName());
            holder.setOnce(mList.get(position).getOnceLimit());
            holder.setDay(mList.get(position).getDayLimit());
            holder.setMonth(mList.get(position).getMonthLimit());
        }

        @Override
        public int getItemCount() {
            if (mList == null || mList.size() == 0) {
                return 0;
            } else {
                return mList.size();
            }
        }

        public void setData(List<SupportBankList.Data> list) {
            mList = list;
            notifyDataSetChanged();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mLayout;

        private TextView mTextId;

        private TextView mTextBank;

        private TextView mTextOnce;

        private TextView mTextDay;

        private TextView mTextMonth;

        public ViewHolder(View itemView) {
            super(itemView);
            mLayout = itemView.findViewById(R.id.layout);
            mTextId = itemView.findViewById(R.id.text_id);
            mTextBank = itemView.findViewById(R.id.text_bank);
            mTextOnce = itemView.findViewById(R.id.text_once);
            mTextDay = itemView.findViewById(R.id.text_day);
            mTextMonth = itemView.findViewById(R.id.text_month);
        }

        public void setBackground(int position) {
            if (position % 2 == 0) {
                mLayout.setBackgroundColor(Color.WHITE);
            } else {
                mLayout.setBackgroundColor(Color.parseColor("#E6E6E6"));
            }
        }

        public void setId(String id) {
            mTextId.setText(id);
        }

        public void setBank(String bank) {
            mTextBank.setText(bank);
        }

        public void setOnce(String once) {
            mTextOnce.setText(once);
        }

        public void setDay(String day) {
            mTextDay.setText(day);
        }

        public void setMonth(String month) {
            mTextMonth.setText(month);
        }
    }
}
