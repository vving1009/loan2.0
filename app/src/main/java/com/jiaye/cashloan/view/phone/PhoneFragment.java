package com.jiaye.cashloan.view.phone;

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
import com.jiaye.cashloan.view.phone.source.PhoneRepository;

/**
 * PhoneFragment
 *
 * @author 贾博瑄
 */

public class PhoneFragment extends BaseFragment implements PhoneContract.View {

    private static final String TAG = "PhoneFragment";

    private PhoneContract.Presenter mPresenter;

    private WebView mWebView;

    public static PhoneFragment newInstance() {
        Bundle args = new Bundle();
        PhoneFragment fragment = new PhoneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.phone_fragment, container, false);
        mWebView = rootView.findViewById(R.id.web_view);
        initWebView();
        mPresenter = new PhonePresenter(this, new PhoneRepository());
        mPresenter.subscribe();
        return rootView;
    }

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
                if (url.startsWith("mailto:") || url.startsWith("geo:") || url.startsWith("tel:") || url.startsWith("sms:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                if (url.contains("loan//callback")) {
                    Uri uri = Uri.parse(url);
                    if (!TextUtils.isEmpty(uri.getQueryParameter("message")) &&
                            uri.getQueryParameter("loginDone").equals("1") &&
                            !TextUtils.isEmpty(uri.getQueryParameter("userId")) &&
                            !TextUtils.isEmpty(uri.getQueryParameter("account")) &&
                            !TextUtils.isEmpty(uri.getQueryParameter("taskId"))) {
                        // TODO: 2018/7/11 carrier verification done.
                        Log.d(TAG, "carrier verification done. ");
                        Log.d(TAG, "message=" + uri.getQueryParameter("message"));
                        Log.d(TAG, "userId=" + uri.getQueryParameter("userId"));
                        Log.d(TAG, "account=" + uri.getQueryParameter("account"));
                        Log.d(TAG, "taskId=" + uri.getQueryParameter("taskId"));
                    }
                    getActivity().finish();
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        String userId = LoanApplication.getInstance().getDbHelper().queryUser().getLoanId();
        String phoneNum = LoanApplication.getInstance().getDbHelper().queryUser().getPhone();
        String userName = LoanApplication.getInstance().getDbHelper().queryUser().getName();
        userName = !TextUtils.isEmpty(userName) ? ",\"name\":\"" + userName + "\"" : "";
        Log.d(TAG, "userName: " + userName);
        String idCard = LoanApplication.getInstance().getDbHelper().queryUser().getId();
        idCard = !TextUtils.isEmpty(idCard) ? ",\"idcard\":\"" + idCard + "\"" : "";
        Log.d(TAG, "idCard: " + idCard);

        mWebView.loadUrl(BuildConfig.MOXIE_URL + "index.html#/carrier?apiKey=" +
                BuildConfig.MOXIE_APIKEY + "&userId=" + userId + "&backUrl=loan://callback&themeColor=425FBB&loginParams={\"phone\":\"" +
                phoneNum + "\"" + userName + idCard + "}");
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
