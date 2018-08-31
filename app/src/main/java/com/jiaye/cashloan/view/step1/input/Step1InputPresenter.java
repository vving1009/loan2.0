package com.jiaye.cashloan.view.step1.input;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.car.CarPrice;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.step1.input.source.Step1InputDataSource;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Step1InputPresenter
 *
 * @author 贾博瑄
 */

public class Step1InputPresenter extends BasePresenterImpl implements Step1InputContract.Presenter {

    private final Step1InputContract.View mView;

    private final Step1InputDataSource mDataSource;

    public Step1InputPresenter(Step1InputContract.View view, Step1InputDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
        //mStep1 = new Step1InputState();
        //mView.setStep2(mStep1);
    }

    @Override
    public void onClickItem(int position) {
        switch (position) {
            case 0:
                /*if (mStep1.getId() == 0) {
                    mView.showIDView();
                }*/
                mView.showCarBrandView();
                break;
            case 1:
                /*if (mStep1.getId() == 1 && mStep1.getBioassay() == 0) {
                    mView.showBioassayView();
                }*/
                mView.showCarDateView();
                break;
            case 2:
                /*if (mStep1.getId() == 1 && mStep1.getBioassay() == 1 && mStep1.getPersonal() == 0) {
                    mView.showInfoView();
                }*/
                mView.showCarMilesView();
                break;
            case 3:
                /*if (mStep1.getId() == 1 && mStep1.getBioassay() == 1 && mStep1.getPersonal() == 1
                        && mStep1.getPhone() == 0) {
                    mView.showPhoneView();
                }*/
                mView.showCarLocationView();
                break;
        }
    }

    @Override
    public void onClickNext(Step1InputState step1) {
        /*if (mStep1.isFinishItem0() && mStep1.isFinishItem1() && mStep1.isFinishItem2()
                && mStep1.isFinishItem3()) {
            Disposable disposable = mDataSource.requestUpdateStep()
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
            mView.showToastById(R.string.step1_error);
        }*/
        if (step1.isFinishItem0() && step1.isFinishItem1() && step1.isFinishItem2()
                && step1.isFinishItem3()) {
            Disposable disposable = mDataSource.requestCarPrice(step1)
                    .compose(new ViewTransformer<CarPrice>() {
                        @Override
                        public void accept() {
                            super.accept();
                            mView.showProgressDialog();
                        }
                    })
                    .subscribe(price -> {
                        mView.dismissProgressDialog();
                        mView.showResultView(String.valueOf(price.getEstPriceResult().get(1)),
                                String.valueOf(price.getEstPriceResult().get(2)));
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        } else {
            mView.showToastById(R.string.step1_error);
        }
    }
}
