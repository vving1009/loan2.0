package com.jiaye.cashloan.view.view.my.credit;

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
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.data.my.credit.CreditPasswordRequest;

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
                CreditPasswordRequest request = getIntent().getExtras().getParcelable("password");
                request.setName(URLEncoder.encode(request.getName()));
                request.setSign(URLEncoder.encode(request.getSign()));
                String result = json2KeyValue(request.toString());
                mWebView.postUrl(BuildConfig.CREDIT2GO_URL + "escrow/p2p/page/passwordset", result.getBytes());
                break;
            case "cash":
                break;
            case "balance":
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
