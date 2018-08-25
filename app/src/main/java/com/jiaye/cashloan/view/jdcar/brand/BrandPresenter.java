package com.jiaye.cashloan.view.jdcar.brand;

import com.jiaye.cashloan.http.data.jdcar.JdCarBrand;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.jdcar.brand.source.BrandDataSource;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * BrandPresenter
 *
 * @author 贾博�?
 */

public class BrandPresenter extends BasePresenterImpl implements BrandContract.Presenter {

    private final BrandContract.View mView;

    private final BrandDataSource mDataSource;

    public BrandPresenter(BrandContract.View view, BrandDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        Disposable disposable = mDataSource.getBrandList()
                .compose(new ViewTransformer<List<JdCarBrand>>() {
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
