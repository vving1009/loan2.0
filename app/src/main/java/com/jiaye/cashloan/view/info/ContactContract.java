package com.jiaye.cashloan.view.info;

import android.net.Uri;

import com.jiaye.cashloan.http.data.dictionary.Relation;
import com.jiaye.cashloan.http.data.loan.SaveContact;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

import java.util.ArrayList;

import io.reactivex.Flowable;

/**
 * ContactContract
 *
 * @author 贾博瑄
 */
public interface ContactContract {

    interface View extends BaseViewContract {

        void setPhone(int requestCode, String phone);

        String get1Name();

        String get1Relation();

        String get1Phone();

        String get2Name();

        String get2Relation();

        String get2Phone();

        void init1Relation(final ArrayList<Relation> relations);

        void init2Relation(final ArrayList<Relation> relations);

        void result();
    }

    interface Presenter extends BasePresenter {

        void selectPhone(Uri uri, int requestCode);

        void upLoadContact();

        Flowable<Boolean> canSubmit();

        Flowable<SaveContact> submit();
    }
}
