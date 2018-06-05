package com.jiaye.cashloan.view.step3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.step3.source.Step3Repository;

/**
 * Step3Fragment
 *
 * @author 贾博瑄
 */

public class Step3Fragment extends BaseFragment implements Step3Contract.View {

    private Step3Contract.Presenter mPresenter;

    public static Step3Fragment newInstance() {
        Bundle args = new Bundle();
        Step3Fragment fragment = new Step3Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.step3_fragment,container,false);
        mPresenter = new Step3Presenter(this, new Step3Repository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }
}
