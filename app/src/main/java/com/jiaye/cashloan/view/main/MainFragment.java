package com.jiaye.cashloan.view.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.home.HomeFragment;
import com.jiaye.cashloan.view.my.MyFragment;
import com.jiaye.cashloan.widget.NoScrollViewPager;

/**
 * MainFragment
 *
 * @author 贾博瑄
 */

public class MainFragment extends BaseFragment {

    private static final int[] TAB_ICON = new int[]{R.drawable.main_ic_home, R.drawable.main_ic_my};

    private static final int[] TAB_TEXT = new int[]{R.string.home, R.string.my};

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_fragment, container, false);
        NoScrollViewPager viewPager = root.findViewById(R.id.view_pager);
        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return HomeFragment.newInstance();
                    case 1:
                        return MyFragment.newInstance();
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        TabLayout tabLayout = root.findViewById(R.id.tab_layout);
        setTabs(tabLayout, inflater, TAB_ICON, TAB_TEXT);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        return root;
    }

    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, int[] icon, int[] text) {
        for (int i = 0; i < icon.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.main_tab, null);
            tab.setCustomView(view);
            TextView tvTitle = view.findViewById(R.id.text);
            tvTitle.setText(text[i]);
            ImageView imgTab = view.findViewById(R.id.img);
            imgTab.setImageResource(icon[i]);
            tabLayout.addTab(tab);
        }
    }
}
