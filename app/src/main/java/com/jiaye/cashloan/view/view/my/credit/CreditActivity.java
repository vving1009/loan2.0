package com.jiaye.cashloan.view.view.my.credit;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.base.RequestContent;
import com.jiaye.cashloan.http.base.RequestHeader;
import com.jiaye.cashloan.http.data.my.CreditCashRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordResetRequest;
import com.jiaye.cashloan.view.BaseActivity;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        WebView webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("action:log")) {
                    String message = URLDecoder.decode(url.replace("action:log:", ""));
                    showToast(message);
                    finish();
                } else if (url.contains("action:forgetPassword")) {
                    finish();
                } else {
                    view.loadUrl(url);
                }
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
                webView.postUrl(BuildConfig.CREDIT2GO_URL + BuildConfig.CREDIT2GO_ESCROW_URL + "p2p/page/passwordset", json2KeyValue(passwordRequest.toString()).getBytes());
                break;
            case "passwordReset":
                CreditPasswordResetRequest passwordResetRequest = getIntent().getExtras().getParcelable("request");
                passwordResetRequest.setName(URLEncoder.encode(passwordResetRequest.getName()));
                passwordResetRequest.setSign(URLEncoder.encode(passwordResetRequest.getSign()));
                passwordResetRequest.setNotifyUrl(URLEncoder.encode(passwordResetRequest.getNotifyUrl()));
                webView.postUrl(BuildConfig.CREDIT2GO_URL + BuildConfig.CREDIT2GO_ESCROW_URL + "p2p/page/mobile", json2KeyValue(passwordResetRequest.toString()).getBytes());
                break;
            case "cash":
                String cash = getIntent().getExtras().getString("cash");
                String bank = getIntent().getExtras().getString("bank");
                Request<CreditCashRequest> request = new Request<>();
                RequestContent<CreditCashRequest> requestContent = new RequestContent<>();
                CreditCashRequest creditCashRequest = new CreditCashRequest();
                creditCashRequest.setCash(cash);
                creditCashRequest.setBank(bank);
                requestContent.setHeader(RequestHeader.create());
                List<CreditCashRequest> list = new ArrayList<>();
                list.add(creditCashRequest);
                requestContent.setBody(list);
                request.setContent(requestContent);
                webView.postUrl(BuildConfig.BASE_URL + "withdraw", new Gson().toJson(request).getBytes());
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
