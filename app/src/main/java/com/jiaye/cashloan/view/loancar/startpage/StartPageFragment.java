package com.jiaye.cashloan.view.loancar.startpage;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFunctionFragment;

/**
 * HomeFragment
 *
 * @author 贾博瑄
 */
public class StartPageFragment extends BaseFunctionFragment implements StartPageContract.View {


    public static StartPageFragment newInstance() {
        Bundle args = new Bundle();
        StartPageFragment fragment = new StartPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getTitleId() {
        return R.string.car_loan_titile;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View root = inflater.inflate(R.layout.car_start_page_fragment, frameLayout, true);
        return root;
    }
}
