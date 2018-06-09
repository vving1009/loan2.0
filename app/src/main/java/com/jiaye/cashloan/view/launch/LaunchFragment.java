package com.jiaye.cashloan.view.launch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.launch.CheckUpdate;
import com.jiaye.cashloan.utils.FileUtils;
import com.jiaye.cashloan.widget.BaseDialog;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.launch.source.LaunchRepository;
import com.jiaye.cashloan.view.view.guide.GuideActivity;
import com.jiaye.cashloan.view.main.MainActivity;

import java.io.File;

/**
 * LaunchFragment
 *
 * @author 贾博瑄
 */

public class LaunchFragment extends BaseFragment implements LaunchContract.View {

    private static final int REQUEST_INSTALL_PERMISSION = 101;

    private static final int REQUEST_ALLOW_INSTALL_PERMISSION = 102;

    private LaunchContract.Presenter mPresenter;

    private BaseDialog mCheckUpdateDialog;

    private TextView mTextName;

    private TextView mTextNotes;

    private ProgressBar mProgressBar;

    private LinearLayout mLayoutBottom;

    private TextView mTextCancel;

    public static LaunchFragment newInstance() {
        Bundle args = new Bundle();
        LaunchFragment fragment = new LaunchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_INSTALL_PERMISSION:
                if (isGrant(grantResults)) {
                    startDownload();
                } else {
                    @SuppressLint("InlinedApi")
                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                    startActivityForResult(intent, REQUEST_ALLOW_INSTALL_PERMISSION);
                }
                break;
        }
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
        mCheckUpdateDialog = new BaseDialog(getActivity());
        View updateView = LayoutInflater.from(getActivity()).inflate(R.layout.check_update_dialog_layout, null);
        mTextName = updateView.findViewById(R.id.text_name);
        mTextNotes = updateView.findViewById(R.id.text_note);
        mProgressBar = updateView.findViewById(R.id.progress);
        mLayoutBottom = updateView.findViewById(R.id.layout_bottom);
        mTextCancel = updateView.findViewById(R.id.text_cancel);
        updateView.findViewById(R.id.text_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasInstallPermission()) {
                    startDownload();
                } else {
                    requestPermissions(new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, REQUEST_INSTALL_PERMISSION);
                }
            }
        });
        mTextCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckUpdateDialog.dismiss();
                mPresenter.auto();
            }
        });
        mCheckUpdateDialog.setContentView(updateView);
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
    public void showGuideView() {
        Intent intent = new Intent(getActivity(), GuideActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void showMainView() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void showGestureView() {
        Intent intent = new Intent(getActivity(), LaunchGestureActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void showUpdateView(CheckUpdate data) {
        mTextName.setText(data.getVersionName());
        mTextNotes.setText(data.getNotes());
        if (data.getIsForce() == 1) {
            mTextCancel.setVisibility(View.GONE);
        }
        mCheckUpdateDialog.show();
    }

    @Override
    public void setProgress(int progress) {
        mProgressBar.setProgress(progress);
    }

    @Override
    public void install(File file) {
        FileUtils.install(getActivity(), file);
    }

    private void startDownload() {
        mProgressBar.setVisibility(View.VISIBLE);
        mLayoutBottom.setVisibility(View.GONE);
        mPresenter.download();
    }

    private boolean hasInstallPermission() {
        boolean has = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            has = getActivity().getPackageManager().canRequestPackageInstalls();
        }
        return has;
    }

    private boolean isGrant(@NonNull int[] grantResults) {
        boolean grant = true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                grant = false;
            }
        }
        return grant;
    }
}
