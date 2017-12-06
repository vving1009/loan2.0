package com.jiaye.cashloan.view.view.loan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.LoanProgress;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.data.loan.source.LoanProgressRepository;

import java.util.List;

/**
 * LoanProgressActivity
 *
 * @author 贾博瑄
 */

public class LoanProgressActivity extends BaseActivity implements LoanProgressContract.View {

    private LoanProgressContract.Presenter mPresenter;

    private Adapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_progress_activity);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new Adapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TextView textPhone = findViewById(R.id.text_phone);
        SpannableString string = new SpannableString(textPhone.getText());
        string.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_blue)), 9, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textPhone.setText(string);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mPresenter = new LoanProgressPresenter(this, new LoanProgressRepository());
        mPresenter.subscribe();
        mPresenter.requestLoanProgress(getIntent().getExtras().getString("loanId"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void setList(List<LoanProgress.Data> list) {
        mAdapter.setList(list);
    }


    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private List<LoanProgress.Data> mList;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(LoanProgressActivity.this).inflate(R.layout.loan_progress_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.setListener(new ViewHolder.OnClickViewHolderListener() {
                @Override
                public void onClickViewHolder(ViewHolder viewHolder) {
                    switch (mList.get(viewHolder.getLayoutPosition()).getStatus()) {
                        case "01":
                            break;
                        case "02":
                            break;
                        case "03":
                            break;
                        case "04":
                            break;
                        case "05":
                            break;
                        case "06":
                            break;
                        case "07":
                            break;
                        case "08":
                            break;
                        case "09":
                            break;
                        case "10":
                            break;
                        case "11":
                            break;
                        case "12":
                            break;
                        case "13":
                            break;
                        case "14":
                            break;
                        case "15":
                            break;
                        case "16":
                            break;
                    }
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (position == 0) {
                holder.mLineTop.setVisibility(View.INVISIBLE);
            } else if (position == mList.size() - 1) {
                holder.mTextDate.setTextColor(getResources().getColor(R.color.color_orange));
                holder.mImgPoint.setImageDrawable(getResources().getDrawable(R.drawable.loan_point_orange));
                holder.mLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.loan_progress_bg_orange));
                holder.mLineBottom.setVisibility(View.INVISIBLE);
                holder.mTextMsg.setTextColor(getResources().getColor(R.color.color_orange));
            }
            switch (mList.get(position).getStatus()) {
                case "01":
                    holder.mImgStatus.setVisibility(View.VISIBLE);
                    holder.mImgStatus.setImageDrawable(getResources().getDrawable(R.drawable.loan_ic_un_pass));
                    holder.mTextStatus.setVisibility(View.INVISIBLE);
                    break;
                case "02":
                    holder.mImgStatus.setVisibility(View.VISIBLE);
                    holder.mImgStatus.setImageDrawable(getResources().getDrawable(R.drawable.loan_ic_pass));
                    holder.mTextStatus.setVisibility(View.INVISIBLE);
                    break;
                case "03":
                    holder.mImgStatus.setVisibility(View.INVISIBLE);
                    holder.mTextStatus.setVisibility(View.INVISIBLE);
                    break;
                case "04":
                    holder.mImgStatus.setVisibility(View.VISIBLE);
                    holder.mImgStatus.setImageDrawable(getResources().getDrawable(R.drawable.loan_ic_pass));
                    holder.mTextStatus.setVisibility(View.INVISIBLE);
                    break;
                case "05":
                    holder.mImgStatus.setVisibility(View.VISIBLE);
                    holder.mImgStatus.setImageDrawable(getResources().getDrawable(R.drawable.loan_ic_un_pass));
                    holder.mTextStatus.setVisibility(View.INVISIBLE);
                    break;
                case "06":
                    holder.mImgStatus.setVisibility(View.VISIBLE);
                    holder.mImgStatus.setImageDrawable(getResources().getDrawable(R.drawable.loan_ic_un_pass));
                    holder.mTextStatus.setVisibility(View.INVISIBLE);
                    break;
                case "07":
                    holder.mImgStatus.setVisibility(View.INVISIBLE);
                    holder.mTextStatus.setVisibility(View.VISIBLE);
                    holder.mTextStatus.setText("绑定");
                    break;
                case "08":
                    holder.mImgStatus.setVisibility(View.INVISIBLE);
                    holder.mTextStatus.setVisibility(View.VISIBLE);
                    holder.mTextStatus.setText("绑定");
                    break;
                case "09":
                    holder.mImgStatus.setVisibility(View.VISIBLE);
                    holder.mImgStatus.setImageDrawable(getResources().getDrawable(R.drawable.loan_ic_pass));
                    holder.mTextStatus.setVisibility(View.INVISIBLE);
                    break;
                case "10":
                    holder.mImgStatus.setVisibility(View.INVISIBLE);
                    holder.mTextStatus.setVisibility(View.VISIBLE);
                    holder.mTextStatus.setText("签约");
                    break;
                case "11":
                    holder.mImgStatus.setVisibility(View.INVISIBLE);
                    holder.mTextStatus.setVisibility(View.VISIBLE);
                    holder.mTextStatus.setText("查看");
                    break;
                case "12":
                    holder.mImgStatus.setVisibility(View.INVISIBLE);
                    holder.mTextStatus.setVisibility(View.INVISIBLE);
                    break;
                case "13":
                    holder.mImgStatus.setVisibility(View.VISIBLE);
                    holder.mImgStatus.setImageDrawable(getResources().getDrawable(R.drawable.loan_ic_pass));
                    holder.mTextStatus.setVisibility(View.INVISIBLE);
                    break;
                case "14":
                    holder.mImgStatus.setVisibility(View.INVISIBLE);
                    holder.mTextStatus.setVisibility(View.VISIBLE);
                    holder.mTextStatus.setText("还款");
                    break;
                case "15":
                    holder.mImgStatus.setVisibility(View.INVISIBLE);
                    holder.mTextStatus.setVisibility(View.INVISIBLE);
                    break;
                case "16":
                    holder.mImgStatus.setVisibility(View.VISIBLE);
                    holder.mImgStatus.setImageDrawable(getResources().getDrawable(R.drawable.loan_ic_pass));
                    holder.mTextStatus.setVisibility(View.INVISIBLE);
                    break;
            }
            holder.mTextDate.setText(mList.get(position).getTime());
            holder.mTextMsg.setText(mList.get(position).getMsg());
        }

        @Override
        public int getItemCount() {
            if (mList != null) {
                return mList.size();
            } else {
                return 0;
            }
        }

        public void setList(List<LoanProgress.Data> list) {
            mList = list;
            notifyDataSetChanged();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private interface OnClickViewHolderListener {

            void onClickViewHolder(ViewHolder viewHolder);
        }

        private TextView mTextDate;

        private ImageView mImgPoint;

        private View mLineTop;

        private View mLineBottom;

        private RelativeLayout mLayout;

        private TextView mTextMsg;

        private ImageView mImgStatus;

        private TextView mTextStatus;

        private OnClickViewHolderListener mListener;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextDate = itemView.findViewById(R.id.text_date);
            mImgPoint = itemView.findViewById(R.id.img_point);
            mLineTop = itemView.findViewById(R.id.line_top);
            mLineBottom = itemView.findViewById(R.id.line_bottom);
            mLayout = itemView.findViewById(R.id.layout);
            mTextMsg = itemView.findViewById(R.id.text_msg);
            mImgStatus = itemView.findViewById(R.id.img_status);
            mTextStatus = itemView.findViewById(R.id.text_status);
            mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onClickViewHolder(ViewHolder.this);
                    }
                }
            });
        }

        public void setListener(OnClickViewHolderListener listener) {
            mListener = listener;
        }
    }
}
