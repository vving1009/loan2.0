package com.jiaye.cashloan.view.info;

import android.net.Uri;

import com.jiaye.cashloan.http.data.dictionary.Area;
import com.jiaye.cashloan.http.data.dictionary.Relation;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

import java.util.ArrayList;

/**
 * InfoContact
 *
 * @author 贾博瑄
 */
public interface InfoContact {

    interface View extends BaseViewContract {

        String getRegisterCity();

        String getCity();

        String getAddress();

        String getEmail();

        void initArea(ArrayList<Area> areas, final ArrayList<ArrayList<String>> areas2, final ArrayList<ArrayList<ArrayList<String>>> areas3);

        void setPhone(int requestCode, String phone);

        String get1Name();

        String get1Relation();

        String get1Phone();

        String get2Name();

        String get2Relation();

        String get2Phone();

        void init1Relation(final ArrayList<Relation> relations);

        void init2Relation(final ArrayList<Relation> relations);

        void finish();
    }

    interface Presenter extends BasePresenter {

        void selectPhone(Uri uri, int requestCode);

        void upLoadContact();

        void submit();
    }
}
