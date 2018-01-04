package com.jiaye.cashloan.view.view.market;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.market.Market;
import com.jiaye.cashloan.http.data.market.MarketUserInfo;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.market.MarketRepository;
import com.jiaye.cashloan.view.view.main.MainFragment;


/**
 * Created by guozihua on 2018/1/3.
 */

public class MarketFragment extends BaseFragment implements MarketContract.View{

    private BridgeWebView mWebView ;
    private MarketPresenter presenter ;
    private boolean isLogin = false ;

    public static MarketFragment newInstance() {
        Bundle args = new Bundle();
        MarketFragment fragment = new MarketFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.market_fragment, container, false);
        mWebView = root.findViewById(R.id.web_view);
        mWebView.setDefaultHandler(new DefaultHandler());
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        mWebView.loadUrl("http://192.168.0.106:8080/b2c/index.html");
//        registerHandler();
        mWebView.addJavascriptInterface(new JiaoHu(),"window.WebViewJavascriptBridge");
        presenter = new MarketPresenter(this,new MarketRepository());
        presenter.subscribe();

        return root;
    }


    public class JiaoHu{
        @JavascriptInterface
        public void hiddenTabbar(){
            Toast.makeText(getActivity(),"js调用了android的方法",Toast.LENGTH_SHORT).show();
        }
    }

    private void registerHandler(){
       final Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.layout_content);

        //隐藏选项卡
        mWebView.registerHandler("hiddenTabbar", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Toast.makeText(getActivity(),"11111",Toast.LENGTH_SHORT).show();

                if (fragment != null && fragment instanceof MainFragment) {
                    ((MainFragment) fragment).hintTabLayout();
                }


//                function.onCallBack();
            }
        });

        //显示选项卡

        mWebView.registerHandler("displayTabbar", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Toast.makeText(getActivity(),"222222",Toast.LENGTH_SHORT).show();
                if (fragment != null && fragment instanceof MainFragment) {
                    ((MainFragment) fragment).showTabLayout();
                }
//                function.onCallBack();
            }
        });


        mWebView.registerHandler("backHome", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Toast.makeText(getActivity(),"22222",Toast.LENGTH_SHORT).show();

                if (fragment != null && fragment instanceof MainFragment) {
                    ((MainFragment) fragment).showHomeView();
                }
                Market<MarketUserInfo> market = new Market();
                function.onCallBack(new Gson().toJson(market));
            }
        });
        mWebView.registerHandler("getUserInfo", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Toast.makeText(getActivity(),"33333",Toast.LENGTH_SHORT).show();
//                function.onCallBack();
            }
        });


//        mWebView.callHandler("backHome", new Gson().toJson(market), new CallBackFunction() {
//            @Override
//            public void onCallBack(String data) {
//                Toast.makeText(getActivity(),"44444",Toast.LENGTH_SHORT).show();
//
//            }
//        });


        mWebView.registerHandler("isLogin", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
//                function.onCallBack();
                Toast.makeText(getActivity(),"55555",Toast.LENGTH_SHORT).show();
                presenter.checklogin();
            }
        });
        mWebView.registerHandler("login", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
//                function.onCallBack();
                Toast.makeText(getActivity(),"66666",Toast.LENGTH_SHORT).show();
            }
        });




    }


    @Override
    public void isLogin(boolean login) {
        isLogin = login ;
    }
}
