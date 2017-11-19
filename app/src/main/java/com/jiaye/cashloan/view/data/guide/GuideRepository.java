package com.jiaye.cashloan.view.data.guide;

import com.jiaye.cashloan.LoanApplication;

/**
 * GuideRepository
 *
 * @author 贾博瑄
 */

public class GuideRepository implements GuideDataSource {

    @Override
    public void setNotNeedGuide() {
        LoanApplication.getInstance().getPreferencesHelper().setNeedGuide(false);
    }
}
