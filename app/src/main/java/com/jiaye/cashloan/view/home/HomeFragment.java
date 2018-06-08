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
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.service.LocationService;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.FunctionActivity;
import com.jiaye.cashloan.view.home.source.HomeRepository;
import com.jiaye.cashloan.view.login.LoginActivity;
import com.jiaye.cashloan.view.search.SearchActivity;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * HomeFragment
 *
 * @author 贾博瑄
 */
public class HomeFragment extends BaseFragment implements HomeContract.View, EasyPermissions.PermissionCallbacks {

    private final int LOCATION_PERMS_REQUEST_CODE = 101;

    @SuppressLint("InlinedApi")
    private final String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private HomePresenter mPresenter;

    private TextView mTextLocation;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationService locationService = ((LocationService.LocationBinder) service).getService();
            locationService.setOnLocationChangeListener(location -> {
                if (location.contains("市")) {
                    location = location.substring(0, location.lastIndexOf("市"));
                }
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
            //mPresenter.loan();
            FunctionActivity.function(getActivity(), "Taobao");
        });
        mTextLocation = root.findViewById(R.id.location_text);
        mPresenter = new HomePresenter(this, new HomeRepository());
        mPresenter.subscribe();
        EasyPermissions.requestPermissions(HomeFragment.this, LOCATION_PERMS_REQUEST_CODE, permissions);
        return root;
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
        new AlertDialog.Builder(getContext())
                .setTitle("权限申请")
                .setMessage("此应用需要" + sb.toString() + "权限，否则应用会关闭，是否打开设置？")
                .setPositiveButton("好", (dialog, which) ->
                        EasyPermissions.requestPermissions(HomeFragment.this, LOCATION_PERMS_REQUEST_CODE, permissions))
                .setNegativeButton("不行", (dialog, which) -> getActivity().finish())
                .show();
    }

    @Override
    public void showSearchView() {
        SearchActivity.startActivity(getContext(), mTextLocation.getText().toString());
    }

    @Override
    public void showCertificationView() {
        FunctionActivity.function(getActivity(), "Certification");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unbindService(mServiceConnection);
    }
}
