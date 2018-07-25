package com.jiaye.cashloan.view.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFunctionFragment;
import com.jiaye.cashloan.view.FunctionActivity;
import com.jiaye.cashloan.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * LoginFragment
 *
 * @author 贾博瑄
 */
public class LoginFragment extends BaseFunctionFragment {

    private static final int[] TAB_TEXT = new int[]{R.string.login_shortcut, R.string.login_normal};

    private List<View> indicators = new ArrayList<>();

    private LocalBroadcastManager mLocalBroadcastManager;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("finish".equals(action)) {
                getActivity().finish();
            }
        }
    };

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static void finish(Context context) {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
        Intent intent = new Intent("finish");
        localBroadcastManager.sendBroadcast(intent);
    }

    @Override
    protected int getTitleId() {
        return R.string.login;
    }

    @Override
    protected int getFunctionId() {
        return R.string.register;
    }

    @Override
    protected void onClickFunction() {
        FunctionActivity.function(getActivity(), "Register");
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.login_fragment, frameLayout, true);
        NoScrollViewPager viewPager = rootView.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return shortcut();
                    case 1:
                        return normal();
                    default:
                        return shortcut();
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
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
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
        IntentFilter intentFilter = new IntentFilter("finish");
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        mLocalBroadcastManager.registerReceiver(mReceiver, intentFilter);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLocalBroadcastManager.unregisterReceiver(mReceiver);
    }

    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, int[] text) {
        for (int aText : text) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.login_tab, null);
            tab.setCustomView(view);
            TextView tvTitle = view.findViewById(R.id.text);
            View indicator = view.findViewById(R.id.indicator);
            tvTitle.setText(aText);
            indicators.add(indicator);
            tabLayout.addTab(tab);
        }
    }

    private LoginShortcutFragment shortcut() {
        return LoginShortcutFragment.newInstance();
    }

    private LoginNormalFragment normal() {
        return LoginNormalFragment.newInstance();
    }
}
