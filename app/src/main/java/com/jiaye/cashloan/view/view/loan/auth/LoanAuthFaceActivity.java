package com.jiaye.cashloan.view.view.loan.auth;

import android.content.Intent;
import android.os.Handler;

import com.oliveapp.face.livenessdetectorsdk.livenessdetector.datatype.LivenessDetectionFrames;

import cn.tongdun.android.liveness.view_controller.LivenessDetectionMainActivity;

/**
 * LoanAuthFaceActivity
 *
 * @author 贾博瑄
 */

public class LoanAuthFaceActivity extends LivenessDetectionMainActivity {

    public static final int REQUEST_FACE = 202;

    @Override
    public void onInitializeSucc() {
        super.onInitializeSucc();
        super.startVerification();
    }

    @Override
    public void onInitializeFail(Throwable e) {
        super.onInitializeFail(e);
        result(false);
    }

    @Override
    public void onLivenessSuccess(LivenessDetectionFrames livenessDetectionFrames) {
        super.onLivenessSuccess(livenessDetectionFrames);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                result(true);
            }
        }, 2000);
    }

    @Override
    public void onLivenessFail(int result, LivenessDetectionFrames livenessDetectionFrames) {
        super.onLivenessFail(result, livenessDetectionFrames);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                result(false);
            }
        }, 2000);
    }

    private void result(boolean success) {
        Intent intent = new Intent();
        intent.putExtra("is_success", success);
        setResult(REQUEST_FACE, intent);
        finish();
    }
}
