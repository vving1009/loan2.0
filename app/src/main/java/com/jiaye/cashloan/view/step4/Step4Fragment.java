package com.jiaye.cashloan.view.step4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    private TextView mText;

    private ImageView mImage;

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
        mText = root.findViewById(R.id.center_text);
        mImage = root.findViewById(R.id.center_image);

        mPresenter = new Step4Presenter(this, new Step4Repository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (((CertificationFragment) getParentFragment()).getCurrentFragmentIndex() == 4) {
            mPresenter.requestStep();
        }
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
    public void setWaitLoan() {
        mImage.setImageDrawable(getResources().getDrawable(R.drawable.certification_ic_wait));
        mText.setText(R.string.step4_wait);
    }

    @Override
    public void setFinishLoan() {
        mImage.setImageDrawable(getResources().getDrawable(R.drawable.certification_ic_money));
        mText.setText(R.string.step4_finish);
    }

    @Override
    public void sendBroadcast() {
        CertificationFragment.refresh(getActivity());
    }
}
