package com.jiaye.cashloan.view.view.loan;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.LoanJavascriptInterface;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.data.loan.source.LoanContractRepository;

/**
 * LoanContractActivity
 *
 * @author 贾博瑄
 */

public class LoanContractActivity extends BaseActivity implements LoanContractContract.View {

    private LoanContractContract.Presenter mPresenter;

    private WebView mWebView;

    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String contractId = getIntent().getExtras().getString("contractId");
        setContentView(R.layout.loan_contract_activity);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        mPresenter = new LoanContractPresenter(this, new LoanContractRepository());
        mPresenter.subscribe();
        mPresenter.setContractId(contractId);
        mPresenter.showContract();
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
    public void result() {
        finish();
    }
}
