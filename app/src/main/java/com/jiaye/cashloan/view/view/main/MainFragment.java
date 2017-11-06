package com.jiaye.cashloan.view.view.main;

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
import com.jiaye.cashloan.view.data.home.source.HomeRepository;
import com.jiaye.cashloan.view.data.loan.source.LoanRepository;
import com.jiaye.cashloan.view.data.my.source.MyRepository;
import com.jiaye.cashloan.view.view.home.HomeFragment;
import com.jiaye.cashloan.view.view.home.HomePresenter;
import com.jiaye.cashloan.view.view.loan.LoanFragment;
import com.jiaye.cashloan.view.view.loan.LoanPresenter;
import com.jiaye.cashloan.view.view.my.MyFragment;
import com.jiaye.cashloan.view.view.my.MyPresenter;
import com.jiaye.cashloan.widget.NoScrollViewPager;

/**
 * MainFragment
 *
 * @author 贾博瑄
 */

public class MainFragment extends BaseFragment {

    private static final int[] TAB_ICON = new int[]{R.drawable.main_ic_home, R.drawable.main_ic_loan, R.drawable.main_ic_my};

    private static final int[] TAB_TEXT = new int[]{R.string.home, R.string.loan, R.string.my};

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private NoScrollViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_fragment, container, false);
        mViewPager = (NoScrollViewPager) root.findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return homeView();
                    case 1:
                        return loanView();
                    case 2:
                        return myView();
                    default:
                        return homeView();
                }
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        });
        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.tab_layout);
        setTabs(tabLayout, inflater, TAB_ICON, TAB_TEXT);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {
                    String name = "android:switcher:" + mViewPager.getId() + ":" + "1";
                    LoanFragment fragment = (LoanFragment) getActivity().getSupportFragmentManager().findFragmentByTag(name);
                    fragment.requestProduct();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return root;
    }

    public void setLoanView() {
        String name = "android:switcher:" + mViewPager.getId() + ":" + "1";
        LoanFragment fragment = (LoanFragment) getActivity().getSupportFragmentManager().findFragmentByTag(name);
        fragment.queryProduct();
        mViewPager.setCurrentItem(1, false);
    }

    private Fragment homeView() {
        HomeFragment fragment = HomeFragment.newInstance();
        new HomePresenter(fragment, new HomeRepository());
        return fragment;
    }

    private Fragment loanView() {
        LoanFragment fragment = LoanFragment.newInstance();
        new LoanPresenter(fragment, new LoanRepository());
        return fragment;
    }

    private Fragment myView() {
        MyFragment fragment = MyFragment.newInstance();
        new MyPresenter(fragment, new MyRepository());
        return fragment;
    }

    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, int[] icon, int[] text) {
        for (int i = 0; i < icon.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.main_tab, null);
            tab.setCustomView(view);
            TextView tvTitle = (TextView) view.findViewById(R.id.text);
            tvTitle.setText(text[i]);
            ImageView imgTab = (ImageView) view.findViewById(R.id.img);
            imgTab.setImageResource(icon[i]);
            tabLayout.addTab(tab);
        }
    }
}
