package com.jiaye.cashloan.view;

/**
 * BaseView
 *
 * @author 贾博瑄
 */

public interface BaseView<T> extends BaseViewContract {

    void setPresenter(T presenter);
}
