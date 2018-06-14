package com.jiaye.cashloan.view.step1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiaye.cashloan.view.BaseFragment;

/**
 * BaseStepFragment
 *
 * @author 贾博瑄
 */
public abstract class BaseStepFragment extends BaseFragment {

    private LocalBroadcastManager mLocalBroadcastManager;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("refresh_child".equals(action)) {
                requestStep();
            }
        }
    };

    public static void refresh(Context context) {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
        Intent intent = new Intent("refresh_child");
        localBroadcastManager.sendBroadcast(intent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        IntentFilter intentFilter = new IntentFilter("refresh_child");
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        mLocalBroadcastManager.registerReceiver(mReceiver, intentFilter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLocalBroadcastManager.unregisterReceiver(mReceiver);
    }

    protected abstract void requestStep();

}
