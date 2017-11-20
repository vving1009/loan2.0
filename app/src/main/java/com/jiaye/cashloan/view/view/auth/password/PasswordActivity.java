package com.jiaye.cashloan.view.view.auth.password;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.jiaye.cashloan.R;

/**
 * ForgetActivity
 *
 * @author 贾博瑄
 */

public class PasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_activity);
        FragmentManager fragmentManager = getSupportFragmentManager();
        ForgetPasswordFragment fragment = ForgetPasswordFragment.newInstance();
        fragmentManager.beginTransaction().replace(R.id.layout_content, fragment).commit();
    }
}
