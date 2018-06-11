package com.jiaye.cashloan.view.support;

import com.jiaye.cashloan.http.data.loan.SupportBankList;
import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

import java.util.List;

/**
 * SupportContract
 *
 * @author 贾博瑄
 */

public interface SupportContract {

    interface View extends BaseViewContract {

        void setList(List<SupportBankList.Data> list);
    }

    interface Presenter extends BasePresenter {
    }
}
