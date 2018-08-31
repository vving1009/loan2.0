package com.jiaye.cashloan.view.car.city;

import com.jiaye.cashloan.http.data.car.CarCity;
import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

import java.util.List;

/**
 * CityContract
 *
 * @author 贾博瑄
 */

public interface CityContract {

    interface View extends BaseViewContract {

        void setList(List<CarCity> list);
    }

    interface Presenter extends BasePresenter {

        void requestData(String provinceId);
    }
}
