package com.jiaye.cashloan.view.info;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.http.data.dictionary.Area;
import com.jiaye.cashloan.http.data.dictionary.Education;
import com.jiaye.cashloan.http.data.dictionary.Marriage;

import java.util.ArrayList;

/**
 * PersonalContract
 *
 * @author 贾博瑄
 */

public interface PersonalContract {

    interface View extends BaseViewContract {

        void setEducation(String education);

        void setMarriage(String marriage);

        void setRegisterCity(String registerCity);

        void setCity(String city);

        void setAddress(String address);

        void setEmail(String email);

        String getEducation();

        String getMarriage();

        String getRegisterCity();

        String getCity();

        String getAddress();

        String getEmail();

        void initArea(ArrayList<Area> areas, final ArrayList<ArrayList<String>> areas2, final ArrayList<ArrayList<ArrayList<String>>> areas3);

        void initEducation(final ArrayList<Education> educations);

        void initMarriage(final ArrayList<Marriage> marriages);

        void result();
    }

    interface Presenter extends BasePresenter {

        void submit();
    }
}
