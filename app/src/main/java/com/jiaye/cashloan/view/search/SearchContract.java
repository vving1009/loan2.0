package com.jiaye.cashloan.view.search;

import com.jiaye.cashloan.persistence.Salesman;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

import java.util.List;

/**
 * SearchContract
 *
 * @author 贾博瑄
 */

public interface SearchContract {

    interface View extends BaseViewContract {

        void setListDataChanged(List<Salesman> list);

        void setListBlankContent();
    }

    interface Presenter extends BasePresenter {

        void querySalesman(String newText);
    }
}
