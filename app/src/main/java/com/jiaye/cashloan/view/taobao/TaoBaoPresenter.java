package com.jiaye.cashloan.view.taobao;

import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.taobao.source.TaoBaoDataSource;

/**
 * TaoBaoPresenter
 *
 * @author 贾博瑄
 */

public class TaoBaoPresenter extends BasePresenterImpl implements TaoBaoContract.Presenter {

    private final TaoBaoContract.View mView;

    private final TaoBaoDataSource mDataSource;

    public TaoBaoPresenter(TaoBaoContract.View view, TaoBaoDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }
}
