package com.jiaye.cashloan.view.view.my.certificate.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.widget.NoScrollViewPager;

/**
 * InfoFragment
 *
 * @author 贾博瑄
 */

public class InfoFragment extends BaseFragment {

    private static final int[] TAB_TEXT = new int[]{R.string.loan_auth_person_info, R.string.loan_auth_contact_info};

    private NoScrollViewPager mViewPager;

    public static InfoFragment newInstance() {
        Bundle args = new Bundle();
        InfoFragment fragment = new InfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_fragment, container, false);
        mViewPager = view.findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return person();
                    case 1:
                        return contact();
                    default:
                        return person();
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        setTabs(tabLayout, getLayoutInflater(), TAB_TEXT);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        return view;
    }

    private Fragment person() {
        return InfoPersonFragment.newInstance();
    }

    private Fragment contact() {
        return InfoContactFragment.newInstance();
    }

    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, int[] text) {
        for (int aText : text) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.info_tab, null);
            tab.setCustomView(view);
            TextView tvTitle = view.findViewById(R.id.text);
            tvTitle.setText(aText);
            tabLayout.addTab(tab);
        }
    }
}
