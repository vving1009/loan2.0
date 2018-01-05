package com.jiaye.cashloan;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.jiaye.cashloan.persistence.DbHelper;
import com.jiaye.cashloan.persistence.PreferencesHelper;
import com.jiaye.cashloan.view.view.auth.AuthActivity;
import com.jiaye.cashloan.view.view.main.MainActivity;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.syd.oden.gesturelock.view.GesturePreference;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * LoanApplication
 *
 * @author 贾博瑄
 */

public class LoanApplication extends Application {

    private static final String TAG = "LOAN";

    private static LoanApplication mInstance;

    private int mActivityNumber;

    private SQLiteDatabase mDatabase;

    private PreferencesHelper mPreferencesHelper;

    private IWXAPI mIWXAPI;

    private List<Activity> activityList;

    public static LoanApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        activityList = new LinkedList<>();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if (mActivityNumber == 0) {
                    DbHelper dbHelper = new DbHelper(getApplicationContext());
                    mDatabase = dbHelper.getWritableDatabase();
                    mPreferencesHelper = new PreferencesHelper(getApplicationContext());
                }
                mActivityNumber++;
                activityList.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activityList.remove(activity);
                mActivityNumber--;
                if (mActivityNumber == 0) {
                    mDatabase.close();
                }
            }
        });
        setupLogger();
        setupWeChat();
    }

    /**
     * 获取数据库实例
     *
     * @return SQLiteDatabase
     */
    public SQLiteDatabase getSQLiteDatabase() {
        return mDatabase;
    }

    /**
     * 获取偏好设置实例
     *
     * @return PreferencesHelper
     */
    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    /**
     * 获取微信实例
     *
     * @return IWXAPI
     */
    public IWXAPI getIWXAPI() {
        return mIWXAPI;
    }

    /**
     * 重新登录
     */
    public void reLogin() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
        new GesturePreference(LoanApplication.getInstance(), -1).WriteStringPreference("null");
        getSQLiteDatabase().delete("user", null, null);
        getSQLiteDatabase().delete("product", null, null);
        Intent intent1 = new Intent(this, MainActivity.class);
        startActivity(intent1);
        Intent intent2 = new Intent(this, AuthActivity.class);
        startActivity(intent2);
    }

    /*setup Logger*/
    private void setupLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder().tag(TAG).build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    /*setup WeChat*/
    private void setupWeChat() {
        mIWXAPI = WXAPIFactory.createWXAPI(this, BuildConfig.WECHAT_APPID, true);
    }
}
