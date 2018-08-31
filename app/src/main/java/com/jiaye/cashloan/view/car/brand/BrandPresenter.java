package com.jiaye.cashloan.view.car.brand;

import com.jiaye.cashloan.http.data.car.CarBrand;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.car.brand.source.BrandDataSource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * BrandPresenter
 *
 * @author 贾博瑄
 */

public class BrandPresenter extends BasePresenterImpl implements BrandContract.Presenter {

    private final BrandContract.View mView;

    private final BrandDataSource mDataSource;

    public BrandPresenter(BrandContract.View view, BrandDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void requestData() {
        Disposable disposable = mDataSource.getBrand()
                .compose(new ViewTransformer<CarBrand>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .map(brand -> {
                    List<CarBrand.Body> list = new ArrayList<>();
                    for (char i = 'A'; i <= 'Z'; i++) {
                        Field field = brand.getClass().getDeclaredField(String.valueOf(i));
                        field.setAccessible(true);
                        List<CarBrand.Body> bodies = (List<CarBrand.Body>) field.get(brand);
                        if (bodies != null && bodies.size() > 0) {
                            list.addAll(bodies);
                        }
                    }
                    return list;
                    /*Collections.sort(list, (o1, o2) -> {
                        if (o1 == null || o2 == null) {
                            return -1;
                        }
                        String str1 = o1.getLetter();
                        String str2 = o2.getLetter();
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
