package com.jiaye.cashloan.view.step1.result;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.FunctionActivity;
import com.jiaye.cashloan.view.certification.CertificationFragment;
import com.jiaye.cashloan.view.step1.BaseStepFragment;
import com.jiaye.cashloan.view.step1.result.source.Step1ResultRepository;

/**
 * Step3ResultFragment
 *
 * @author 贾博瑄
 */

public class Step1ResultFragment extends BaseStepFragment implements Step1ResultContract.View {

    private Step1ResultContract.Presenter mPresenter;

    private TextView mText;

    private Button mBtnNext;

    public static Step1ResultFragment newInstance(String... value) {
        Bundle args = new Bundle();
        args.putStringArray("value", value);
        Step1ResultFragment fragment = new Step1ResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.step1_result_fragment, container, false);
        assert getArguments() != null;
        String[] value = getArguments().getStringArray("value");
        mText = root.findViewById(R.id.text);
        if (value != null && value.length == 2) {
            mText.setText(getResources().getString(R.string.step1_valuation, value[0], value[1]));
        }
        mBtnNext = root.findViewById(R.id.btn_next);
        mBtnNext.setOnClickListener(v -> {
            mPresenter.onClickConfirm();
        });
        mPresenter = new Step1ResultPresenter(this, new Step1ResultRepository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void sendBroadcast() {
        CertificationFragment.refresh(getActivity());
    }

    @Override
    protected void requestStep() {

    }
}
