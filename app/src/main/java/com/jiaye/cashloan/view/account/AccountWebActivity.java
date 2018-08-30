package com.jiaye.cashloan.view.account;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.jiaye.cashloan.http.LoanClient;
import com.jiaye.cashloan.http.base.ChildRequest;
import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.base.RequestContent;
import com.jiaye.cashloan.http.base.RequestHeader;
import com.jiaye.cashloan.http.data.loan.AccountOpenRequest;
import com.jiaye.cashloan.http.data.loan.BindCardRequest;
import com.jiaye.cashloan.http.data.loan.TermsAuthRequest;
import com.jiaye.cashloan.http.data.loan.UnbindCardRequest;
import com.jiaye.cashloan.http.data.my.CreditCashRequest;
import com.jiaye.cashloan.http.data.my.CreditInfo;
import com.jiaye.cashloan.http.data.my.CreditInfoRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordResetRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.orhanobut.logger.Logger;

import org.reactivestreams.Publisher;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * AccountWebActivity
 *
 * @author 贾博瑄
 */

public class AccountWebActivity extends BaseActivity {

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
                Logger.d("AccountWebActivity: shouldOverrideUrlLoading: " + URLDecoder.decode(url));
                if (url.contains("action:log")) {
                    String message = URLDecoder.decode(url.replace("action:log:", ""));
                    showToast(message);
                    finish();
                } else if (url.contains("action:forgetPassword")) {
                    showPasswordResetView();
                } else if (url.contains("action:jiayidai")) {
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
        Request request;
        switch (getIntent().getExtras().getString("type")) {
            case "password":
                CreditPasswordRequest passwordRequest = getIntent().getExtras().getParcelable("request");
                passwordRequest.setName(URLEncoder.encode(passwordRequest.getName()));
                passwordRequest.setSign(URLEncoder.encode(passwordRequest.getSign()));
                passwordRequest.setRetUrl(URLEncoder.encode(passwordRequest.getRetUrl()));
                passwordRequest.setNotifyUrl(URLEncoder.encode(passwordRequest.getNotifyUrl()));
                webView.postUrl(BuildConfig.CREDIT2GO_URL + BuildConfig.CREDIT2GO_ESCROW_URL + "p2p/page/passwordset", json2KeyValue(passwordRequest.toString()).getBytes());
                break;
            case "passwordReset":
                CreditPasswordResetRequest passwordResetRequest = getIntent().getExtras().getParcelable("request");
                passwordResetRequest.setName(URLEncoder.encode(passwordResetRequest.getName()));
                passwordResetRequest.setSign(URLEncoder.encode(passwordResetRequest.getSign()));
                passwordResetRequest.setRetUrl(URLEncoder.encode(passwordResetRequest.getRetUrl()));
                passwordResetRequest.setNotifyUrl(URLEncoder.encode(passwordResetRequest.getNotifyUrl()));
                webView.postUrl(BuildConfig.CREDIT2GO_URL + BuildConfig.CREDIT2GO_ESCROW_URL + "p2p/page/mobile", json2KeyValue(passwordResetRequest.toString()).getBytes());
                break;
            case "cash":
                String cash = getIntent().getExtras().getString("cash");
                String bank = getIntent().getExtras().getString("bank");
                CreditCashRequest creditCashRequest = new CreditCashRequest();
                creditCashRequest.setCash(cash);
                creditCashRequest.setBank(bank);
                request = getRequest(creditCashRequest);
                webView.postUrl(BuildConfig.BASE_URL + "withdraw", new Gson().toJson(request).getBytes());
                break;
            case "accountOpen":
                AccountOpenRequest accountRequest = new AccountOpenRequest();
                request = getRequest(accountRequest);
                Logger.d("AccountWebActivity: accountOpen: " + BuildConfig.BASE_URL + "accountOpen" + ",\n " + new Gson().toJson(request));
                webView.postUrl(BuildConfig.BASE_URL + "accountOpen", new Gson().toJson(request).getBytes());
                break;
            case "bindCard":
                BindCardRequest bindCardRequest = new BindCardRequest();
                request = getRequest(bindCardRequest);
                Logger.d("AccountWebActivity: bindCard: " + BuildConfig.BASE_URL + "bindCard" + ",\n " + new Gson().toJson(request));
                webView.postUrl(BuildConfig.BASE_URL + "bindCard", new Gson().toJson(request).getBytes());
                break;
            case "unbind":
                UnbindCardRequest unbindRequest = new UnbindCardRequest();
                request = getRequest(unbindRequest);
                Logger.d("AccountWebActivity: unbind: " + BuildConfig.BASE_URL + "unbind" + ",\n " + new Gson().toJson(request));
                webView.postUrl(BuildConfig.BASE_URL + "unbind", new Gson().toJson(request).getBytes());
                break;
            case "termsAuth":
                TermsAuthRequest termsAuthRequest = new TermsAuthRequest();
                request = getRequest(termsAuthRequest);
                Logger.d("AccountWebActivity: termsAuth: " + BuildConfig.BASE_URL + "termsAuth" + ",\n " + new Gson().toJson(request));
                webView.postUrl(BuildConfig.BASE_URL + "termsAuth", new Gson().toJson(request).getBytes());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
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

    private <T extends ChildRequest> Request<T> getRequest(T body) {
        Request<T> request = new Request<>();
        RequestContent<T> requestContent = new RequestContent<>();
        requestContent.setHeader(RequestHeader.create());
        List<T> list = new ArrayList<>();
        list.add(body);
        requestContent.setBody(list);
        request.setContent(requestContent);
        return request;
    }

    private CreditPasswordResetRequest mPasswordResetRequest;

    private Disposable mDisposable;

    private void showPasswordResetView() {
        mDisposable = Flowable.just(new CreditInfoRequest())
                .compose(new ResponseTransformer<CreditInfoRequest, CreditInfo>("creditInfo")).map(creditInfo -> {
                    mPasswordResetRequest = new CreditPasswordResetRequest();
                    mPasswordResetRequest.setAccountId(creditInfo.getAccountId());
                    mPasswordResetRequest.setIdNo(creditInfo.getId());
                    mPasswordResetRequest.setName(creditInfo.getName());
                    mPasswordResetRequest.setMobile(creditInfo.getPhone());
                    return mPasswordResetRequest;
                }).flatMap((Function<CreditPasswordResetRequest, Publisher<Response<ResponseBody>>>) request ->
                        LoanClient.INSTANCE.getService().sign(URLEncoder.encode(request.toString())))
                .flatMap((Function<Response<ResponseBody>, Publisher<CreditPasswordResetRequest>>) sign -> {
                    mPasswordResetRequest.setSign(new String(sign.body().string().getBytes("UTF-8")));
                    return Flowable.just(mPasswordResetRequest);
                })
                .compose(new ViewTransformer<CreditPasswordResetRequest>() {
                    @Override
                    public void accept() {
                        super.accept();
                        showProgressDialog();
                    }
                })
                .subscribe(request -> {
                    dismissProgressDialog();
                    Intent intent = new Intent(AccountWebActivity.this, AccountWebActivity.class);
                    intent.putExtra("type", "passwordReset");
                    intent.putExtra("request", request);
                    startActivity(intent);
                    finish();
                }, new ThrowableConsumer(this));
    }
}
