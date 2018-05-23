package com.jiaye.cashloan.view.view.loan.auth.ocr;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * LoanAuthOCRContract
 *
 * @author 贾博瑄
 */

public interface LoanAuthOCRContract {

    interface View extends BaseViewContract {

        void pickFront(String path);

        void pickBack(String path);

        void setFrontDrawable(String path);

        void setBackDrawable(String path);

        void setCheckEnable();

        void showInfo(String name, String id, String date);

        void setSubmitEnable();

        String getName();

        String getIdCard();

        String getIdCardDate();

        void result();
    }

    interface Presenter extends BasePresenter {

        void pickFront();

        void pickBack();

        void savePath(String path);

        void check();

        void submit();
    }
}
