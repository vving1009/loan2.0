package com.jiaye.cashloan.view.view.home;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/**
 * HomeFragment
 *
 * @author 贾博瑄
 */
public class HomeFragment extends BaseFragment implements HomeContract.View, EasyPermissions.PermissionCallbacks {

    private final int CONTACTS_READ_STORAGE_REQUEST_CODE = 101;

    @SuppressLint("InlinedApi")
    private String[] permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE};

    private HomePresenter mPresenter;

    private LinearLayout mLayoutProduct;

    private Banner mBanner;

    private String loanId;

    private PermissionRequest mPermissionRequest;

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
        mLayoutProduct = root.findViewById(R.id.layout_product);
        mBanner = root.findViewById(R.id.banner);
        mBanner.setDelayTime(4000);
        mBanner.setImageLoader(new GlideImageLoader());
        mPresenter = new HomePresenter(this, new HomeRepository());
        mPresenter.subscribe();
        mPermissionRequest = new PermissionRequest.Builder(HomeFragment.this, CONTACTS_READ_STORAGE_REQUEST_CODE, permissions)
                .setRationale("如需使用该功能，请打开权限")
                .setPositiveButtonText(R.string.yes)
                .setNegativeButtonText(R.string.no)
                .build();
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
    public void setProduct(final ProductList.Product[] products) {
        for (ProductList.Product product : products) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.include_home_product, null, false);
            mLayoutProduct.addView(view);
            ImageView icon = view.findViewById(R.id.img_icon);
            ImageView image = view.findViewById(R.id.img_product);
            TextView textTitle = view.findViewById(R.id.text_title);
            Glide.with(this).load(product.getIcon()).into(icon);
            Glide.with(this).load(product.getUrl()).into(image);
            textTitle.setText(product.getName());
            loanId = product.getId();
            final boolean isOpen = product.isOpen();
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isOpen) {
                        EasyPermissions.requestPermissions(mPermissionRequest);
                    }
                }
            });
        }
    }

    @Override
    public void showLoanAuthView() {
        Intent intent = new Intent(getActivity(), LoanAuthActivity.class);
        startActivity(intent);
    }

    @Override
    public void startAuthView() {
        Intent intent = new Intent(getActivity(), AuthActivity.class);
        startActivity(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (perms.contains("android.permission.READ_EXTERNAL_STORAGE") && perms.contains("android.permission.READ_CONTACTS")) {
            mPresenter.loan(loanId);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog
                    .Builder(this)
                    .setTitle("权限申请")
                    .setRationale("此功能需要" + "" + "权限，否则无法正常使用，是否打开设置")
                    .setPositiveButton("好")
                    .setNegativeButton("不行")
                    .build()
                    .show();
        }
    }
}
