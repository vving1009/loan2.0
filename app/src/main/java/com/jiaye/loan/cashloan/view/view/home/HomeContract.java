package com.jiaye.loan.cashloan.view.view.home;

import com.jiaye.loan.cashloan.http.data.home.Product;
import com.jiaye.loan.cashloan.view.BasePresenter;
import com.jiaye.loan.cashloan.view.BaseView;

import java.util.List;

/**
 * HomeContract
 *
 * @author 贾博瑄
 */

public class HomeContract {

    interface View extends BaseView<Presenter> {

        void setList(List<Product> list);
    }

    interface Presenter extends BasePresenter {

    }
}
