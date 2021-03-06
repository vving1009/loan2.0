package com.jiaye.cashloan.view.bioassay;

import android.os.Bundle;
import android.widget.Toast;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.bioassay.source.BioassayRepository;
import com.jiaye.cashloan.widget.CustomProgressDialog;
import com.oliveapp.face.livenessdetectorsdk.livenessdetector.datatype.LivenessDetectionFrames;

import cn.tongdun.android.liveness.view_controller.LivenessDetectionMainActivity;

/**
 * BioassayActivity
 *
 * @author 贾博瑄
 */

public class BioassayActivity extends LivenessDetectionMainActivity implements BioassayContract.View {

    private BioassayContract.Presenter mPresenter;

    private CustomProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new BioassayPresenter(this, new BioassayRepository());
        mPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void onInitializeSucc() {
        super.onInitializeSucc();
        super.startVerification();
    }

    @Override
    public void onInitializeFail(Throwable e) {
        super.onInitializeFail(e);
        result();
    }

    @Override
    public void onLivenessSuccess(LivenessDetectionFrames livenessDetectionFrames) {
        super.onLivenessSuccess(livenessDetectionFrames);
        mPresenter.submit(livenessDetectionFrames.verificationPackage);
    }

    @Override
    public void onLivenessFail(int result, LivenessDetectionFrames livenessDetectionFrames) {
        super.onLivenessFail(result, livenessDetectionFrames);
        showToastById(R.string.loan_auth_face_fail);
    }

    @Override
    public void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToastById(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        if (mDialog == null) {
            mDialog = new CustomProgressDialog(this);
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
        }
        mDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    public void result() {
        finish();
    }
}
