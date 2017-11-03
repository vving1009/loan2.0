package com.jiaye.loan.cashloan.view.view.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.jiaye.loan.cashloan.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        FragmentManager fragmentManager = getSupportFragmentManager();
        MainFragment fragment = MainFragment.newInstance();
        fragmentManager.beginTransaction().replace(R.id.layout_content, fragment).commit();
    }
}
