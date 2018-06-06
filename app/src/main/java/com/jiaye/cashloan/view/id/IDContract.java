package com.jiaye.cashloan.view.id;

import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

/**
 * IDContract
 *
 * @author 贾博瑄
 */

public interface IDContract {

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
