package com.jiaye.cashloan.view.car.province;

import com.jiaye.cashloan.http.data.car.CarProvince;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.car.province.source.ProvinceDataSource;

import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * ProvincePresenter
 *
 * @author 贾博瑄
 */

public class ProvincePresenter extends BasePresenterImpl implements ProvinceContract.Presenter {

    private final ProvinceContract.View mView;

    private final ProvinceDataSource mDataSource;

    public ProvincePresenter(ProvinceContract.View view, ProvinceDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void requestData() {
        Disposable disposable = mDataSource.getProvinceList()
                .compose(new ViewTransformer<List<CarProvince>>() {
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
