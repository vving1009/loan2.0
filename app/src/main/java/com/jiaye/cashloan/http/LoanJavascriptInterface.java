package com.jiaye.cashloan.http;

import android.app.Activity;
import android.webkit.JavascriptInterface;

/**
 * LoanJavascriptInterface
 *
 * @author 贾博瑄
 */

public class LoanJavascriptInterface {

    private Activity mActivity;

    public LoanJavascriptInterface(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * 保存登录信息
     */
    @JavascriptInterface
    public void close() {
        mActivity.finish();
    }
}
