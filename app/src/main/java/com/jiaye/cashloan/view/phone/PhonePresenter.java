package com.jiaye.cashloan.view.phone;

import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.phone.source.PhoneDataSource;

/**
 * PhonePresenter
 *
 * @author 贾博瑄
 */

public class PhonePresenter extends BasePresenterImpl implements PhoneContract.Presenter {

    private final PhoneContract.View mView;

    private final PhoneDataSource mDataSource;

    public PhonePresenter(PhoneContract.View view, PhoneDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }
}
