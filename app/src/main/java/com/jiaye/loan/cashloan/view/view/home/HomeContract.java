package com.jiaye.loan.cashloan.view.view.home;

import com.jiaye.loan.cashloan.view.BasePresenter;
import com.jiaye.loan.cashloan.view.BaseView;
import com.jiaye.loan.cashloan.view.data.home.Card;

import java.util.List;

/**
 * HomeContract
 *
 * @author 贾博瑄
 */

public class HomeContract {

    interface View extends BaseView<Presenter> {

        void setList(List<Card> list);
    }

    interface Presenter extends BasePresenter {

    }
}
