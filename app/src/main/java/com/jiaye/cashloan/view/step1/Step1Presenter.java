package com.jiaye.cashloan.view.step1;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.car.CarPrice;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.step1.source.Step1DataSource;
import com.jiaye.cashloan.view.step1.source.Step1InputState;

import io.reactivex.disposables.Disposable;

/**
 * Step1Presenter
 *
 * @author 贾博瑄
 */

public class Step1Presenter extends BasePresenterImpl implements Step1Contract.Presenter {

    private final Step1Contract.View mView;

    private final Step1DataSource mDataSource;

    private Step1InputState mStep1;

    public Step1Presenter(Step1Contract.View view, Step1DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
        mStep1 = new Step1InputState();
    }

    @Override
    public void subscribe() {
        super.subscribe();
    }

    @Override
    public Step1InputState getStep1() {
        return mStep1;
    }

    @Override
    public void onClickItem(int position) {
        switch (position) {
            case 0:
                mView.showCarBrandView();
                break;
            case 1:
                if (!TextUtils.isEmpty(mStep1.getCarId())) {
                    mView.showCarDateView();
                } else {
                    mView.showToast("请先选择车型");
                }
                break;
            case 2:
                mView.showCarMilesView();
                break;
            case 3:
                mView.showCarLocationView();
                break;
        }
    }

    @Override
    public void onClickNext() {
        if (!mStep1.isFinishItem0()) {
            mView.showToastById(R.string.step1_error_brand);
        } else if (!mStep1.isFinishItem1()) {
            mView.showToastById(R.string.step1_error_date);
        } else if (!mStep1.isFinishItem2()) {
            mView.showToastById(R.string.step1_error_miles);
        } else if (!mStep1.isFinishItem3()) {
            mView.showToastById(R.string.step1_error_city);
        } else {
            Disposable disposable = mDataSource.requestCarPrice(mStep1)
                    .compose(new ViewTransformer<CarPrice>() {
                        @Override
                        public void accept() {
                            super.accept();
                            mView.showProgressDialog();
                        }
                    })
                    .subscribe(price -> {
                        mView.dismissProgressDialog();
                        String min = String.valueOf(price.getEstPriceResult().get(1));
                        String max = String.valueOf(price.getEstPriceResult().get(2));
                        mStep1.setCarMaxPrice(max);
                        mView.showResultView(min, max);
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        }
    }

    @Override
    public void saveCarPrice() {
        Disposable disposable = mDataSource.saveCarPrice(mStep1.getCarMaxPrice())
                .flatMap(response -> mDataSource.requestUpdateStep())
                .compose(new ViewTransformer<EmptyResponse>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(emptyResponse -> {
                    mView.dismissProgressDialog();
                    mView.sendBroadcast();
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
