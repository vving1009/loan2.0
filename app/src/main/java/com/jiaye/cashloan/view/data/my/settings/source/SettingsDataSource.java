package com.jiaye.cashloan.view.data.my.settings.source;

import io.reactivex.Flowable;

/**
 * SettingsDataSource
 *
 * @author 贾博瑄
 */

public interface SettingsDataSource {

    Flowable<Boolean> exit();
}
