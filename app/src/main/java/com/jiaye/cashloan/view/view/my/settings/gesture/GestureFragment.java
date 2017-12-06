package com.jiaye.cashloan.view.view.my.settings.gesture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.syd.oden.gesturelock.view.GestureLockViewGroup;
import com.syd.oden.gesturelock.view.listener.GesturePasswordSettingListener;

/**
 * GestureFragment
 *
 * @author 贾博瑄
 */

public class GestureFragment extends BaseFragment {

    private GestureLockViewGroup mGesture;

    private TextView mTextGesture;

    public static GestureFragment newInstance() {
        Bundle args = new Bundle();
        GestureFragment fragment = new GestureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gesture_fragment, container, false);
        mGesture = view.findViewById(R.id.gesture_view);
        mTextGesture = view.findViewById(R.id.text_gesture);
        mGesture.setGesturePasswordSettingListener(new GesturePasswordSettingListener() {
            @Override
            public boolean onFirstInputComplete(int len) {
                if (len > 3) {
                    mTextGesture.setText("再次绘制手势密码");
                    return true;
                } else {
                    mTextGesture.setText("最少连接4个点，请重新输入!");
                    return false;
                }
            }

            @Override
            public void onSuccess() {
                mTextGesture.setText("手势密码设置成功");
                getActivity().finish();
            }

            @Override
            public void onFail() {
                mTextGesture.setText("与上一次绘制不一致，请重新绘制");
            }
        });
        mGesture.removePassword();
        return view;
    }
}
