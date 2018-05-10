package com.jiaye.cashloan;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;

import com.jiaye.cashloan.config.FileConfig;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.persistence.DbHelper;
import com.jiaye.cashloan.persistence.PreferencesHelper;
import com.jiaye.cashloan.service.LocationService;
import com.jiaye.cashloan.utils.FileUtils;
import com.jiaye.cashloan.view.view.main.MainActivity;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.syd.oden.gesturelock.view.GesturePreference;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;
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
                    setupBuglyUserId(null);
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
                    Intent intent = new Intent(LoanApplication.this, LocationService.class);
                    stopService(intent);
                }
            }
        });
        setupBugly();
        setupLogger();
        setupWeChat();
        setupDict();
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
        setupBuglyUserId("unknown");
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
        new GesturePreference(LoanApplication.getInstance(), -1).WriteStringPreference("null");
        getSQLiteDatabase().delete("user", null, null);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("type", "reLogin");
        startActivity(intent);
    }

    /**
     * 设置Bugly用户
     *
     * @param phone 手机号
     */
    public void setupBuglyUserId(String phone) {
        String userId = "unknown";
        if (!TextUtils.isEmpty(phone)) {
            userId = phone;
        } else {
            Cursor cursor = mDatabase.rawQuery("SELECT phone FROM user;", null);
            if (cursor != null) {
                if (cursor.moveToNext()) {
                    userId = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
                }
                cursor.close();
            }
        }
        CrashReport.setUserId(userId);
    }

    /*setup Bugly*/
    private void setupBugly() {
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppChannel(BuildConfig.FLAVOR);
        // 测试环境记录详细bugly信息
        boolean buglyDebug = BuildConfig.FLAVOR.equals("urlTest");
        CrashReport.initCrashReport(getApplicationContext(), BuildConfig.BUGLY_APPID, buglyDebug, strategy);
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

    /*setup Dict*/
    private void setupDict() {
        // 判断指定目录下是否有视频文件
        // 如果没有 将assets下的视频文件保存到指定目录
        File file1 = new File(FileConfig.AREA_PATH);
        if (!file1.exists()) {
            FileUtils.copyFiles(this, "area.json", file1.getPath());
        }
        File file2 = new File(FileConfig.EDUCATION_PATH);
        if (!file2.exists()) {
            FileUtils.copyFiles(this, "education.json", file2.getPath());
        }
        File file3 = new File(FileConfig.MARRIAGE_PATH);
        if (!file3.exists()) {
            FileUtils.copyFiles(this, "marriage.json", file3.getPath());
        }
        File file4 = new File(FileConfig.RELATION_PATH);
        if (!file4.exists()) {
            FileUtils.copyFiles(this, "relation.json", file4.getPath());
        }
    }
}
