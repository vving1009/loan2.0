package com.jiaye.cashloan.view.car.model;

import com.jiaye.cashloan.http.data.car.CarModel;
import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

import java.util.List;

/**
 * ModelContract
 *
 * @author 贾博瑄
 */

public interface ModelContract {

    interface View extends BaseViewContract {

        void setList(List<Object> list);
    }

    interface Presenter extends BasePresenter {

        void requestData(String familyId);
    }
}
