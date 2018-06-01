package com.jiaye.cashloan.view.home;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
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
import com.jiaye.cashloan.service.LocationService;
import com.jiaye.cashloan.utils.GlideImageLoader;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.home.source.HomeRepository;
import com.jiaye.cashloan.view.view.auth.AuthActivity;
import com.jiaye.cashloan.view.view.loan.LoanAuthActivity;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * HomeFragment
 *
 * @author 贾博瑄
 */
public class HomeFragment extends BaseFragment implements HomeContract.View, EasyPermissions.PermissionCallbacks {

    // 申请地理位置权限
        // 给
            // 1.根据经纬度定位城市
            // 2.用户可以自己更改城市
        // 不给 退出应用
    //

    private final int CONTACTS_STORAGE_LOCATION_REQUEST_CODE = 101;

    private static final int OPEN_GPS_REQUEST_CODE = 201;

    @SuppressLint("InlinedApi")
    private final String[] permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private HomePresenter mPresenter;

    private LinearLayout mLayoutProduct;

    private Banner mBanner;

    private String loanId;

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
                        EasyPermissions.requestPermissions(HomeFragment.this, CONTACTS_STORAGE_LOCATION_REQUEST_CODE, permissions);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_GPS_REQUEST_CODE) {
            startLoanVerification();
        }
    }

    private boolean startLocationService() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Intent intent = new Intent(getContext(), LocationService.class);
            getContext().startService(intent);
            return true;
        }
        return false;
    }

    private void startLoanVerification() {
        if (startLocationService()) {
            mPresenter.loan(loanId);
        } else {
            new AlertDialog.Builder(getContext())
                    .setMessage("此功能需要打开定位开关，否则无法正常使用，是否打开？")
                    .setPositiveButton("打开", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, OPEN_GPS_REQUEST_CODE);
                        }
                    })
                    .setCancelable(true)
                    .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.hasPermissions(getContext(), permissions)) {
            startLoanVerification();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        StringBuilder sb = new StringBuilder();
        String s;
        for (String perm : perms) {
            switch (perm) {
                case Manifest.permission.READ_CONTACTS:
                    s = "读取联系人，";
                    break;
                case Manifest.permission.READ_EXTERNAL_STORAGE:
                    s = "读存储设备，";
                    break;
                case Manifest.permission.ACCESS_COARSE_LOCATION:
                    s = "定位，";
                    break;
                default:
                    s = "";
                    break;
            }
            sb.append(s);
        }
        sb.deleteCharAt(sb.lastIndexOf("，"));
        new AppSettingsDialog
                .Builder(this)
                .setTitle("权限申请")
                .setRationale("此功能需要" + sb.toString() + "权限，否则无法正常使用，是否打开设置？")
                .setPositiveButton("好")
                .setNegativeButton("不行")
                .build()
                .show();
    }
}
