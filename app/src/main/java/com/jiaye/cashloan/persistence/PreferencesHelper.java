package com.jiaye.cashloan.persistence;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * PreferencesHelper
 *
 * @author 贾博瑄
 */

public class PreferencesHelper {

    private static final String PREF_KEY_NEED_GUIDE = "PREF_KEY_NEED_GUIDE";

    private final SharedPreferences mPrefs;

    public PreferencesHelper(Context context) {
        mPrefs = context.getSharedPreferences("loan", Context.MODE_PRIVATE);
    }

    public void setNeedGuide(boolean needGuide) {
        mPrefs.edit().putBoolean(PREF_KEY_NEED_GUIDE, needGuide).apply();
    }

    public boolean isNeedGuide() {
        return mPrefs.getBoolean(PREF_KEY_NEED_GUIDE, true);
    }
}