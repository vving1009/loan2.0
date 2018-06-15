package com.jiaye.cashloan.view.company;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseActivity;

public class CompanyActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_auth_activity);

        String city = getIntent().getStringExtra("city");
        FragmentManager fragmentManager = getSupportFragmentManager();
        CompanyFragment fragment = CompanyFragment.newInstance(city);
        fragmentManager.beginTransaction().replace(R.id.layout_content, fragment).commit();
    }

    public static void startActivity(Context context, String city) {
        Intent intent = new Intent(context, CompanyActivity.class);
        intent.putExtra("city", city);
        context.startActivity(intent);
    }
}
