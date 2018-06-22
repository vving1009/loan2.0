package com.jiaye.cashloan.view.step4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.certification.CertificationFragment;
import com.jiaye.cashloan.view.step1.BaseStepFragment;
import com.jiaye.cashloan.view.step4.source.Step4Repository;

/**
 * Step4Fragment
 *
 * @author 贾博瑄
 */

public class Step4Fragment extends BaseStepFragment implements Step4Contract.View {

    private Step4Contract.Presenter mPresenter;

    private LinearLayout mLayout1;

    private LinearLayout mLayout2;

    private TextView mText;

    private Button mBtnNext;

    public static Step4Fragment newInstance() {
        Bundle args = new Bundle();
        Step4Fragment fragment = new Step4Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.step4_fragment, container, false);
        mLayout1 = root.findViewById(R.id.layout_1);
        mLayout2 = root.findViewById(R.id.layout_2);
        mText = root.findViewById(R.id.text);
        mBtnNext = root.findViewById(R.id.btn_next);
        mBtnNext.setOnClickListener(v -> mPresenter.onClickNext());
        mPresenter = new Step4Presenter(this, new Step4Repository());
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
        mText.setText(msg);
    }

    @Override
    public void setLayoutVisibility() {
        mLayout1.setVisibility(View.INVISIBLE);
        mLayout2.setVisibility(View.VISIBLE);
    }

    @Override
    public void setBtnVisibility(boolean visibility) {
        if (visibility) {
            mBtnNext.setVisibility(View.VISIBLE);
        } else {
            mBtnNext.setVisibility(View.INVISIBLE);
        }
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
