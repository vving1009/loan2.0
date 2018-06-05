package com.jiaye.cashloan.view.certification;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFunctionFragment;
import com.jiaye.cashloan.view.certification.source.CertificationRepository;
import com.jiaye.cashloan.view.step1.Step1Fragment;
import com.jiaye.cashloan.view.step2.Step2Fragment;
import com.jiaye.cashloan.view.step3.Step3Fragment;
import com.jiaye.cashloan.view.step4.Step4Fragment;
import com.jiaye.cashloan.widget.NoScrollViewPager;
import com.jiaye.cashloan.widget.ScrollOffsetTransformer;
import com.jiaye.cashloan.widget.ViewPagerScroller;

/**
 * CertificationFragment
 *
 * @author 贾博瑄
 */

public class CertificationFragment extends BaseFunctionFragment implements CertificationContract.View {

    private CertificationContract.Presenter mPresenter;

    private TextView mTextCompany;

    private TextView mTextNumber;

    private TextView mTextStep1;

    private TextView mTextStep2;

    private TextView mTextStep3;

    private TextView mTextStep4;

    private NoScrollViewPager mViewPager;

    public static CertificationFragment newInstance() {
        Bundle args = new Bundle();
        CertificationFragment fragment = new CertificationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getTitleId() {
        return R.string.certification_title;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View view = inflater.inflate(R.layout.certification_fragment, frameLayout, true);
        mTextCompany = view.findViewById(R.id.text_company);
        mTextNumber = view.findViewById(R.id.text_number);
        mViewPager = view.findViewById(R.id.view_pager);
        mTextStep1 = view.findViewById(R.id.text_step_1);
        mTextStep2 = view.findViewById(R.id.text_step_2);
        mTextStep3 = view.findViewById(R.id.text_step_3);
        mTextStep4 = view.findViewById(R.id.text_step_4);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return Step1Fragment.newInstance();
                    case 1:
                        return Step2Fragment.newInstance();
                    case 2:
                        return Step3Fragment.newInstance();
                    case 3:
                        return Step4Fragment.newInstance();
                }
                return null;
            }

            @Override
            public int getCount() {
                return 4;
            }
        });
        mViewPager.setPageTransformer(false, new ScrollOffsetTransformer());
        ViewPagerScroller pagerScroller = new ViewPagerScroller(getActivity());
        pagerScroller.setScrollDuration(600);
        pagerScroller.initViewPagerScroll(mViewPager);
        mPresenter = new CertificationPresenter(this, new CertificationRepository());
        mPresenter.subscribe();
        return view;
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
    public void setCompany(String company) {
        mTextCompany.setText(company);
    }

    @Override
    public void setNumber(String number) {
        mTextNumber.setText(number);
    }

    @Override
    public void setStep(int step) {
        mViewPager.setLock(step);
        switch (step) {
            case 3:
                mTextStep3.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        getResources().getDrawable(R.drawable.certification_ic_step_3_end),
                        null,
                        null);
                mTextStep4.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        getResources().getDrawable(R.drawable.certification_ic_step_4_end),
                        null,
                        null);
            case 2:
                mTextStep2.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        getResources().getDrawable(R.drawable.certification_ic_step_2_end),
                        null,
                        null);
            case 1:
                mTextStep1.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        getResources().getDrawable(R.drawable.certification_ic_step_1_end),
                        null,
                        null);
                break;
        }
    }
}
