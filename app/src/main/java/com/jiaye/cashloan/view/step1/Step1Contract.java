package com.jiaye.cashloan.view.step1;

import com.jiaye.cashloan.http.data.step1.Step1;
import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

import java.util.List;

/**
 * Step1Contract
 *
 * @author 贾博瑄
 */

public interface Step1Contract {

    interface View extends BaseViewContract {

        void setList(List<Step1> list);

        void showIDView();
    }

    interface Presenter extends BasePresenter {

        void onClickItem(int position);
    }
}
