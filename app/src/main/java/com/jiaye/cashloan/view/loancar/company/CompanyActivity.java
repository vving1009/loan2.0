package com.jiaye.cashloan.view.loancar.company;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.loancar.search.SearchFragment;

public class CompanyActivity extends BaseActivity {

    private CompanyFragment mCompanyFragment;
    private SearchFragment mSearchFragment;
    private FragmentManager mFragmentManager;
    private BaseFragment mCurrentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        String city = getIntent().getStringExtra("city");
        mFragmentManager = getSupportFragmentManager();
        mCompanyFragment = CompanyFragment.newInstance(city);
        mSearchFragment = SearchFragment.newInstance();
        showCompanyView();
    }

    public void showCompanyView() {
        mFragmentManager.beginTransaction().replace(R.id.layout_content, mCompanyFragment).commit();
        mCurrentFragment = mCompanyFragment;
    }

    public void showSearchView() {
        mFragmentManager.beginTransaction().replace(R.id.layout_content, mSearchFragment).commit();
        mCurrentFragment = mSearchFragment;
    }

    @Override
    public void onBackPressed() {
        if (!mCurrentFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
