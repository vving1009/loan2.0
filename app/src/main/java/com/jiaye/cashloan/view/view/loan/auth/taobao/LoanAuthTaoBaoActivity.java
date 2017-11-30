package com.jiaye.cashloan.view.view.loan.auth.taobao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.widget.NoScrollViewPager;

/**
 * LoanAuthTaoBaoActivity
 *
 * @author 贾博瑄
 */

public class LoanAuthTaoBaoActivity extends AppCompatActivity {

    private static final int[] TAB_TEXT = new int[]{R.string.loan_auth_taobao_normal, R.string.loan_auth_taobao_qr};

    private NoScrollViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_auth_taobao_activity);
        mViewPager = (NoScrollViewPager) findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

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
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        setTabs(tabLayout, getLayoutInflater(), TAB_TEXT);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {
                    String name = "android:switcher:" + mViewPager.getId() + ":" + "1";
                    LoanAuthTaoBaoQRFragment fragment = (LoanAuthTaoBaoQRFragment) getSupportFragmentManager().findFragmentByTag(name);
                    fragment.request();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private LoanAuthTaoBaoNormalFragment normal() {
        return LoanAuthTaoBaoNormalFragment.newInstance();
    }

    private LoanAuthTaoBaoQRFragment qr() {
        return LoanAuthTaoBaoQRFragment.newInstance();
    }

    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, int[] text) {
        for (int aText : text) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.loan_auth_taobao_tab, null);
            tab.setCustomView(view);
            TextView tvTitle = (TextView) view.findViewById(R.id.text);
            tvTitle.setText(aText);
            tabLayout.addTab(tab);
        }
    }
}
