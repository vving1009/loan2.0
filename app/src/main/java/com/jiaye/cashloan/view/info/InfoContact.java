package com.jiaye.cashloan.view.info;

import com.jiaye.cashloan.http.data.loan.SaveContact;
import com.jiaye.cashloan.http.data.loan.SavePerson;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

import io.reactivex.Flowable;

/**
 * InfoContact
 *
 * @author 贾博瑄
 */
public interface InfoContact {

    interface View extends BaseViewContract {

        void finish();
    }

    interface Presenter extends BasePresenter {

        void submit(Flowable<Boolean> bPerson, Flowable<Boolean> bContact, Flowable<SavePerson> person, Flowable<SaveContact> contact);
    }
}
