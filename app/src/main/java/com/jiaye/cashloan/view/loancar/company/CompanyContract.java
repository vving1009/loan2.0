package com.jiaye.cashloan.view.loancar.company;

import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.persistence.Salesman;

import java.util.List;

/**
 * CompanyContract
 *
 * @author 贾博瑄
 */

public interface CompanyContract {

    interface View extends BaseViewContract {

        void setInitCity();

        void setCompanyListItemSelected(String company);

        void setCompanyListNoneSelected();

        void setCompanyListDataChanged(List<String> list);

        void setPersonListDataChanged(List<Salesman> list);

        void setPersonListBlankContent();
    }

    interface Presenter extends BasePresenter {

        void queryPeopleByCompanyList(String column, String word);

        void queryCompany(String city);
    }
}
