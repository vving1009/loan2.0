package com.jiaye.cashloan.view.car.year;

import com.jiaye.cashloan.http.data.car.CarSeries;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.car.year.source.YearDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * YearPresenter
 *
 * @author 贾博�?
 */

public class YearPresenter extends BasePresenterImpl implements YearContract.Presenter {

    private final YearContract.View mView;

    private final YearDataSource mDataSource;

    public YearPresenter(YearContract.View view, YearDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void requestData(String modelId) {
        Disposable disposable = mDataSource.getYearList(modelId)
                .compose(new ViewTransformer<List<String>>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(list -> {
                    mView.dismissProgressDialog();
                    mView.setList(list);
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
