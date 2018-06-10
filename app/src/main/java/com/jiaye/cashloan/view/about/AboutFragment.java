package com.jiaye.cashloan.view.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFunctionFragment;

/**
 * AboutFragment
 *
 * @author 贾博瑄
 */

public class AboutFragment extends BaseFunctionFragment {

    public static AboutFragment newInstance() {
        Bundle args = new Bundle();
        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getTitleId() {
        return R.string.my_about;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        return inflater.inflate(R.layout.about_fragment, frameLayout, true);
    }
}
