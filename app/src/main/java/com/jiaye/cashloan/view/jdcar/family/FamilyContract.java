package com.jiaye.cashloan.view.jdcar.family;

import com.jiaye.cashloan.http.data.jdcar.JdCarFamily;
import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

import java.util.List;

/**
 * FamilyContract
 *
 * @author 贾博�?
 */

public interface FamilyContract {

    interface View extends BaseViewContract {

        void setList(List<JdCarFamily> list);
    }

    interface Presenter extends BasePresenter {
    }
}
