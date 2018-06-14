package com.jiaye.cashloan.view.step2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.certification.CertificationFragment;
import com.jiaye.cashloan.view.step1.BaseStepFragment;
import com.jiaye.cashloan.view.step2.source.Step2Repository;

/**
 * Step2Fragment
 *
 * @author 贾博瑄
 */

public class Step2Fragment extends BaseStepFragment implements Step2Contract.View {

    private Step2Contract.Presenter mPresenter;

    private TextView mView;

    public static Step2Fragment newInstance() {
        Bundle args = new Bundle();
        Step2Fragment fragment = new Step2Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.step2_fragment, container, false);
        mView = root.findViewById(R.id.text);
        root.findViewById(R.id.btn_next).setOnClickListener(v -> mPresenter.onClickNext());
        mPresenter = new Step2Presenter(this, new Step2Repository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.requestStep();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    protected void requestStep() {
        mPresenter.requestStep();
    }

    @Override
    public void setText(String msg) {
        mView.setText(msg);
    }

    @Override
    public void sendBroadcast() {
        CertificationFragment.refresh(getActivity());
    }

    @Override
    public void finish() {
        getActivity().finish();
    }
}
