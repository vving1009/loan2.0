package com.jiaye.cashloan.view.loancar.carprice.series;

import com.jiaye.cashloan.http.data.car.CarSeries;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.loancar.carprice.series.source.SeriesDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * SeriesPresenter
 *
 * @author 贾博瑄
 */

public class SeriesPresenter extends BasePresenterImpl implements SeriesContract.Presenter {

    private final SeriesContract.View mView;

    private final SeriesDataSource mDataSource;

    public SeriesPresenter(SeriesContract.View view, SeriesDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void requestData(String brandId) {
        Disposable disposable = mDataSource.getSeriesList(brandId)
                .compose(new ViewTransformer<CarSeries>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .map(carSeries -> {
                    List<Object> list = new ArrayList<>();
                    List<CarSeries.PinpaiListBean> pinpaiList = carSeries.getPinpaiList();
                    for (CarSeries.PinpaiListBean pinpai : pinpaiList) {
                        list.add(pinpai.getPpname());
                        list.addAll(pinpai.getXilie());
                    }
                    return list;
                    /*Collections.sort(list, (o1, o2) -> {
                        if (o1 == null || o2 == null) {
                            return -1;
                        }
                        String str1 = o1.getFactoryname();
                        String str2 = o2.getFactoryname();
                        if (str1.compareToIgnoreCase(str2) < 0) {
                            return -1;
                        } else if (str1.compareToIgnoreCase(str2) == 0) {
                            return 0;
                        }
                        return 1;
                    });*/
                })
                .subscribe(list -> {
                    mView.dismissProgressDialog();
                    mView.setList(list);
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }


}
