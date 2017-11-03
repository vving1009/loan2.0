package com.jiaye.loan.cashloan.view.view.auth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.jiaye.loan.cashloan.R;
import com.jiaye.loan.cashloan.view.data.auth.login.source.LoginRepository;
import com.jiaye.loan.cashloan.view.view.auth.login.LoginFragment;
import com.jiaye.loan.cashloan.view.view.auth.login.LoginPresenter;

/**
 * AuthActivity
 *
 * @author 贾博瑄
 */

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);
        FragmentManager fragmentManager = getSupportFragmentManager();
        LoginFragment fragment = LoginFragment.newInstance();
        fragmentManager.beginTransaction().replace(R.id.layout_content, fragment).commit();
        new LoginPresenter(fragment, new LoginRepository());
    }
}
