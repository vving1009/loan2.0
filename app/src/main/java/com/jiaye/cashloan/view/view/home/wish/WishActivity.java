package com.jiaye.cashloan.view.view.home.wish;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.DefaultProduct;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.data.loan.source.LoanRepository;
import com.jiaye.cashloan.view.view.auth.AuthActivity;
import com.jiaye.cashloan.view.view.loan.LoanAuthActivity;
import com.jiaye.cashloan.view.view.loan.LoanContract;
import com.jiaye.cashloan.view.view.loan.LoanPresenter;

/**
 * Created by guozihua on 2018/1/2.
 */

public class WishActivity extends BaseActivity implements LoanContract.View {

    private LoanPresenter mPresenter;

    private int[] itemName = {R.string.wish_item1, R.string.wish_item2, R.string.wish_item3, R.string.wish_item4, R.string.wish_item5, R.string.wish_item6,
            R.string.wish_item7, R.string.wish_item8, R.string.wish_item9, R.string.wish_item10};
    private int[] itemIcon = {R.drawable.market_ic_household, R.drawable.market_ic_phone, R.drawable.market_ic_computer, R.drawable.market_ic_watch, R.drawable.market_ic_clothes,
            R.drawable.market_ic_beauty, R.drawable.market_ic_gem, R.drawable.market_ic_digit, R.drawable.market_ic_other, R.drawable.market_ic_old};

    private EditText mEdit1, mEdit2, mEdit3, mEdit4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist_activity);

        mEdit1 = findViewById(R.id.edit1);
        mEdit2 = findViewById(R.id.edit2);
        mEdit3 = findViewById(R.id.edit3);
        mEdit4 = findViewById(R.id.edit4);
        findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.loan();
            }
        });

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    onBackPressed();
            }
        });

        RecyclerView mRecyclerGrid = findViewById(R.id.recycler_product_grid);
        mRecyclerGrid.setLayoutManager(new GridLayoutManager(this, 5));
        mRecyclerGrid.setAdapter(new GridAdapter());

        mPresenter = new LoanPresenter(this, new LoanRepository());
        mPresenter.subscribe();
        mPresenter.requestProduct();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void startAuthView() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }

    @Override
    public void queryProduct() {
        mPresenter.queryProduct();
    }

    @Override
    public void requestProduct() {
        mPresenter.requestProduct();
    }

    @Override
    public void cleanProduct() {
    }

    @Override
    public void setDefaultProduct(DefaultProduct defaultProduct) {
    }

    @Override
    public void showLoanAuthView() {
        Intent intent = new Intent(this, LoanAuthActivity.class);
        startActivity(intent);
    }

    private class GridAdapter extends RecyclerView.Adapter<GridViewHolder> {
        int selectPosition = -1;

        @Override
        public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.wish_product_grid_item, parent, false);
            final GridViewHolder holder = new GridViewHolder(view);
            holder.setListener(new GridViewHolder.OnClickViewHolderListener() {
                @Override
                public void onClickViewHolder(GridViewHolder viewHolder) {
                    selectPosition = viewHolder.getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(GridViewHolder holder, int position) {
            holder.mTextName.setText(itemName[position]);
            holder.mImgIcon.setBackgroundResource(itemIcon[position]);
            if (selectPosition != -1) {
                if (selectPosition == position) {
                    holder.mTextName.setTextColor(getResources().getColor(R.color.color_red));
                } else {
                    holder.mTextName.setTextColor(getResources().getColor(R.color.color_525252));
                }
            }

        }

        @Override
        public int getItemCount() {
            return itemName.length;
        }
    }

    private static class GridViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextName;
        private ImageView mImgIcon;

        private interface OnClickViewHolderListener {

            void onClickViewHolder(GridViewHolder viewHolder);
        }

        private OnClickViewHolderListener mListener;


        public GridViewHolder(View itemView) {
            super(itemView);
            mTextName = itemView.findViewById(R.id.tv_name);
            mImgIcon = itemView.findViewById(R.id.img_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onClickViewHolder(GridViewHolder.this);
                    }
                }
            });

        }

        public void setListener(GridViewHolder.OnClickViewHolderListener listener) {
            mListener = listener;
        }
    }

}
