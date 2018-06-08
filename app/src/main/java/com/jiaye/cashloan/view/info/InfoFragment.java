package com.jiaye.cashloan.view.info;

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
 * InfoFragment
 *
 * @author 贾博瑄
 */

public class InfoFragment extends BaseFunctionFragment {

    private static final int[] TAB_TEXT = new int[]{R.string.loan_auth_person_info, R.string.loan_auth_contact_info};

    private NoScrollViewPager mViewPager;

    private List<View> indicators = new ArrayList<>();

    private PersonalFragment personal() {
        return PersonalFragment.newInstance();
    }

    private ContactFragment contact() {
        return ContactFragment.newInstance();
    }

    public static InfoFragment newInstance() {
        Bundle args = new Bundle();
        InfoFragment fragment = new InfoFragment();
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
        return R.string.loan_auth_info;
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
                        return personal();
                    case 1:
                        return contact();
                    default:
                        return personal();
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
                    ContactFragment fragment = (ContactFragment) getActivity().getSupportFragmentManager().findFragmentByTag(name);
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
