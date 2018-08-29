package com.jiaye.cashloan.view.info2;

import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

/**
 * Info2Contract
 *
 * @author 贾博瑄
 */

public interface Info2Contract {

    interface View extends BaseViewContract {

        String getMarried();

        String getEducation();

        String getIndustry();

        String getYears();

        String getJob();

        String getSalary();

        String getHouse();

        String getCreditCardNum();

        String getCreditCardLimit();

        String getDescription();

        void finish();
    }

    interface Presenter extends BasePresenter {

        void submit();
    }
}
