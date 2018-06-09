package com.jiaye.cashloan.view.vehicle;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.PhotoFunctionFragment;
import com.jiaye.cashloan.view.vehicle.source.VehicleRepository;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TResult;

import java.io.File;

/**
 * VehicleFragment
 *
 * @author 贾博瑄
 */

public class VehicleFragment extends PhotoFunctionFragment implements VehicleContract.View {

    private VehicleContract.Presenter mPresenter;

    private BottomSheetDialog mBottomDialog;

    public static VehicleFragment newInstance() {
        Bundle args = new Bundle();
        VehicleFragment fragment = new VehicleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    protected int getTitleId() {
        return R.string.Vehicle_title;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.vehicle_fragment, frameLayout, true);
        rootView.findViewById(R.id.drive_Licence).setOnClickListener(v -> {
            mPresenter.setFolder(VehicleContract.FOLDER_DRIVE_LICENCE);
            mBottomDialog.show();
        });
        rootView.findViewById(R.id.vehicle_ownership).setOnClickListener(v -> {
            mPresenter.setFolder(VehicleContract.FOLDER_VEHICLE_OWNERSHIP);
            mBottomDialog.show();
        });
        rootView.findViewById(R.id.btn_commit).setOnClickListener(v -> {
            mPresenter.submit();
        });
        initBottomDialog();
        mPresenter = new VehiclePresenter(this, new VehicleRepository());
        mPresenter.subscribe();
        return rootView;
    }

    private void initBottomDialog() {
        mBottomDialog = new BottomSheetDialog(getContext());
        View layout = LayoutInflater.from(getContext()).inflate(R.layout.photo_layout, null);
        layout.findViewById(R.id.text_camera).setOnClickListener(v -> {
            mBottomDialog.dismiss();
            camera("/camera/" + System.currentTimeMillis() + ".jpg");
        });
        layout.findViewById(R.id.text_photo).setOnClickListener(v -> {
            mBottomDialog.dismiss();
            photo();
        });
        layout.findViewById(R.id.text_cancel).setOnClickListener(v -> mBottomDialog.dismiss());
        mBottomDialog.setContentView(layout);
    }

    @Override
    public void takeSuccess(TResult result) {
        mPresenter.upload(result.getImages());
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    public void camera(String path) {
        File file = new File(Environment.getExternalStorageDirectory(), path);
        if (!file.getParentFile().exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);
        CompressConfig compressConfig = CompressConfig.ofDefaultConfig();
        compressConfig.enableReserveRaw(false);
        compressConfig.setMaxSize(500 * 1024);
        getTakePhoto().onEnableCompress(compressConfig, false);
        getTakePhoto().onPickFromCapture(imageUri);
    }

    public void photo() {
        CompressConfig compressConfig = CompressConfig.ofDefaultConfig();
        compressConfig.enableReserveRaw(false);
        compressConfig.setMaxSize(500 * 1024);
        getTakePhoto().onEnableCompress(compressConfig, false);
        getTakePhoto().onPickMultiple(3);
    }

    @Override
    public void finish() {
        getActivity().finish();
    }
}
