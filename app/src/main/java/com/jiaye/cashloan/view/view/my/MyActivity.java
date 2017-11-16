package com.jiaye.cashloan.view.view.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.view.my.about.AboutFragment;
import com.jiaye.cashloan.view.view.my.certificate.CertificateFragment;
import com.jiaye.cashloan.view.view.my.help.HelpFragment;
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
                CertificateFragment myCertificateFragment = CertificateFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, myCertificateFragment).commit();
                break;
            case "help":
                textView.setText(R.string.my_help);
                HelpFragment helpFragment = HelpFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, helpFragment).commit();
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
