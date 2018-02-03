package com.jiaye.cashloan.view.view.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.my.CreditBalance;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.view.my.about.AboutFragment;
import com.jiaye.cashloan.view.view.my.certificate.CertificateFragment;
import com.jiaye.cashloan.view.view.my.certificate.bank.BankFragment;
import com.jiaye.cashloan.view.view.my.certificate.idcard.IdCardFragment;
import com.jiaye.cashloan.view.view.my.certificate.info.InfoFragment;
import com.jiaye.cashloan.view.view.my.certificate.operator.OperatorFragment;
import com.jiaye.cashloan.view.view.my.credit.CreditFragment;
import com.jiaye.cashloan.view.view.my.credit.cash.CreditCashFragment;
import com.jiaye.cashloan.view.view.my.settings.SettingsFragment;
import com.jiaye.cashloan.view.view.my.settings.gesture.GestureFragment;

/**
 * MyActivity
 *
 * @author 贾博瑄
 */

public class MyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity);
        TextView textView = findViewById(R.id.text_title);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        String view = intent.getStringExtra("view");
        switch (view) {
            case "certificate":
                textView.setText(R.string.my_certificate);
                CertificateFragment certificateFragment = CertificateFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, certificateFragment).commit();
                break;
            case "certificate_bank":
                textView.setText(R.string.my_certificate_bank);
                BankFragment bankFragment = BankFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, bankFragment).commit();
                break;
            case "certificate_id_card":
                textView.setText(R.string.my_certificate_id_card);
                IdCardFragment idCardFragment = IdCardFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, idCardFragment).commit();
                break;
            case "certificate_info":
                textView.setText(R.string.my_certificate_info);
                InfoFragment infoFragment = InfoFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, infoFragment).commit();
                break;
            case "certificate_operator":
                textView.setText(R.string.my_certificate_operator);
                OperatorFragment operatorFragment = OperatorFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, operatorFragment).commit();
                break;
            case "about":
                textView.setText(R.string.my_about);
                AboutFragment aboutFragment = AboutFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, aboutFragment).commit();
                break;
            case "settings":
                textView.setText(R.string.my_settings);
                SettingsFragment settingsFragment = SettingsFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, settingsFragment).commit();
                break;
            case "gesture":
                textView.setText(R.string.my_settings_gesture);
                GestureFragment gestureFragment = GestureFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, gestureFragment).commit();
                break;
            case "credit":
                textView.setText(R.string.my_credit);
                CreditFragment creditFragment = CreditFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, creditFragment).commit();
                break;
            case "credit_cash":
                textView.setText(R.string.my_credit_cash);
                CreditCashFragment creditCashFragment = CreditCashFragment.newInstance((CreditBalance) getIntent().getParcelableExtra("balance"));
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, creditCashFragment).commit();
                break;
        }
    }
}
