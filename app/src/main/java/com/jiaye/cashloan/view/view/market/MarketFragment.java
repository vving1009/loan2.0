package com.jiaye.cashloan.view.view.market;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.market.Market;
import com.jiaye.cashloan.view.BaseFragment;


/**
 * Created by guozihua on 2018/1/3.
 */

public class MarketFragment extends BaseFragment {

    private BridgeWebView mWebView ;

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
        mWebView.loadUrl("http://192.168.0.106:8080/b2c/index.html");
        registerHandler();
        return root;
    }


    private void registerHandler(){

        //隐藏选项卡
        mWebView.registerHandler("hiddenTabbar", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
//                function.onCallBack();
            }
        });

        //显示选项卡

        mWebView.registerHandler("displayTabbar", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
//                function.onCallBack();
            }
        });
        final Market market = new Market();

        mWebView.registerHandler("backHome", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Toast.makeText(getActivity(),"11111",Toast.LENGTH_SHORT).show();
                Log.e("******","html");
                function.onCallBack(new Gson().toJson(market));
            }
        });
        mWebView.registerHandler("getUserInfo", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
//                function.onCallBack();
            }
        });


        mWebView.callHandler("backHome", new Gson().toJson(market), new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Toast.makeText(getActivity(),"11111",Toast.LENGTH_SHORT).show();

            }
        });


        mWebView.registerHandler("isLogin", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
//                function.onCallBack();
            }
        });
        mWebView.registerHandler("login", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
//                function.onCallBack();
            }
        });




    }



}
