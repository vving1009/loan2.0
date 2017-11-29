package com.jiaye.cashloan.view.view.guide;

import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.data.guide.source.GuideDataSource;

/**
 * GuidePresenter
 *
 * @author 贾博瑄
 */

public class GuidePresenter extends BasePresenterImpl implements GuideContract.Presenter {

    private final GuideContract.View mView;

    private final GuideDataSource mDataSource;

    public GuidePresenter(GuideContract.View view, GuideDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void setNotNeedGuide() {
        mDataSource.setNotNeedGuide();
        mView.showMainView();
    }
}
