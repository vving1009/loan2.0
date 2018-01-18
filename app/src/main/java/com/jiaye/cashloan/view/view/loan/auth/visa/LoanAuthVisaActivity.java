package com.jiaye.cashloan.view.view.loan.auth.visa;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.BaseDialog;
import com.jiaye.cashloan.view.data.loan.auth.source.visa.LoanAuthVisaRepository;

/**
 * LoanAuthVisaActivity
 *
 * @author 贾博瑄
 */

public class LoanAuthVisaActivity extends BaseActivity implements LoanAuthVisaContract.View {

    private LoanAuthVisaContract.Presenter mPresenter;

    private WebView mWebView;

    private BaseDialog mSMSDialog;

    private Button mBtnVisa;

    private EditText mEditSMS;

    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //noinspection ConstantConditions
        String type = getIntent().getExtras().getString("type", "visa");
        setContentView(R.layout.loan_auth_visa_activity);
        TextView textTitle = findViewById(R.id.text_title);
        switch (type) {
            case "visa":
                textTitle.setText(R.string.loan_auth_visa);
                break;
            case "visa_history":
                textTitle.setText(R.string.loan_auth_visa_history);
                break;
        }
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mBtnVisa = findViewById(R.id.btn_visa);
        mBtnVisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.sendSMS();
            }
        });
        mWebView = findViewById(R.id.web_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {

            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        mSMSDialog = new BaseDialog(this);
        View smsView = LayoutInflater.from(this).inflate(R.layout.sms_dialog_layout, null);
        mEditSMS = smsView.findViewById(R.id.edit_sms);
        smsView.findViewById(R.id.text_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSMSDialog.dismiss();
            }
        });
        smsView.findViewById(R.id.text_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.sign(mEditSMS.getText().toString());
            }
        });
        mSMSDialog.setContentView(smsView);
        mPresenter = new LoanAuthVisaPresenter(this, new LoanAuthVisaRepository());
        switch (type) {
            case "visa":
                mPresenter.setType("01");
                break;
            case "visa_history":
                mPresenter.setType("02");
                break;
        }
        mPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void postUrl(String url, byte[] postData) {
        mWebView.postUrl(url, postData);
    }

    @Override
    public void showSMSDialog() {
        mSMSDialog.show();
    }

    @Override
    public void dismissSMSDialog() {
        mSMSDialog.dismiss();
    }

    @Override
    public void hideBtn() {
        mBtnVisa.setVisibility(View.INVISIBLE);
    }
}
