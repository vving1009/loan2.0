package com.jiaye.cashloan.view.view.home;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.home.Product;
import com.jiaye.cashloan.utils.GlideImageLoader;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.home.source.HomeRepository;
import com.jiaye.cashloan.view.view.home.wish.WishActivity;
import com.jiaye.cashloan.view.view.main.MainFragment;
import com.jiaye.cashloan.view.view.market.MarketActivity;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * HomeFragment
 *
 * @author 贾博瑄
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {

    private HomeContract.Presenter mPresenter;

    private Adapter mAdapter;
    private Banner banner ;
    private int[] images = {R.drawable.home_ic_price,R.drawable.home_ic_credit,R.drawable.home_ic_loan};
    private int[] tab_images = {R.drawable.home_tab_price,R.drawable.home_tab_credit,R.drawable.home_tab_loan} ;

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
        mAdapter = new Adapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(mAdapter);

        banner = root.findViewById(R.id.banner);
        ArrayList<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.bg_title);
        imageList.add(R.drawable.bg_title);
        imageList.add(R.drawable.bg_title);
        imageList.add(R.drawable.bg_title);
        banner.setImages(imageList);
        banner.setDelayTime(4000);
        banner.setImageLoader(new GlideImageLoader());
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(getActivity(),position+"",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),MarketActivity.class);
                startActivity(intent);

            }
        });
        banner.start();

        mPresenter = new HomePresenter(this, new HomeRepository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void setList(List<Product> list) {
        /**
         * 假数据
         */
        Product p1 = new Product();
        p1.setName("消费分期");
        Product p2 = new Product();
        p2.setName("信贷产品");
        Product p3 = new Product();
        p3.setName("借款产品");

        ArrayList<Product> arrayList = new ArrayList<>();
        arrayList.add(p1);
        arrayList.add(p2);
        arrayList.add(p3);

        mAdapter.setList(arrayList);
    }
    @Override
    public void showLoanView() {
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.layout_content);
        if (fragment != null && fragment instanceof MainFragment) {
            ((MainFragment) fragment).showLoanView();
        }
    }

    @Override
    public void showWishView() {
        startActivity(new Intent(getActivity(),WishActivity.class));
    }

    @Override
    public void showCreditView() {//先判断是否登录 跳一个空白页 然后跳到六部认证

    }

    @Override
    public void showLoanProductView() {



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("");
        builder.setMessage("尽情期待");
        builder.setPositiveButton("确定" , new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("取消",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create();
        builder.show();

    }

    //    private class Adapter extends RecyclerView.Adapter<ViewHolder> {
//
//        private List<Product> mList;
//
//        private String formatAmount;
//
//        public Adapter() {
//            formatAmount = getString(R.string.home_card_amount);
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_item, parent, false);
//            ViewHolder viewHolder = new ViewHolder(view);
//            viewHolder.setListener(new ViewHolder.OnClickViewHolderListener() {
//                @Override
//                public void onClickViewHolder(ViewHolder viewHolder) {
//                    mPresenter.selectProduct(mList.get(viewHolder.getLayoutPosition()));
//                }
//            });
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(ViewHolder holder, int position) {
//            holder.setDrawable(mList.get(position).getLabelResId());
//            holder.setColor(getResources().getColor(mList.get(position).getColor()));
//            String amount = String.format(formatAmount, mList.get(position).getAmount());
//            SpannableString sp = new SpannableString(amount);
//            sp.setSpan(new AbsoluteSizeSpan(12, true), 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//            sp.setSpan(new AbsoluteSizeSpan(30, true), 5, amount.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//            holder.setAmount(sp);
//            holder.setDeadline(mList.get(position).getDeadline());
//            holder.setPaymentMethod(mList.get(position).getPaymentMethod());
//        }
//
//        @Override
//        public int getItemCount() {
//            if (mList != null && mList.size() > 0) {
//                return mList.size();
//            } else {
//                return 0;
//            }
//        }
//
//        public void setList(List<Product> list) {
//            mList = list;
//            notifyDataSetChanged();
//        }
//    }
    private class Adapter extends  RecyclerView.Adapter<ViewHolder>{

        private List<Product> mList;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_product_item,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.setListener(new ViewHolder.OnClickViewHolderListener(){
                @Override
                public void onClickViewHolder(ViewHolder viewHolder) {
                    mPresenter.selectProduct(mList.get(viewHolder.getLayoutPosition()));
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTextName.setText(mList.get(position).getName());
            holder.mImgProduct.setBackgroundResource(images[position]);
            holder.mImgIcon.setBackgroundResource(tab_images[position]);
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

//    private static class ViewHolder extends RecyclerView.ViewHolder {
//
//        private interface OnClickViewHolderListener {
//
//            void onClickViewHolder(ViewHolder viewHolder);
//        }
//
//        private OnClickViewHolderListener mListener;
//
//        private ImageView mImgLabel;
//
//        private TextView mTextAmount;
//
//        private TextView mTextDeadline;
//
//        private TextView mTextPaymentMethod;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            itemView.findViewById(R.id.layout_card).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mListener != null) {
//                        mListener.onClickViewHolder(ViewHolder.this);
//                    }
//                }
//            });
//            mImgLabel = itemView.findViewById(R.id.img_label);
//            mTextAmount = itemView.findViewById(R.id.text_amount);
//            mTextDeadline = itemView.findViewById(R.id.text_deadline);
//            mTextPaymentMethod = itemView.findViewById(R.id.text_payment_method);
//        }
//
//        public void setListener(OnClickViewHolderListener listener) {
//            mListener = listener;
//        }
//
//        public void setDrawable(int resId) {
//            mImgLabel.setImageResource(resId);
//        }
//
//        public void setColor(int color) {
//            mTextAmount.setTextColor(color);
//            mTextDeadline.setTextColor(color);
//            mTextPaymentMethod.setTextColor(color);
//        }
//
//        public void setAmount(SpannableString amount) {
//            mTextAmount.setText(amount);
//        }
//
//        public void setDeadline(String deadline) {
//            mTextDeadline.setText(deadline);
//        }
//
//        public void setPaymentMethod(String paymentMethod) {
//            mTextPaymentMethod.setText(paymentMethod);
//        }
//    }
    private static class ViewHolder extends RecyclerView.ViewHolder{


        private interface OnClickViewHolderListener {

            void onClickViewHolder(ViewHolder viewHolder);
        }

        private OnClickViewHolderListener mListener;


        private ImageView mImgIcon;
        private TextView mTextName;
        private ImageView mImgProduct ;

    public ViewHolder(View itemView) {
        super(itemView);
        mImgIcon = itemView.findViewById(R.id.img_icon);
        mImgProduct = itemView.findViewById(R.id.img_product);
        mTextName = itemView.findViewById(R.id.tv_name);
        mImgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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




    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }
}
