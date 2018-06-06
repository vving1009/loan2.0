package com.jiaye.cashloan.view.home;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.service.LocationService;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.home.source.HomeRepository;
import com.jiaye.cashloan.view.search.SearchActivity;
import com.jiaye.cashloan.view.login.LoginActivity;
import com.jiaye.cashloan.view.view.loan.LoanAuthActivity;

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

    private final int LOCATION_PERMS_REQUEST_CODE = 101;

    private String city = "北京";

    @SuppressLint("InlinedApi")
    private final String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private HomePresenter mPresenter;

    private LinearLayout mLayoutProduct;

    private String loanId;

    private TextView mTextLocation;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationService locationService = ((LocationService.LocationBinder) service).getService();
            locationService.setOnLocationChangeListener(location -> {
                mTextLocation.setText(location);
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

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
        root.findViewById(R.id.btn_apply).setOnClickListener(v -> {
            SearchActivity.startActivity(getContext(), city);
        });
        mTextLocation = root.findViewById(R.id.location_text);
        mPresenter = new HomePresenter(this, new HomeRepository());
        mPresenter.subscribe();
        EasyPermissions.requestPermissions(HomeFragment.this, LOCATION_PERMS_REQUEST_CODE, permissions);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void showLoanAuthView() {
        Intent intent = new Intent(getActivity(), LoanAuthActivity.class);
        startActivity(intent);
    }

    @Override
    public void startAuthView() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.hasPermissions(getContext(), permissions)) {
            Intent intent = new Intent(getContext(), LocationService.class);
            getContext().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        StringBuilder sb = new StringBuilder();
        String s;
        for (String perm : perms) {
            switch (perm) {
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
