package com.jiaye.cashloan.view.view.shop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.jiaye.cashloan.R;

public class ShopActivity extends AppCompatActivity {

    private static final String URL = "http://192.168.0.111:8080/b2c/Default.html";

    private BridgeWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_activity);
        mWebView = findViewById(R.id.web_view);
        mWebView.setDefaultHandler(new DefaultHandler());
        mWebView.registerHandler("getUserInfo", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Response<UserInfo> response = new Response<>();
                UserInfo userInfo = new UserInfo("13752126558");
                response.setBody(userInfo);
                function.onCallBack(createCallBack(response));
            }
        });
        mWebView.registerHandler("isLogin", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Response<IsLogin> response = new Response<>();
                IsLogin isLogin = new IsLogin("1");
                response.setBody(isLogin);
                function.onCallBack(createCallBack(response));
            }
        });
        mWebView.registerHandler("login", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Toast.makeText(ShopActivity.this, "跳转到登录页面", Toast.LENGTH_SHORT).show();
            }
        });
        mWebView.registerHandler("backHome", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Toast.makeText(ShopActivity.this, "回到首页", Toast.LENGTH_SHORT).show();
            }
        });
        mWebView.registerHandler("hiddenTabbar", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Toast.makeText(ShopActivity.this, "hiddenTabbar", Toast.LENGTH_SHORT).show();
            }
        });
        mWebView.setWebViewClient(new BridgeWebViewClient(mWebView) {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return alipays(url) || super.shouldOverrideUrlLoading(view, url);
            }
        });
        mWebView.loadUrl(URL);
    }

    private boolean alipays(String url) {
        if (url.startsWith("alipays:") || url.startsWith("alipay")) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
            } catch (Exception e) {
                new AlertDialog.Builder(ShopActivity.this)
                        .setMessage("未检测到支付宝客户端，请安装后重试。")
                        .setPositiveButton("立即安装", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Uri alipayUrl = Uri.parse("https://d.alipay.com");
                                startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
                            }
                        }).setNegativeButton("取消", null).show();
            }
            return true;
        }
        return false;
    }

    private String createCallBack(Response response) {
        Gson gson = new Gson();
        return gson.toJson(response);
    }

    private class Response<T> {

        private String code = "0";

        private T body;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public T getBody() {
            return body;
        }

        public void setBody(T body) {
            this.body = body;
        }
    }

    private class UserInfo {

        private String phone;

        public UserInfo(String phone) {
            this.phone = phone;
        }
    }

    private class IsLogin {

        private String isLogin;

        public IsLogin(String isLogin) {
            this.isLogin = isLogin;
        }
    }
}
