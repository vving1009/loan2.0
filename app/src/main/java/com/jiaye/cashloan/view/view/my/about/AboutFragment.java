package com.jiaye.cashloan.view.view.my.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;

/**
 * AboutFragment
 *
 * @author 贾博瑄
 */

public class AboutFragment extends BaseFragment {

    public static AboutFragment newInstance() {
        Bundle args = new Bundle();
        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_fragment, container, false);
    }
}
