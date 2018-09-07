package com.jiaye.cashloan.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.jiaye.cashloan.R;

public class CreditLoanFirstPageFragment extends BaseFunctionFragment {

    public static CreditLoanFirstPageFragment newInstance() {
        Bundle args = new Bundle();
        CreditLoanFirstPageFragment fragment = new CreditLoanFirstPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getTitleId() {
        return R.string.credit_loan_titile;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View root = inflater.inflate(R.layout.credit_first_page_fragment, frameLayout, true);
        return root;
    }
}
