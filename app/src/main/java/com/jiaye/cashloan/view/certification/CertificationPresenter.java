package com.jiaye.cashloan.view.certification;

import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.certification.source.CertificationDataSource;

/**
 * CertificationPresenter
 *
 * @author 贾博瑄
 */

public class CertificationPresenter extends BasePresenterImpl implements CertificationContract.Presenter {

    private final CertificationContract.View mView;

    private final CertificationDataSource mDataSource;

    public CertificationPresenter(CertificationContract.View view, CertificationDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }
}
