package com.jiaye.cashloan.view.view.loan;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.http.LoanJavascriptInterface;
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
        boolean contract = getIntent().getExtras().getBoolean("contract");
        String loanId = getIntent().getExtras().getString("loanId");
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
        if (!contract) {
            findViewById(R.id.btn_commit).setVisibility(View.GONE);
            findViewById(R.id.text_phone).setVisibility(View.GONE);
        } else {
            TextView textPhone = findViewById(R.id.text_phone);
            SpannableString string = new SpannableString(textPhone.getText());
            string.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_blue)), 9, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textPhone.setText(string);
            findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.requestContract();
                }
            });
        }
        mPresenter = new LoanContractPresenter(this, new LoanContractRepository());
        mPresenter.subscribe();
        mPresenter.setLoanId(loanId);
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
