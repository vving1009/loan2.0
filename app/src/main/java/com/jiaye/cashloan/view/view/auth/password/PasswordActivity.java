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
        /*0 忘记密码 1 修改密码*/
        int type = getIntent().getExtras().getInt("type", 0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        ForgetPasswordFragment fragment = ForgetPasswordFragment.newInstance(type);
        fragmentManager.beginTransaction().replace(R.id.layout_content, fragment).commit();
    }
}
