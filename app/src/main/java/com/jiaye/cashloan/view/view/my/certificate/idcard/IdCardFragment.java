package com.jiaye.cashloan.view.view.my.certificate.idcard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiaye.cashloan.view.BaseFragment;

/**
 * IdCardFragment
 *
 * @author 贾博瑄
 */

public class IdCardFragment extends BaseFragment {

    public static IdCardFragment newInstance() {
        Bundle args = new Bundle();
        IdCardFragment fragment = new IdCardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
