package com.jiaye.loan.cashloan;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * LoanApplication
 *
 * @author 贾博瑄
 */

public class LoanApplication extends Application {

    private static final String TAG = "LOAN";

    private static LoanApplication mInstance;

    public static LoanApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        setupLogger();
    }

    /*setup Logger*/
    private void setupLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder().tag(TAG).build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }
}
