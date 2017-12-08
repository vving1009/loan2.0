package com.jiaye.cashloan.view.view.my.certificate.info;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * InfoContactContract
 *
 * @author 贾博瑄
 */

public class InfoContactContract {

    interface View extends BaseViewContract {

        void setFamilyName(String text);

        void setFamily(String text);

        void setFamilyPhone(String text);

        void setFriendName(String text);

        void setFriend(String text);

        void setFriendPhone(String text);
    }

    interface Presenter extends BasePresenter {
    }
}
