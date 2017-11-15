package com.jiaye.cashloan.view.view.home;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.home.Product;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.view.main.MainFragment;

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
        RecyclerView recyclerView = root.findViewById(R.id.recycler);
        mAdapter = new Adapter(getActivity(), new OnClickCardListener() {
            @Override
            public void onClickCard(Product product) {
                mPresenter.selectProduct(product);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
    public void setList(List<Product> list) {
        mAdapter.setList(list);
    }

    @Override
    public void startLoanView() {
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.layout_content);
        if (fragment != null && fragment instanceof MainFragment) {
            ((MainFragment) fragment).setLoanView();
        }
    }

    private static class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private Context mContext;

        private OnClickCardListener mListener;

        private List<Product> mList;

        private String formatAmount;

        private String formatDeadline;

        public Adapter(Context context, OnClickCardListener listener) {
            mContext = context;
            mListener = listener;
            formatAmount = mContext.getString(R.string.home_card_amount);
            formatDeadline = mContext.getString(R.string.home_card_deadline);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.home_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.setListener(new ViewHolder.OnClickViewHolderListener() {
                @Override
                public void onClickViewHolder(ViewHolder viewHolder) {
                    mListener.onClickCard(mList.get(viewHolder.getLayoutPosition()));
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setDrawable(mList.get(position).getLabelResId());
            holder.setColor(mContext.getResources().getColor(mList.get(position).getColor()));
            String amount = String.format(formatAmount, mList.get(position).getAmount());
            SpannableString sp = new SpannableString(amount);
            sp.setSpan(new AbsoluteSizeSpan(12, true), 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            sp.setSpan(new AbsoluteSizeSpan(30, true), 5, amount.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            holder.setAmount(sp);
            holder.setDeadline(String.format(formatDeadline, mList.get(position).getDeadline()));
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

        public void setList(List<Product> list) {
            mList = list;
            notifyDataSetChanged();
        }
    }

    private interface OnClickCardListener {

        void onClickCard(Product product);
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private interface OnClickViewHolderListener {

            void onClickViewHolder(ViewHolder viewHolder);
        }

        private OnClickViewHolderListener mListener;

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
                        mListener.onClickViewHolder(ViewHolder.this);
                    }
                }
            });
            mImgLabel = itemView.findViewById(R.id.img_label);
            mTextAmount = itemView.findViewById(R.id.text_amount);
            mTextDeadline = itemView.findViewById(R.id.text_deadline);
            mTextPaymentMethod = itemView.findViewById(R.id.text_payment_method);
        }

        public void setListener(OnClickViewHolderListener listener) {
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
