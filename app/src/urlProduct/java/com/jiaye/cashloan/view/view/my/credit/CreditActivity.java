package com.jiaye.cashloan.view.view.my.credit;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.my.CreditCashRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordResetRequest;
import com.jiaye.cashloan.view.BaseActivity;

import java.net.URLEncoder;

/**
 * CreditActivity
 *
 * @author 贾博瑄
 */

public class CreditActivity extends BaseActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_activity);
        WebView webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

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
        switch (getIntent().getExtras().getString("type")) {
            case "password":
                CreditPasswordRequest passwordRequest = getIntent().getExtras().getParcelable("request");
                passwordRequest.setName(URLEncoder.encode(passwordRequest.getName()));
                passwordRequest.setSign(URLEncoder.encode(passwordRequest.getSign()));
                passwordRequest.setNotifyUrl(URLEncoder.encode(passwordRequest.getNotifyUrl()));
                webView.postUrl(BuildConfig.CREDIT2GO_URL + "escrowll/p2p/page/passwordset", json2KeyValue(passwordRequest.toString()).getBytes());
                break;
            case "passwordReset":
                CreditPasswordResetRequest passwordResetRequest = getIntent().getExtras().getParcelable("request");
                passwordResetRequest.setName(URLEncoder.encode(passwordResetRequest.getName()));
                passwordResetRequest.setSign(URLEncoder.encode(passwordResetRequest.getSign()));
                passwordResetRequest.setNotifyUrl(URLEncoder.encode(passwordResetRequest.getNotifyUrl()));
                webView.postUrl(BuildConfig.CREDIT2GO_URL + "escrowll/p2p/page/mobile", json2KeyValue(passwordResetRequest.toString()).getBytes());
                break;
            case "cash":
                CreditCashRequest creditCashRequest = getIntent().getExtras().getParcelable("request");
                creditCashRequest.setName(URLEncoder.encode(creditCashRequest.getName()));
                creditCashRequest.setSign(URLEncoder.encode(creditCashRequest.getSign()));
                creditCashRequest.setNotifyUrl(URLEncoder.encode(creditCashRequest.getNotifyUrl()));
                webView.postUrl(BuildConfig.CREDIT2GO_URL + "escrowll/p2p/page/withdraw", json2KeyValue(creditCashRequest.toString()).getBytes());
                break;
        }
    }

    private String json2KeyValue(String string) {
        String result = string.replaceAll("\\{", "");
        result = result.replaceAll("\\}", "");
        result = result.replaceAll("\"", "");
        result = result.replaceAll(":", "=");
        result = result.replaceAll(",", "&");
        return result;
    }
}
