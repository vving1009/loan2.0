package com.jiaye.cashloan.view.step1.parent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.step1.BaseStepFragment;
import com.jiaye.cashloan.view.step1.input.Step1InputFragment;
import com.jiaye.cashloan.view.step1.parent.source.Step1Repository;
import com.jiaye.cashloan.view.step1.result.Step1ResultFragment;

/**
 * Step1InputFragment
 *
 * @author 贾博瑄
 */

public class Step1Fragment extends BaseStepFragment implements Step1Contract.View {

    private Step1InputFragment mInputFragment;
    private Step1ResultFragment mResultFragment;
    private FragmentManager mFragmentManager;

    private Step1Contract.Presenter mPresenter;

    public static Step1Fragment newInstance() {
        Bundle args = new Bundle();
        Step1Fragment fragment = new Step1Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnNextClickListener {
        void onClick(String... value);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.step1_fragment, container, false);
        mInputFragment = Step1InputFragment.newInstance();
        mInputFragment.setOnNextClickListener(this::showResultView);
        mFragmentManager = getFragmentManager();
        showInputView();
        mPresenter = new Step1Presenter(this, new Step1Repository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }


    @Override
    public void showInputView() {
        mFragmentManager.beginTransaction().replace(R.id.step1_content, mInputFragment).commit();
    }

    @Override
    public void showResultView(String... value) {
        mResultFragment = Step1ResultFragment.newInstance(value);
        mFragmentManager.beginTransaction().replace(R.id.step1_content, mResultFragment).commit();
    }

    @Override
    protected void requestStep() {

    }
}
