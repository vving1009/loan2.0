package com.jiaye.cashloan.view.step2.parent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.step1.BaseStepFragment;
import com.jiaye.cashloan.view.step2.input.Step2InputFragment;
import com.jiaye.cashloan.view.step2.parent.source.Step2Repository;
import com.jiaye.cashloan.view.step2.result.Step2ResultFragment;

/**
 * Step1InputFragment
 *
 * @author 贾博瑄
 */

public class Step2Fragment extends BaseStepFragment implements Step2Contract.View {

    private Step2InputFragment mInputFragment;

    private Step2ResultFragment mResultFragment;

    private FragmentManager mFragmentManager;

    private Step2Contract.Presenter mPresenter;

    private int mainStep;

    public static Step2Fragment newInstance() {
        Bundle args = new Bundle();
        Step2Fragment fragment = new Step2Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnNextClickListener {
        void onClick(String value);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.step2_fragment, container, false);
        mInputFragment = Step2InputFragment.newInstance();
        mInputFragment.setOnNextClickListener(this::showResultView);
        mFragmentManager = getFragmentManager();
        showInputView();
        mPresenter = new Step2Presenter(this, new Step2Repository());
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
        mFragmentManager.beginTransaction().replace(R.id.step2_content, mInputFragment).commit();
    }

    @Override
    public void showResultView(String value) {
        mResultFragment = Step2ResultFragment.newInstance(value);
        mFragmentManager.beginTransaction().replace(R.id.step2_content, mResultFragment).commit();
    }

    @Override
    protected void requestStep() {
        /*if (mainStep == 1) {
            showInputView();
        } else {
            showResultView();
        }*/
    }

    public void setMainStep(int mainStep) {
        this.mainStep = mainStep;
    }


}
