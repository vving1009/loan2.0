package com.jiaye.cashloan.view.view.launch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.view.main.MainActivity;
import com.syd.oden.gesturelock.view.GestureLockViewGroup;
import com.syd.oden.gesturelock.view.listener.GestureEventListener;
import com.syd.oden.gesturelock.view.listener.GestureUnmatchedExceedListener;

/**
 * LaunchGestureActivity
 *
 * @author 贾博瑄
 */

public class LaunchGestureActivity extends BaseActivity {

    private GestureLockViewGroup mGesture;

    private TextView mTextGesture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_gesture_activity);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mGesture = findViewById(R.id.gesture_view);
        mTextGesture = findViewById(R.id.text_gesture);
        mGesture.setGestureEventListener(new GestureEventListener() {
            @Override
            public void onGestureEvent(boolean matched) {
                if (matched) {
                    showMainView();
                } else {
                    mTextGesture.setText("手势密码输入错误");
                }
            }
        });
        mGesture.setGestureUnmatchedExceedListener(5, new GestureUnmatchedExceedListener() {
            @Override
            public void onUnmatchedExceedBoundary() {
                cleanData();
                showMainView();
            }
        });
    }

    private void showMainView() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void cleanData() {
        mGesture.removePassword();
        LoanApplication.getInstance().getSQLiteDatabase().delete("user", null, null);
        LoanApplication.getInstance().getSQLiteDatabase().delete("product", null, null);
    }
}
