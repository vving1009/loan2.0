package com.jiaye.cashloan.view.data.my.settings.source;

import com.jiaye.cashloan.LoanApplication;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * SettingsRepository
 *
 * @author 贾博瑄
 */

public class SettingsRepository implements SettingsDataSource {

    @Override
    public Flowable<Boolean> exit() {
        return Flowable.just(true)
                .map(new Function<Boolean, Boolean>() {
                    @Override
                    public Boolean apply(Boolean delete) throws Exception {
                        LoanApplication.getInstance().getSQLiteDatabase().delete("user", null, null);
                        return delete;
                    }
                });
    }
}
