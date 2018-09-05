package com.jiaye.cashloan.view.step2;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.step2.Step2;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.LocalException;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.step2.source.Step2DataSource;

import java.math.BigDecimal;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Step2Presenter
 *
 * @author 贾博瑄
 */

public class Step2Presenter extends BasePresenterImpl implements Step2Contract.Presenter {

    private final Step2Contract.View mView;

    private final Step2DataSource mDataSource;

    private Step2 mStep2;

    public Step2Presenter(Step2Contract.View view, Step2DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void requestStep() {
        Disposable disposable = mDataSource.requestStep()
                .filter(step -> step.getStep() == 4)
                .flatMap(step -> mDataSource.requestStep2())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(step2 -> {
                    mStep2 = step2;
                    mView.setStep2(step2);
                })
                .defaultIfEmpty(new Step2())
                .observeOn(Schedulers.io())
                .flatMap(step2 -> mDataSource.requestStep())
                .filter(step -> step.getStep() == 8)
                .flatMap(step -> mDataSource.queryCarValuation())
                .map(response -> {
                    String s = response.getValuation();
                    if (!TextUtils.isEmpty(s)) {
                        BigDecimal a = new BigDecimal(s);
                        BigDecimal b = new BigDecimal("10000");
                        return a.multiply(b).setScale(0).toString();
                    } else {
                        throw new LocalException(R.string.error_car_valuation);
                    }
                })
                .compose(new ViewTransformer<String>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(mView::showResultView, new ThrowableConsumer(mView), mView::dismissProgressDialog);
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onClickItem(int position) {
        switch (position) {
            case 0:
                if (mStep2.getCarinsuranceAuth() == 0) {
                    mView.showInsuranceView();
                }
                break;
            case 1:
                if (mStep2.getCarinsuranceAuth() == 1 && mStep2.getId() == 0) {
                    mView.showIDView();
                }
                break;
            case 2:
                if (mStep2.getCarinsuranceAuth() == 1 && mStep2.getId() == 1 && mStep2.getBioassayAuth() == 0) {
                    mView.showBioassayView();
                }
                break;
            case 3:
                if (mStep2.getCarinsuranceAuth() == 1 && mStep2.getId() == 1 && mStep2.getBioassayAuth() == 1 &&
                        mStep2.getUserInfo() == 0) {
                    mView.showInfoView();
                }
                break;
            case 4:
                if (mStep2.getCarinsuranceAuth() == 1 && mStep2.getId() == 1 && mStep2.getBioassayAuth() == 1 &&
                        mStep2.getUserInfo() == 1 && mStep2.getOperatorAuth() == 0) {
                    mView.showPhoneView();
                }
                break;
            case 5:
                if (mStep2.getCarinsuranceAuth() == 1 && mStep2.getId() == 1 && mStep2.getBioassayAuth() == 1 &&
                        mStep2.getUserInfo() == 1 && mStep2.getOperatorAuth() == 1 && mStep2.getTaobaoAuth() == 0) {
                    mView.showTaobaoView();
                }
                break;
            case 6:
                if (mStep2.getCarinsuranceAuth() == 1 && mStep2.getId() == 1 && mStep2.getBioassayAuth() == 1 &&
                        mStep2.getUserInfo() == 1 && mStep2.getOperatorAuth() == 1 && mStep2.getTaobaoAuth() == 1 && mStep2.getCarPapers() == 0) {
                    mView.showVehicleView();
                }
                break;
        }
    }

    @Override
    public void onClickNext() {
        if (mStep2.getCarinsuranceAuth() == 1 && mStep2.getId() == 1 && mStep2.getBioassayAuth() == 1 && mStep2.getUserInfo() == 1
                && mStep2.getOperatorAuth() == 1 && mStep2.getTaobaoAuth() == 1 && mStep2.getCarPapers() == 1) {
            Disposable disposable = mDataSource.requestUpdateStep(8, "提交信息完成")
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
        } else {
            mView.showToastById(R.string.step2_error);
        }
    }

    @Override
    public void requestUpdateStep() {
        Disposable disposable = mDataSource.requestUpdateStep(5, "提交信息")
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
