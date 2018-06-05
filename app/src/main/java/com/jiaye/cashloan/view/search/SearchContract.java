package com.jiaye.cashloan.view.search;

import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.search.source.Salesman;

import java.util.List;

/**
 * SearchContract
 *
 * @author 贾博�?
 */

public interface SearchContract {

    interface View extends BaseViewContract {

        void setCompanyListItemSelected(String company);

        void setCompanyListNoneSelected();

        void setCompanyListDataChanged(List<String> list);

        void setPersonListDataChanged(List<Salesman> list);

        void setPersonListBlankContent();
    }

    interface Presenter extends BasePresenter {

        void queryCompany();

        void queryPeopleByCompanyList(String column, String word);

        void queryPeopleBySearchView(String newText);
    }
}
