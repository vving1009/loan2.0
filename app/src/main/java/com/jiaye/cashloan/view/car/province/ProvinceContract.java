package com.jiaye.cashloan.view.car.province;

import com.jiaye.cashloan.http.data.car.CarProvince;
import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

import java.util.List;

/**
 * ProvinceContract
 *
 * @author 贾博瑄
 */

public interface ProvinceContract {

    interface View extends BaseViewContract {

        void setList(List<CarProvince> list);
    }

    interface Presenter extends BasePresenter {

        void requestData();
    }
}
