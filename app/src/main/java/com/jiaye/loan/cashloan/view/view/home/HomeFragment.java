package com.jiaye.loan.cashloan.view.view.home;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiaye.loan.cashloan.R;
import com.jiaye.loan.cashloan.view.BaseFragment;
import com.jiaye.loan.cashloan.view.data.home.Card;
import com.jiaye.loan.cashloan.view.view.main.MainFragment;

import java.util.List;

/**
 * HomeFragment
 *
 * @author 贾博瑄
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {

    private HomeContract.Presenter mPresenter;

    private Adapter mAdapter;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler);
        mAdapter = new Adapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.subscribe();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setList(List<Card> list) {
        mAdapter.setList(list);
    }

    private static class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private Context mContext;

        private List<Card> mList;

        public Adapter(Context context) {
            mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.home_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.setListener(new ViewHolder.OnClickCardListener() {
                @Override
                public void onClickCard(ViewHolder viewHolder) {
                    int position = viewHolder.getLayoutPosition();
                    if (mList.get(position).isOpen()) {
                        Fragment fragment = ((AppCompatActivity) mContext).getSupportFragmentManager().findFragmentById(R.id.layout_content);
                        if (fragment != null && fragment instanceof MainFragment) {
                            ((MainFragment) fragment).setLoanView(mList.get(position).getType());
                        }
                    }
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setDrawable(mList.get(position).getLabelResId());
            holder.setColor(mContext.getResources().getColor(mList.get(position).getColor()));
            holder.setAmount(mList.get(position).getAmount());
            holder.setDeadline(mList.get(position).getDeadline());
            holder.setPaymentMethod(mList.get(position).getPaymentMethod());
        }

        @Override
        public int getItemCount() {
            if (mList != null && mList.size() > 0) {
                return mList.size();
            } else {
                return 0;
            }
        }

        public void setList(List<Card> list) {
            mList = list;
            notifyDataSetChanged();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private interface OnClickCardListener {

            void onClickCard(ViewHolder viewHolder);
        }

        private OnClickCardListener mListener;

        private ImageView mImgLabel;

        private TextView mTextAmount;

        private TextView mTextDeadline;

        private TextView mTextPaymentMethod;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.findViewById(R.id.layout_card).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onClickCard(ViewHolder.this);
                    }
                }
            });
            mImgLabel = (ImageView) itemView.findViewById(R.id.img_label);
            mTextAmount = (TextView) itemView.findViewById(R.id.text_amount);
            mTextDeadline = (TextView) itemView.findViewById(R.id.text_deadline);
            mTextPaymentMethod = (TextView) itemView.findViewById(R.id.text_payment_method);
        }

        public void setListener(OnClickCardListener listener) {
            mListener = listener;
        }

        public void setDrawable(int resId) {
            mImgLabel.setImageResource(resId);
        }

        public void setColor(int color) {
            mTextAmount.setTextColor(color);
            mTextDeadline.setTextColor(color);
            mTextPaymentMethod.setTextColor(color);
        }

        public void setAmount(SpannableString amount) {
            mTextAmount.setText(amount);
        }

        public void setDeadline(String deadline) {
            mTextDeadline.setText(deadline);
        }

        public void setPaymentMethod(String paymentMethod) {
            mTextPaymentMethod.setText(paymentMethod);
        }
    }
}
