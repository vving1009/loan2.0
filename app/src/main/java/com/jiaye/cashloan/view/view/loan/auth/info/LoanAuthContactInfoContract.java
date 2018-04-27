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

        void setFamilyName2(String text);

        void setFamily2(String text);

        void setFamilyPhone2(String text);

        void setFriendName2(String text);

        void setFriend2(String text);

        void setFriendPhone2(String text);

        String getFamilyName2();

        String getFamily2();

        String getFamilyPhone2();

        String getFriendName2();

        String getFriend2();

        String getFriendPhone2();

        void initFamily(final ArrayList<Relation> relations);

        void initFriend(final ArrayList<Relation> relations);

        void initFamily2(final ArrayList<Relation> relations);

        void initFriend2(final ArrayList<Relation> relations);

        void result();
    }

    interface Presenter extends BasePresenter {

        void submit();
    }
}
