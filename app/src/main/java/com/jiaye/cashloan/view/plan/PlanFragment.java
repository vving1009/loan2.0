package com.jiaye.cashloan.view.plan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.BaseFunctionFragment;
import com.jiaye.cashloan.view.plan.source.PlanRepository;

/**
 * PlanFragment
 *
 * @author 贾博瑄
 */

public class PlanFragment extends BaseFunctionFragment implements PlanContract.View {

    private PlanContract.Presenter mPresenter;

    public static PlanFragment newInstance() {
        Bundle args = new Bundle();
        PlanFragment fragment = new PlanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getTitleId() {
        return R.string.loan_plan_title;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        mPresenter = new PlanPresenter(this, new PlanRepository());
        mPresenter.subscribe();
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }
}
