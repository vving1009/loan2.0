package com.jiaye.cashloan.view.view.my;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.view.my.about.AboutFragment;
import com.jiaye.cashloan.view.view.my.certificate.MyCertificateFragment;
import com.jiaye.cashloan.view.view.my.help.HelpFragment;

public class OtherActivity extends AppCompatActivity {

    private TextView mTextTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_activity);
        mTextTitle = findViewById(R.id.text_title);
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
                mTextTitle.setText(R.string.my_certificate);
                MyCertificateFragment myCertificateFragment = MyCertificateFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, myCertificateFragment).commit();
                break;
            case "help":
                mTextTitle.setText(R.string.my_help);
                HelpFragment helpFragment = HelpFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, helpFragment).commit();
                break;
            case "about":
                mTextTitle.setText(R.string.my_about);
                AboutFragment aboutFragment = AboutFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, aboutFragment).commit();
                break;
        }
    }
}
