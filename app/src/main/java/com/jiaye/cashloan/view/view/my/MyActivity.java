package com.jiaye.cashloan.view.view.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.view.my.about.AboutFragment;
import com.jiaye.cashloan.view.view.my.certificate.bank.BankFragment;
import com.jiaye.cashloan.view.view.my.certificate.CertificateFragment;
import com.jiaye.cashloan.view.view.my.certificate.idcard.IdCardFragment;
import com.jiaye.cashloan.view.view.my.certificate.info.InfoFragment;
import com.jiaye.cashloan.view.view.my.certificate.operator.OperatorFragment;
import com.jiaye.cashloan.view.view.my.certificate.taobao.TaoBaoFragment;
import com.jiaye.cashloan.view.view.my.settings.SettingsFragment;

/**
 * MyActivity
 *
 * @author 贾博瑄
 */

public class MyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity);
        TextView textView = findViewById(R.id.text_title);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
            case "certificate_taobao":
                textView.setText(R.string.my_certificate_taobao);
                TaoBaoFragment taoBaoFragment = TaoBaoFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, taoBaoFragment).commit();
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
        }
    }
}
