package com.jiaye.cashloan.view.certification;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

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
        NoScrollViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setLock(4);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
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
        viewPager.setPageTransformer(false, new ScrollOffsetTransformer());
        ViewPagerScroller pagerScroller = new ViewPagerScroller(getActivity());
        pagerScroller.setScrollDuration(600);
        pagerScroller.initViewPagerScroll(viewPager);
        mPresenter = new CertificationPresenter(this, new CertificationRepository());
        mPresenter.subscribe();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }
}
