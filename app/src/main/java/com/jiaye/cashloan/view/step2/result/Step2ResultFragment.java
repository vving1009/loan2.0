package com.jiaye.cashloan.view.step2.result;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.certification.CertificationFragment;
import com.jiaye.cashloan.view.step1.BaseStepFragment;
import com.jiaye.cashloan.view.step2.result.source.Step2ResultRepository;

/**
 * Step3ResultFragment
 *
 * @author 贾博瑄
 */

public class Step2ResultFragment extends BaseStepFragment implements Step2ResultContract.View {

    private Step2ResultContract.Presenter mPresenter;

    private TextView mText;

    private Button mBtnNext;

    public static Step2ResultFragment newInstance(String value) {
        Bundle args = new Bundle();
        args.putString("value", value);
        Step2ResultFragment fragment = new Step2ResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.step2_result_fragment, container, false);
        mText = root.findViewById(R.id.text);
        if (getArguments() != null) {
            mText.setText(getResources().getString(R.string.step2_loan_limit, getArguments().getString("value")));
        }
        mBtnNext = root.findViewById(R.id.btn_next);
        mBtnNext.setOnClickListener(v -> {
            mPresenter.onClickConfirm();
        });
        mPresenter = new Step2ResultPresenter(this, new Step2ResultRepository());
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
