package com.jiaye.cashloan.view.loancar.startpage;

import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.loancar.startpage.source.StartpageDataSource;

/**
 * StartPagePresenter
 *
 * @author 贾博瑄
 */

public class StartPagePresenter extends BasePresenterImpl implements StartPageContract.Presenter {

    private final StartPageContract.View mView;

    private final StartpageDataSource mDataSource;

    public StartPagePresenter(StartPageContract.View view, StartpageDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }
}
