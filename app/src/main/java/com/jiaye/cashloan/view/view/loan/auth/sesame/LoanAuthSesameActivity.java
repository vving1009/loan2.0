package com.jiaye.cashloan.view.view.loan.auth.sesame;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.data.loan.auth.source.sesame.LoanAuthSesameRepository;

/**
 * LoanAuthSesameActivity
 *
 * @author 贾博瑄
 */

public class LoanAuthSesameActivity extends BaseActivity implements LoanAuthSesameContract.View {

    private LoanAuthSesameContract.Presenter mPresenter;

    private WebView mWebView;

    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_auth_sesame_activity);
        mWebView = findViewById(R.id.web_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new LoanJavascriptInterface(this), "loan");
        mWebView.setWebViewClient(new WebViewClient() {

            @SuppressWarnings("deprecation")
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
        mPresenter = new LoanAuthSesamePresenter(this, new LoanAuthSesameRepository());
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
}
