package com.jiaye.cashloan.view.view.launch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.jiaye.cashloan.R;

/**
 * LaunchActivity
 *
 * @author 贾博瑄
 */

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity);
        FragmentManager fragmentManager = getSupportFragmentManager();
        LaunchFragment fragment = LaunchFragment.newInstance();
        fragmentManager.beginTransaction().replace(R.id.layout_content, fragment).commit();
    }
}
