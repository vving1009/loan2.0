package com.jiaye.cashloan.view.view.loan.auth.info;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.http.data.dictionary.Relation;

import java.util.ArrayList;

/**
 * LoanAuthContactInfoContract
 *
 * @author 贾博瑄
 */

public interface LoanAuthContactInfoContract {

    interface View extends BaseViewContract {

        void setFamilyName(String text);

        void setFamily(String text);

        void setFamilyPhone(String text);

        void setFriendName(String text);

        void setFriend(String text);

        void setFriendPhone(String text);

        String getFamilyName();

        String getFamily();

        String getFamilyPhone();

        String getFriendName();

        String getFriend();

        String getFriendPhone();

        void initFamily(final ArrayList<Relation> relations);

        void initFriend(final ArrayList<Relation> relations);

        void result();
    }

    interface Presenter extends BasePresenter {

        void submit();
    }
}
