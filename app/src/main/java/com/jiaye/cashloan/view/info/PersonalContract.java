package com.jiaye.cashloan.view.info;

import com.jiaye.cashloan.http.data.dictionary.Area;
import com.jiaye.cashloan.http.data.loan.SavePerson;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

import java.util.ArrayList;

import io.reactivex.Flowable;

/**
 * PersonalContract
 *
 * @author 贾博瑄
 */

public interface PersonalContract {

    interface View extends BaseViewContract {

        String getRegisterCity();

        String getCity();

        String getAddress();

        String getEmail();

        void initArea(ArrayList<Area> areas, final ArrayList<ArrayList<String>> areas2, final ArrayList<ArrayList<ArrayList<String>>> areas3);

        void result();
    }

    interface Presenter extends BasePresenter {

        Flowable<Boolean> canSubmit();

        Flowable<SavePerson> submit();
    }
}
