package com.jiaye.cashloan.view.step3.parent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.step1.BaseStepFragment;
import com.jiaye.cashloan.view.step3.input.Step3InputFragment;
import com.jiaye.cashloan.view.step3.parent.source.Step3Repository;
import com.jiaye.cashloan.view.step3.result.Step3ResultFragment;

/**
 * Step1InputFragment
 *
 * @author 贾博瑄
 */

public class Step3Fragment extends BaseStepFragment implements Step3Contract.View {

    private Step3InputFragment mInputFragment;

    private Step3ResultFragment mResultFragment;

    private FragmentManager mFragmentManager;

    private Step3Contract.Presenter mPresenter;

    private int mainStep;

    public static Step3Fragment newInstance() {
        Bundle args = new Bundle();
        Step3Fragment fragment = new Step3Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnNextClickListener {
        void onClick();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.step3_fragment, container, false);
        mInputFragment = Step3InputFragment.newInstance();
        mInputFragment.setOnNextClickListener(this::showResultView);
        mFragmentManager = getFragmentManager();
        showInputView();
        mPresenter = new Step3Presenter(this, new Step3Repository());
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
        mFragmentManager.beginTransaction().replace(R.id.step3_content, mInputFragment).commit();
    }

    @Override
    public void showResultView() {
        mResultFragment = Step3ResultFragment.newInstance();
        mFragmentManager.beginTransaction().replace(R.id.step3_content, mResultFragment).commit();
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
