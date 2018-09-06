package com.jiaye.cashloan.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.home.HomeFragment;

/**
 * HomeFragment
 *
 * @author 贾博瑄
 */
public class CarLoanFirstPageFragment extends BaseFunctionFragment {


    public static CarLoanFirstPageFragment newInstance() {
        Bundle args = new Bundle();
        CarLoanFirstPageFragment fragment = new CarLoanFirstPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getTitleId() {
        return R.string.car_loan_titile;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View root = inflater.inflate(R.layout.car_first_page_fragment, frameLayout, true);
        return root;
    }
}
