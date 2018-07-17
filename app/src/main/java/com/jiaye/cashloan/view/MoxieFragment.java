package com.jiaye.cashloan.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;

public abstract class MoxieFragment extends BaseFragment {

    private static final String TAG = "MoxieFragment";

    private WebView mWebView;

    private BasePresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.moxie_fragment, container, false);
        mWebView = rootView.findViewById(R.id.web_view);
        initWebView();
        mPresenter = getPresenter();
        mPresenter.subscribe();
        return rootView;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG, "shouldOverrideUrlLoading: " + url);
                //调用拨号、短信等程序
                if (url.startsWith("mailto:") || url.startsWith("geo:") ||
                        url.startsWith("tel:") || url.startsWith("sms:") || url.startsWith("taobao:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                if (url.contains("loan//callback")) {
                    Uri uri = Uri.parse(url);
                    if (uri.getQueryParameter("mxcode").equals("-1") &&
                            uri.getQueryParameter("loginDone").equals("0")) {
                        Log.d(TAG, "exit moxie verification. ");
                        getActivity().finish();
                    }
                    if (uri.getQueryParameter("mxcode").equals("1") &&
                            uri.getQueryParameter("loginDone").equals("1") &&
                            !TextUtils.isEmpty(uri.getQueryParameter("message")) &&
                            !TextUtils.isEmpty(uri.getQueryParameter("userId")) &&
                            !TextUtils.isEmpty(uri.getQueryParameter("account")) &&
                            !TextUtils.isEmpty(uri.getQueryParameter("taskId"))) {
                        // TODO: 2018/7/11 carrier verification done.
                        Log.d(TAG, "moxie verification done. ");
                        Log.d(TAG, "message=" + uri.getQueryParameter("message"));
                        Log.d(TAG, "userId=" + uri.getQueryParameter("userId"));
                        Log.d(TAG, "account=" + uri.getQueryParameter("account"));
                        Log.d(TAG, "taskId=" + uri.getQueryParameter("taskId"));
                        getActivity().finish();
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        String userId = LoanApplication.getInstance().getDbHelper().queryUser().getToken();
        String url = BuildConfig.MOXIE_URL + "index.html#/" + getMoxieType() + "?apiKey=" +
                BuildConfig.MOXIE_APIKEY + "&userId=" + userId + "&backUrl=loan://callback&themeColor=425FBB" + getMoxieParams();
        Log.d(TAG, "url= " + url);
        mWebView.loadUrl(url);
    }

    @Override
    public boolean onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            ViewGroup parent = (ViewGroup) mWebView.getParent();
            if(parent!=null){
                parent.removeView(mWebView);
            }
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
        mPresenter.unsubscribe();
    }

    protected abstract String getMoxieType();

    protected abstract String getMoxieParams();

    protected abstract BasePresenter getPresenter();
}
