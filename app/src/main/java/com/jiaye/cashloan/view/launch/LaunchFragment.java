package com.jiaye.cashloan.view.launch;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.launch.source.LaunchRepository;
import com.jiaye.cashloan.view.main.MainActivity;
import com.satcatche.library.network.checkupgrade.bean.UpgradeResponse;
import com.satcatche.library.utils.FileUtils;
import com.satcatche.library.widget.SatcatcheDialog;

import java.io.File;

/**
 * LaunchFragment
 *
 * @author 贾博瑄
 */

public class LaunchFragment extends BaseFragment implements LaunchContract.View {

    private static final int REQUEST_ALLOW_INSTALL_PERMISSION = 101;

    private LaunchContract.Presenter mPresenter;

    private SatcatcheDialog mCheckUpdateDialog;

    public static LaunchFragment newInstance() {
        Bundle args = new Bundle();
        LaunchFragment fragment = new LaunchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ALLOW_INSTALL_PERMISSION:
                if (hasInstallPermission()) {
                    startDownload();
                } else {
                    showToastById(R.string.error_install_permission);
                }
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.launch_fragment, container, false);
        mCheckUpdateDialog = new SatcatcheDialog.Builder(getContext())
                .setTitle("发现新的版本")
                .setPositiveButton("立即下载", ((dialog, which) -> {
                    if (hasInstallPermission()) {
                        startDownload();
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            new SatcatcheDialog.Builder(getContext())
                                    .setTitle("提示")
                                    .setMessage("安装应用需要打开未知来源权限，请去设置中开启权限")
                                    .setPositiveButton("确定", ((dialog1, which1) -> {
                                        Uri packageURI = Uri.parse("package:" + getContext().getPackageName());
                                        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                                        startActivityForResult(intent, REQUEST_ALLOW_INSTALL_PERMISSION);
                                    }))
                                    .build().show();
                        }
                    }
                }))
                .setNegativeButton("暂不下载", ((dialog, which) -> {
                    mCheckUpdateDialog.dismiss();
                    mPresenter.auto();
                }))
                .build();
        mPresenter = new LaunchPresenter(this, new LaunchRepository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void showMainView() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void showUpdateView(UpgradeResponse.Body data) {
        mCheckUpdateDialog.setMessage(data.getVersionName() + "\n" + data.getNotes());
        if (data.getIsForce() == 1) {
            mCheckUpdateDialog.setNegativeBtnVisibility(View.GONE);
        }
        mCheckUpdateDialog.show();
    }

    @Override
    public void setProgress(int progress) {
        mCheckUpdateDialog.setProgress(progress);
    }

    @Override
    public void install(File file) {
        mCheckUpdateDialog.dismiss();
        FileUtils.install(getActivity(), file, BuildConfig.APPLICATION_ID);
    }

    private void startDownload() {
        mCheckUpdateDialog.setProgressVisibility(View.VISIBLE);
        mCheckUpdateDialog.setAllBtnVisibility(View.GONE);
        mCheckUpdateDialog.show();
        mPresenter.download();
    }

    private boolean hasInstallPermission() {
        boolean has = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            has = getActivity().getPackageManager().canRequestPackageInstalls();
        }
        return has;
    }
}
