package com.jiaye.cashloan.view.data.launch;

import com.jiaye.cashloan.LoanApplication;

/**
 * LaunchRepository
 *
 * @author 贾博瑄
 */

public class LaunchRepository implements LaunchDataSource {

    @Override
    public boolean isNeedGuide() {
        return LoanApplication.getInstance().getPreferencesHelper().isNeedGuide();
    }
}
