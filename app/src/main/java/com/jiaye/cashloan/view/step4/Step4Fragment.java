package com.jiaye.cashloan.view.step4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.step4.source.Step4Repository;

/**
 * Step4Fragment
 *
 * @author 贾博瑄
 */

public class Step4Fragment extends BaseFragment implements Step4Contract.View {

    private Step4Contract.Presenter mPresenter;

    public static Step4Fragment newInstance() {
        Bundle args = new Bundle();
        Step4Fragment fragment = new Step4Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.step4_fragment,container,false);
        mPresenter = new Step4Presenter(this, new Step4Repository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }
}
