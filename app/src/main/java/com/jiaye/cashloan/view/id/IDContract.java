package com.jiaye.cashloan.view.id;

import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

/**
 * IDContract
 *
 * @author 贾博瑄
 */

public interface IDContract {

    String FOLDER_FILE = "idCard";

    interface View extends BaseViewContract {

        void setFrontDrawable(String path);

        void setBackDrawable(String path);

        void setCheckEnable();

        void showInfo(String name, String id, String date);

        void setSubmitEnable();

        String getName();

        String getIdCard();

        String getIdCardDate();

        void result();

        void showBottomDialog();
    }

    interface Presenter extends BasePresenter {

        void pickFront();

        void pickBack();

        void savePath(String path);

        void check();

        void submit();
    }
}
