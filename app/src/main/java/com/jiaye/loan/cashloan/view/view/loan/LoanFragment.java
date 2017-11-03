package com.jiaye.loan.cashloan.view.view.loan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiaye.loan.cashloan.R;
import com.jiaye.loan.cashloan.view.BaseFragment;

/**
 * LoanFragment
 *
 * @author 贾博瑄
 */

public class LoanFragment extends BaseFragment {

    /**
     * 类型
     *
     * @param type 类型 1 2
     * @return LoanFragment
     */
    public static LoanFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        LoanFragment fragment = new LoanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.loan_fragment, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
