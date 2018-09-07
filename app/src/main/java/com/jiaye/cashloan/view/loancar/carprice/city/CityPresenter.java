package com.jiaye.cashloan.view.loancar.carprice.city;

import com.jiaye.cashloan.http.data.car.CarCity;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.loancar.carprice.city.source.CityDataSource;

import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * CityPresenter
 *
 * @author 贾博瑄
 */

public class CityPresenter extends BasePresenterImpl implements CityContract.Presenter {

    private final CityContract.View mView;

    private final CityDataSource mDataSource;

    public CityPresenter(CityContract.View view, CityDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }
    
    @Override
    public void requestData(String provinceId) {
        Disposable disposable = mDataSource.getCityList(provinceId)
                .compose(new ViewTransformer<List<CarCity>>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .doOnNext(list -> {
                    Collections.sort(list, (o1, o2) -> {
                        if (o1 == null || o2 == null) {
                            return -1;
                        }
                        String str1 = o1.getPinyin();
                        String str2 = o2.getPinyin();
                        if (str1.compareToIgnoreCase(str2) < 0) {
                            return -1;
                        } else if (str1.compareToIgnoreCase(str2) == 0) {
                            return 0;
                        }
                        return 1;
                    });
                })
                .subscribe(list -> {
                    mView.dismissProgressDialog();
                    mView.setList(list);
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
