package com.jiaye.cashloan.view.view.auth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.view.auth.login.LoginFragment;

/**
 * AuthActivity
 *
 * @author 贾博瑄
 */

public class AuthActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);
        FragmentManager fragmentManager = getSupportFragmentManager();
        LoginFragment fragment = LoginFragment.newInstance();
        fragmentManager.beginTransaction().replace(R.id.layout_content, fragment).commit();
    }
}
