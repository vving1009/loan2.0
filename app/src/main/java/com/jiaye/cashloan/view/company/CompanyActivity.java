package com.jiaye.cashloan.view.company;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.search.SearchFragment;

public class CompanyActivity extends BaseActivity {

    private CompanyFragment mCompanyFragment;
    private SearchFragment mSearchFragment;
    private FragmentManager mFragmentManager;

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

    public static void startActivityForResult(Context context, String city) {
        Intent intent = new Intent(context, CompanyActivity.class);
        intent.putExtra("city", city);
        context.startActivity(intent);
    }

    public void showCompanyView() {
        mFragmentManager.beginTransaction().replace(R.id.layout_content, mCompanyFragment).commit();
    }

    public void showSearchView() {
        mFragmentManager.beginTransaction().replace(R.id.layout_content, mSearchFragment).commit();
    }
}
