package com.jiaye.cashloan.view.insurance
        ;

import android.os.Bundle;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.MoxieFragment;
import com.jiaye.cashloan.view.insurance.source.InsuranceRepository;

/**
 * InsuranceFragment
 *
 * @author 贾博瑄
 */

public class InsuranceFragment extends MoxieFragment implements InsuranceContract.View {

    private static final String TAG = "InsuranceFragment";

    private InsuranceContract.Presenter mPresenter;

    public static InsuranceFragment newInstance() {
        Bundle args = new Bundle();
        InsuranceFragment fragment = new InsuranceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BasePresenter getPresenter() {
        mPresenter = new InsurancePresenter(this, new InsuranceRepository());
        return mPresenter;
    }

    @Override
    protected String getMoxieType() {
        return "inslist";
    }

    @Override
    protected String getMoxieParams() {
        return "";
    }

    @Override
    protected void notifyService() {

    }
}
