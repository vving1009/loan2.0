package com.jiaye.cashloan.view.search;

import com.jiaye.cashloan.persistence.Salesman;
import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

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

        void showCertificationView();
    }

    interface Presenter extends BasePresenter {

        void querySalesman(String newText);

        void selectSalesman(Salesman salesman);

        void saveSalesman();
    }
}
