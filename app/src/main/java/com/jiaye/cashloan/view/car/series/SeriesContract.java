package com.jiaye.cashloan.view.car.series;

import com.jiaye.cashloan.http.data.car.CarSeries;
import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

import java.util.List;

/**
 * SeriesContract
 *
 * @author 贾博瑄
 */

public interface SeriesContract {

    interface View extends BaseViewContract {

        void setList(List<Object> list);
    }

    interface Presenter extends BasePresenter {

        void requestData(String brandId);
    }
}
