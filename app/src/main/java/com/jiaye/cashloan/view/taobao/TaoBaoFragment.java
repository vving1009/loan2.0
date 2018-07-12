package com.jiaye.cashloan.view.taobao;

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
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.taobao.source.TaoBaoRepository;

/**
 * TaoBaoFragment
 *
 * @author 贾博瑄
 */

public class TaoBaoFragment extends BaseFragment implements TaoBaoContract.View {

    private static final String TAG = "TaoBaoFragment";

    private TaoBaoContract.Presenter mPresenter;

    private WebView mWebView;

    public static TaoBaoFragment newInstance() {
        Bundle args = new Bundle();
        TaoBaoFragment fragment = new TaoBaoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.taobao_fragment, container, false);
        mWebView = rootView.findViewById(R.id.web_view);
        initWebView();
        mPresenter = new TaoBaoPresenter(this, new TaoBaoRepository());
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
                if (url.startsWith("taobao:") || url.startsWith("mailto:") ||
                        url.startsWith("geo:") || url.startsWith("tel:") || url.startsWith("sms:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                if (url.contains("loan//callback")) {
                    Uri uri = Uri.parse(url);
                    if (uri.getQueryParameter("mxcode").equals("-1") &&
                            uri.getQueryParameter("loginDone").equals("0")) {
                        Log.d(TAG, "exit carrier verification. ");
                        getActivity().finish();
                    }
                    if (uri.getQueryParameter("mxcode").equals("1") &&
                            uri.getQueryParameter("loginDone").equals("1") &&
                            !TextUtils.isEmpty(uri.getQueryParameter("message")) &&
                            !TextUtils.isEmpty(uri.getQueryParameter("userId")) &&
                            !TextUtils.isEmpty(uri.getQueryParameter("account")) &&
                            !TextUtils.isEmpty(uri.getQueryParameter("taskId"))) {
                        // TODO: 2018/7/11 carrier verification done.
                        Log.d(TAG, "carrier verification done. ");
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
        mWebView.loadUrl(BuildConfig.MOXIE_URL + "index.html#/taobao?apiKey=" +
                BuildConfig.MOXIE_APIKEY + "&userId=" + userId + "&backUrl=loan://callback&themeColor=425FBB");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public boolean onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onBackPressed();
    }
}
