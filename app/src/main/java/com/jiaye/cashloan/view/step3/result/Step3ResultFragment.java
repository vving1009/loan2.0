package com.jiaye.cashloan.view.step3.result;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.FunctionActivity;
import com.jiaye.cashloan.view.certification.CertificationFragment;
import com.jiaye.cashloan.view.step1.BaseStepFragment;
import com.jiaye.cashloan.view.step3.result.source.Step3ResultRepository;

/**
 * Step3ResultFragment
 *
 * @author 贾博瑄
 */

public class Step3ResultFragment extends BaseStepFragment implements Step3ResultContract.View {

    private Step3ResultContract.Presenter mPresenter;

    private ImageView mCenterImg;

    private TextView mCenterText;

    private Button mBtnNext;

    private CheckBox mCheckBox;

    public static Step3ResultFragment newInstance() {
        Bundle args = new Bundle();
        Step3ResultFragment fragment = new Step3ResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.step3_result_fragment, container, false);
        mCenterImg = root.findViewById(R.id.center_image);
        mCenterText = root.findViewById(R.id.center_text);
        mCheckBox = root.findViewById(R.id.sign_check_box);
        mBtnNext = root.findViewById(R.id.btn_next);
        mBtnNext.setOnClickListener(v -> {
            if (mCheckBox.isChecked()) {
                mPresenter.onClickConfirm();
            }
        });
        //showAmountView("63000");
        //showApprovingView();
        showRejectView();
        mPresenter = new Step3ResultPresenter(this, new Step3ResultRepository());
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

    @Override
    public void showApprovingView() {
        mCenterImg.setImageDrawable(getResources().getDrawable(R.drawable.certification_ic_wait));
        mCenterText.setText(R.string.step3_approving);
        mBtnNext.setVisibility(View.INVISIBLE);
        mCheckBox.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showRejectView() {
        mCenterImg.setImageDrawable(getResources().getDrawable(R.drawable.certification_ic_reject));
        mCenterText.setText(R.string.step3_reject);
        mBtnNext.setVisibility(View.INVISIBLE);
        mCheckBox.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showAmountView(String value) {
        mCenterImg.setImageDrawable(getResources().getDrawable(R.drawable.certification_ic_shop));
        mCenterText.setText(getResources().getString(R.string.step3_amount, value));
        mBtnNext.setVisibility(View.VISIBLE);
        mCheckBox.setVisibility(View.VISIBLE);
    }
}
