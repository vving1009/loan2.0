package com.jiaye.cashloan.view.jdcar.brand;

import com.jiaye.cashloan.http.data.jdcar.JdCarBrand;
import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

import java.util.List;

/**
 * BrandContract
 *
 * @author 贾博�?
 */

public interface BrandContract {

    interface View extends BaseViewContract {
        void setList(List<JdCarBrand> list);
    }

    interface Presenter extends BasePresenter {
    }
}
