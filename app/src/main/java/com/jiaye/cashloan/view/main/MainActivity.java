package com.jiaye.cashloan.view.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.FunctionActivity;

public class MainActivity extends BaseActivity {

    private MainFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            myFragment = MainFragment.newInstance();
        } else {
            myFragment = (MainFragment) getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
        }
        setContentView(R.layout.activity);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.layout_content, myFragment).commit();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String type = bundle.getString("type", "default");
            if (type.equals("reLogin")) {
                FunctionActivity.function(this, "Login");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (myFragment != null) {
            getSupportFragmentManager().putFragment(outState, "fragment", myFragment);
        }
    }
}
