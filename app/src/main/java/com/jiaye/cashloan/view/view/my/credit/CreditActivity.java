package com.jiaye.cashloan.view.view.my.credit;

import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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

    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_activity);
        mWebView = findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
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
                mWebView.postUrl(BuildConfig.CREDIT2GO_URL + "escrow/p2p/page/passwordset", json2KeyValue(passwordRequest.toString()).getBytes());
                break;
            case "passwordReset":
                CreditPasswordResetRequest passwordResetRequest = getIntent().getExtras().getParcelable("request");
                passwordResetRequest.setName(URLEncoder.encode(passwordResetRequest.getName()));
                passwordResetRequest.setSign(URLEncoder.encode(passwordResetRequest.getSign()));
                mWebView.postUrl(BuildConfig.CREDIT2GO_URL + "escrow/p2p/page/mobile", json2KeyValue(passwordResetRequest.toString()).getBytes());
                break;
            case "cash":
                CreditCashRequest creditCashRequest = getIntent().getExtras().getParcelable("request");
                creditCashRequest.setName(URLEncoder.encode(creditCashRequest.getName()));
                if (TextUtils.isEmpty(creditCashRequest.getSign())) {
                    showToast("签名失败");
                    finish();
                } else {
                    creditCashRequest.setSign(URLEncoder.encode(creditCashRequest.getSign()));
                    mWebView.postUrl(BuildConfig.CREDIT2GO_URL + "escrow/p2p/page/withdraw", json2KeyValue(creditCashRequest.toString()).getBytes());
                }
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
