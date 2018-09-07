package com.jiaye.cashloan.view.loancar.carpaper;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.PhotoFunctionFragment;
import com.jiaye.cashloan.view.loancar.carpaper.source.VehicleRepository;
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

    private RelativeLayout mDriveLicence;

    private RelativeLayout mVehicleOwnership;

    private ImageView mDriveLicenceIcon;

    private ImageView mVehicleOwnershipIcon;

    private TextView mDriveLicenceText;

    private TextView mVehicleOwnershipText;

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
        return R.string.vehicle_title;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.vehicle_fragment, frameLayout, true);
        mDriveLicenceIcon = rootView.findViewById(R.id.drive_Licence_ic);
        mVehicleOwnershipIcon = rootView.findViewById(R.id.vehicle_ownership_ic);
        mDriveLicenceText = rootView.findViewById(R.id.drive_Licence_text);
        mVehicleOwnershipText = rootView.findViewById(R.id.vehicle_ownership_text);
        mDriveLicence = rootView.findViewById(R.id.drive_licence);
        mDriveLicence.setOnClickListener(v -> {
            mPresenter.setFolder(VehicleContract.FOLDER_DRIVE_LICENCE);
            mBottomDialog.show();
        });
        mVehicleOwnership = rootView.findViewById(R.id.vehicle_ownership);
        mVehicleOwnership.setOnClickListener(v -> {
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
    public void takeFail(TResult result, String msg) {}

    @Override
    public void takeCancel() {}

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
        compressConfig.setMaxSize(500 * 1024);
        getTakePhoto().onEnableCompress(compressConfig, false);
        getTakePhoto().onPickMultiple(9);
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void showLicenceCount(int count) {
        Resources resources = getContext().getResources();
        mDriveLicence.setBackgroundDrawable(resources.getDrawable(R.drawable.vehicle_bg_capture_uploaded));
        mDriveLicenceIcon.setVisibility(View.INVISIBLE);
        mDriveLicenceText.setText(resources.getString(R.string.vehicle_drive_licence_count, count));
        mDriveLicenceText.setTextColor(resources.getColor(R.color.color_white));
    }

    @Override
    public void showOwnershipCount(int count) {
        Resources resources = getContext().getResources();
        mVehicleOwnership.setBackgroundDrawable(resources.getDrawable(R.drawable.vehicle_bg_capture_uploaded));
        mVehicleOwnershipIcon.setVisibility(View.INVISIBLE);
        mVehicleOwnershipText.setText(resources.getString(R.string.vehicle_ownership_count, count));
        mVehicleOwnershipText.setTextColor(resources.getColor(R.color.color_white));
    }
}
