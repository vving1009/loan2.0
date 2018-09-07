package com.jiaye.cashloan.view.loancar.carprice.brand;

import com.jiaye.cashloan.http.data.car.CarBrand;
import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

import java.util.List;

/**
 * BrandContract
 *
 * @author 贾博瑄
 */

public interface BrandContract {

    interface View extends BaseViewContract {

        void setList(List<CarBrand.Body> list);
    }

    interface Presenter extends BasePresenter {

        void requestData();
    }
}
