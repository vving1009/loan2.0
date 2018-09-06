package com.jiaye.cashloan.view.calculator;

import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.calculator.source.CalculatorDataSource;

/**
 * CalculatorPresenter
 *
 * @author 贾博瑄
 */

public class CalculatorPresenter extends BasePresenterImpl implements CalculatorContract.Presenter {

    private final CalculatorContract.View mView;

    private final CalculatorDataSource mDataSource;

    public CalculatorPresenter(CalculatorContract.View view, CalculatorDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }
}
