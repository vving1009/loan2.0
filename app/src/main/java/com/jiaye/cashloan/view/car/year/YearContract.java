package com.jiaye.cashloan.view.car.year;

import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

import java.util.List;

/**
 * YearContract
 *
 * @author 贾博瑄
 */

public interface YearContract {

    interface View extends BaseViewContract {

        void setList(List<String> list);
    }

    interface Presenter extends BasePresenter {

        void requestData(String modelId);
    }
}
