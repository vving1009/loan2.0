package com.jiaye.cashloan.view.taobao;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFunctionFragment;
import com.jiaye.cashloan.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * TaoBaoFragment
 *
 * @author 贾博瑄
 */

public class TaoBaoFragment extends BaseFunctionFragment {

    private static final int[] TAB_TEXT = new int[]{R.string.loan_auth_taobao_normal, R.string.loan_auth_taobao_qr};

    private NoScrollViewPager mViewPager;

    private List<View> indicators = new ArrayList<>();

    private TaoBaoNormalFragment normal() {
        return TaoBaoNormalFragment.newInstance();
    }

    private TaoBaoQRFragment qr() {
        return TaoBaoQRFragment.newInstance();
    }

    public static TaoBaoFragment newInstance() {
        Bundle args = new Bundle();
        TaoBaoFragment fragment = new TaoBaoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, int[] text) {
        for (int aText : text) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.taobao_tab, null);
            tab.setCustomView(view);
            TextView tvTitle = view.findViewById(R.id.text);
            View indicator = view.findViewById(R.id.indicator);
            tvTitle.setText(aText);
            indicators.add(indicator);
            tabLayout.addTab(tab);
        }
    }

    @Override
    protected int getTitleId() {
        return R.string.taobao_title;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.taobao_fragment, frameLayout, true);
        mViewPager = rootView.findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return normal();
                    case 1:
                        return qr();
                    default:
                        return normal();
                }
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        });
        TabLayout tabLayout = rootView.findViewById(R.id.tab_layout);
        setTabs(tabLayout, getLayoutInflater(), TAB_TEXT);
        indicators.get(0).setVisibility(View.VISIBLE);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {
                    String name = "android:switcher:" + mViewPager.getId() + ":" + "1";
                    TaoBaoQRFragment fragment = (TaoBaoQRFragment) getActivity().getSupportFragmentManager().findFragmentByTag(name);
                    fragment.request();
                }
                for (View indicator : indicators) {
                    indicator.setVisibility(View.INVISIBLE);
                }
                indicators.get(tab.getPosition()).setVisibility(View.VISIBLE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return rootView;
    }
}
