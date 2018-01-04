package com.jiaye.cashloan.view.view.main;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.jiaye.cashloan.view.data.main.MainRepository;
import com.jiaye.cashloan.view.view.auth.AuthActivity;
import com.jiaye.cashloan.view.view.home.HomeFragment;
import com.jiaye.cashloan.view.view.loan.LoanFragment;
import com.jiaye.cashloan.view.view.market.MarketFragment;
import com.jiaye.cashloan.view.view.my.MyFragment;
import com.jiaye.cashloan.widget.NoScrollViewPager;

/**
 * MainFragment
 *
 * @author 贾博瑄
 */

public class MainFragment extends BaseFragment implements MainContract.View{

    private static final int[] TAB_ICON = new int[]{R.drawable.main_ic_home, R.drawable.main_ic_loan, R.drawable.main_ic_my};

    private static final int[] TAB_TEXT = new int[]{R.string.home, R.string.market, R.string.my};

    private NoScrollViewPager mViewPager;

    private  TabLayout tabLayout ;

    private MainPresenter presenter ;

    /*是否为选中的产品*/
    private boolean mIsSelect;

    private int tabPosition = 0 ;

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
        mViewPager = root.findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return HomeFragment.newInstance();
                    case 1:
                        return MarketFragment.newInstance();
                    case 2:
                        return MyFragment.newInstance();
                    default:
                        return null;
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
        tabLayout = root.findViewById(R.id.tab_layout);
        setTabs(tabLayout, inflater, TAB_ICON, TAB_TEXT);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                if (tab.getPosition() == 1) {
//                    String tag = "android:switcher:" + mViewPager.getId() + ":" + "1";
//                    LoanFragment fragment = (LoanFragment) getActivity().getSupportFragmentManager().findFragmentByTag(tag);
//                    if (mIsSelect) {
//                        mIsSelect = false;
//                        fragment.queryProduct();
//                    } else {
//                        fragment.requestProduct();
//                    }
//                }
//                if(tabPosition!=1){
//                    tabPosition = tab.getPosition();
//                }
//
//                if(tab.getPosition()==1){
//                    presenter.requestCheck();
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        presenter = new MainPresenter(this,new MainRepository());
        presenter.subscribe();
        return root;

    }

    /**
     * 显示借款页面
     */
    public void showLoanView() {
        mIsSelect = true;
        mViewPager.setCurrentItem(1, false);
    }

    /**
     * 隐藏选项栏
     */
    public void hintTabLayout(){
        tabLayout.setVisibility(View.GONE);
    }

    /**
     * 显示选项栏
     */
    public void showTabLayout(){
        tabLayout.setVisibility(View.VISIBLE);
    }

    /**
     *显示首页页面
     */
    public void showHomeView(){
        mViewPager.setCurrentItem(0, false);
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



    @Override
    public void startAuthView() {
//        Intent intent = new Intent(getActivity(), AuthActivity.class);
//        startActivity(intent);
//        mViewPager.setCurrentItem(tabPosition, false);
    }

    @Override
    public void isLogin() {
//        tabPosition=1 ;
    }
}
