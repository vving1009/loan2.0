package com.jiaye.cashloan.view.company;

import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.persistence.Salesman;

import java.util.List;

/**
 * CompanyContract
 *
 * @author 贾博�?
 */

public interface CompanyContract {

    interface View extends BaseViewContract {

        void setCompanyListItemSelected(String company);

        void setCompanyListNoneSelected();

        void setCompanyListDataChanged(List<String> list);

        void setPersonListDataChanged(List<Salesman> list);

        void setPersonListBlankContent();

        void showCertificationView();
    }

    interface Presenter extends BasePresenter {

        void queryPeopleByCompanyList(String column, String word);

        void queryPeopleBySearchView(String newText);

        void selectSalesman(Salesman salesman);

        void saveSalesman();
    }
}
