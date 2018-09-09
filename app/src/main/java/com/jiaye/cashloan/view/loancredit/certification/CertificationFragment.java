package com.jiaye.cashloan.view.loancredit.certification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFunctionFragment;
import com.jiaye.cashloan.view.loancredit.step1.BaseStepFragment;
import com.jiaye.cashloan.view.loancredit.certification.source.CertificationRepository;
import com.jiaye.cashloan.view.loancredit.step1.Step1Fragment;
import com.jiaye.cashloan.view.loancredit.step2.Step2Fragment;
import com.jiaye.cashloan.view.loancredit.step3.Step3Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * JdCarFragment
 *
 * @author 贾博瑄
 */

public class CertificationFragment extends BaseFunctionFragment implements CertificationContract.View {

    private CertificationContract.Presenter mPresenter;

    private LocalBroadcastManager mLocalBroadcastManager;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("CreditRefresh".equals(action)) {
                mPresenter.requestStep();
            }
        }
    };

    private TextView mTextStep1;

    private TextView mTextStep2;

    private TextView mTextStep3;

    private int currentFragmentIndex;

    private List<BaseStepFragment> fragments;

    public static CertificationFragment newInstance() {
        Bundle args = new Bundle();
        CertificationFragment fragment = new CertificationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static void refresh(Context context) {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
        Intent intent = new Intent("CreditRefresh");
        localBroadcastManager.sendBroadcast(intent);
    }

    @Override
    protected int getTitleId() {
        return R.string.credit_certification_title_1;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View view = inflater.inflate(R.layout.credit_certification_fragment, frameLayout, true);
        mTextStep1 = view.findViewById(R.id.text_step_1);
        mTextStep2 = view.findViewById(R.id.text_step_2);
        mTextStep3 = view.findViewById(R.id.text_step_3);

        fragments = new ArrayList<>();
        fragments.add(Step1Fragment.newInstance());
        fragments.add(Step2Fragment.newInstance());
        fragments.add(Step3Fragment.newInstance());

        mPresenter = new CertificationPresenter(this, new CertificationRepository());
        mPresenter.subscribe();
        mPresenter.requestStep();
        IntentFilter intentFilter = new IntentFilter("CreditRefresh");
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        mLocalBroadcastManager.registerReceiver(mReceiver, intentFilter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
        mLocalBroadcastManager.unregisterReceiver(mReceiver);
    }

    public int getCurrentFragmentIndex() {
        return currentFragmentIndex;
    }

    @Override
    public void setStep(int step) {
        showStepView(2);
        if (step == 1) {
            //showStepView(1);
            setTitle(getResources().getString(R.string.credit_certification_title_1));
            currentFragmentIndex = 1;
            //Step1Fragment.refresh(getActivity());
        } else if (step == 4 || step == 8) {
            //showStepView(2);
            setTitle(getResources().getString(R.string.credit_certification_title_2));
            currentFragmentIndex = 2;
            //Step2Fragment.refresh(getActivity());
        } else if (step == 5 || step == 6 || step == 3 || step == 7 || step == 10) {
            //showStepView(3);
            setTitle(getResources().getString(R.string.credit_certification_title_3));
            currentFragmentIndex = 3;
            //Step3Fragment.refresh(getActivity());
        }
        switch (step) {
            case 10:
            case 7:
            case 6:
            case 5:
            case 3:
                mTextStep3.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        getResources().getDrawable(R.drawable.credit_step_ic_blue),
                        null,
                        null);
            case 8:
            case 4:
                mTextStep2.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        getResources().getDrawable(R.drawable.credit_step_ic_blue),
                        null,
                        null);
            case 1:
                mTextStep1.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        getResources().getDrawable(R.drawable.credit_step_ic_blue),
                        null,
                        null);
                break;
        }
    }

    private void showStepView(int index) {
        getFragmentManager().beginTransaction().replace(R.id.content, fragments.get(index - 1)).commit();
    }
}
