package com.jiaye.cashloan.view.sign;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFunctionFragment;
import com.jiaye.cashloan.view.sign.source.SignRepository;
import com.jiaye.cashloan.widget.BaseDialog;

/**
 * SignFragment
 *
 * @author 贾博瑄
 */
public class SignFragment extends BaseFunctionFragment implements SignContract.View {

    private SignContract.Presenter mPresenter;

    private WebView mWebView;

    private BaseDialog mSMSDialog;

    private Button mBtnVisa;

    private EditText mEditSMS;

    public static SignFragment newInstance() {
        Bundle args = new Bundle();
        SignFragment fragment = new SignFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getTitleId() {
        return R.string.sign_title;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View root = inflater.inflate(R.layout.sign_fragment, frameLayout, true);
        mBtnVisa = root.findViewById(R.id.btn_visa);
        mBtnVisa.setOnClickListener(v -> mPresenter.sendSMS());
        mWebView = root.findViewById(R.id.web_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        mWebView.getSettings().setJavaScriptEnabled(true);
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
        mSMSDialog = new BaseDialog(getActivity());
        View smsView = LayoutInflater.from(getActivity()).inflate(R.layout.sms_dialog_layout, null);
        mEditSMS = smsView.findViewById(R.id.edit_sms);
        smsView.findViewById(R.id.text_cancel).setOnClickListener(v -> mSMSDialog.dismiss());
        smsView.findViewById(R.id.text_confirm).setOnClickListener(v -> mPresenter.sign(mEditSMS.getText().toString()));
        mSMSDialog.setContentView(smsView);
        mPresenter = new SignPresenter(this, new SignRepository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void postUrl(String url, byte[] postData) {
        mWebView.postUrl(url, postData);
    }

    @Override
    public void showSMSDialog() {
        mSMSDialog.show();
    }

    @Override
    public void dismissSMSDialog() {
        mSMSDialog.dismiss();
    }

    @Override
    public void hideBtn() {
        mBtnVisa.setVisibility(View.INVISIBLE);
    }
}
