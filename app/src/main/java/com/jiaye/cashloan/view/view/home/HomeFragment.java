package com.jiaye.cashloan.view.view.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.home.BannerList;
import com.jiaye.cashloan.http.data.home.ProductList;
import com.jiaye.cashloan.utils.GlideImageLoader;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.home.HomeRepository;
import com.jiaye.cashloan.view.view.auth.AuthActivity;
import com.jiaye.cashloan.view.view.loan.LoanAuthActivity;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * HomeFragment
 *
 * @author 贾博瑄
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {

    private static final int[] TAB_IMAGES = {R.drawable.home_tab_price, R.drawable.home_tab_credit, R.drawable.home_tab_loan};

    private static final int[] TAB_TITLE = {R.string.home_price, R.string.home_credit, R.string.home_loan};

    private HomePresenter mPresenter;

    private Banner mBanner;

    private ImageView mImage1;

    private ImageView mImage2;

    private ImageView mImage3;

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
        /*BannerList*/
        mBanner = root.findViewById(R.id.banner);
        mBanner.setDelayTime(4000);
        mBanner.setImageLoader(new GlideImageLoader());
        /*ProductList*/
        View view1 = root.findViewById(R.id.include_1);
        TextView textTitle1 = view1.findViewById(R.id.text_title);
        mImage1 = view1.findViewById(R.id.img_product);
        textTitle1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(TAB_IMAGES[0]), null, null, null);
        textTitle1.setText(getString(TAB_TITLE[0]));
        mImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loan();
            }
        });
        View view2 = root.findViewById(R.id.include_2);
        TextView textTitle2 = view2.findViewById(R.id.text_title);
        mImage2 = view2.findViewById(R.id.img_product);
        textTitle2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(TAB_IMAGES[1]), null, null, null);
        textTitle2.setText(getString(TAB_TITLE[1]));
        View view3 = root.findViewById(R.id.include_3);
        TextView textTitle3 = view3.findViewById(R.id.text_title);
        mImage3 = view3.findViewById(R.id.img_product);
        textTitle3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(TAB_IMAGES[2]), null, null, null);
        textTitle3.setText(getString(TAB_TITLE[2]));
        mPresenter = new HomePresenter(this, new HomeRepository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
    }

    @Override
    public void setBanners(BannerList.Banner[] banners) {
        List<String> list = new ArrayList<>();
        for (BannerList.Banner banner : banners) {
            list.add(banner.getImgUrl());
        }
        mBanner.setImages(list);
        mBanner.start();
    }

    @Override
    public void setProduct(ProductList.Product[] products) {
        for (int i = 0; i < products.length; i++) {
            switch (i) {
                case 0:
                    Glide.with(this).load(products[i].getUrl()).into(mImage1);
                    break;
                case 1:
                    Glide.with(this).load(products[i].getUrl()).into(mImage2);
                    break;
                case 2:
                    Glide.with(this).load(products[i].getUrl()).into(mImage3);
                    break;
            }
        }
    }

    @Override
    public void showLoanAuthView(String productId) {
        Intent intent = new Intent(getActivity(), LoanAuthActivity.class);
        intent.putExtra("productId", productId);
        startActivity(intent);
    }

    @Override
    public void startAuthView() {
        Intent intent = new Intent(getActivity(), AuthActivity.class);
        startActivity(intent);
    }
}
