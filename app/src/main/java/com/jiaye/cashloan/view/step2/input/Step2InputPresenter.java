package com.jiaye.cashloan.view.step2.input;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.step2.Step2Input;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.step2.input.source.Step2InputDataSource;

import io.reactivex.disposables.Disposable;

/**
 * Step2InputPresenter
 *
 * @author 贾博瑄
 */

public class Step2InputPresenter extends BasePresenterImpl implements Step2InputContract.Presenter {

    private final Step2InputContract.View mView;

    private final Step2InputDataSource mDataSource;

    private Step2Input mStep2Input;

    public Step2InputPresenter(Step2InputContract.View view, Step2InputDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void requestStep() {
        Disposable disposable = mDataSource.requestStep2()
                .compose(new ViewTransformer<>())
                .subscribe(step1 -> {
                    // TODO: 2018/8/24
                    //mStep2Input = step1;
                    mStep2Input = new Step2Input();
                    mView.setStep1(mStep2Input);
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onClickItem(int position) {
        switch (position) {
            case 0:
                if (mStep2Input.getInsurance() == 0) {
                    mView.showInsuranceView();
                }
                break;
            case 1:
                if (mStep2Input.getInsurance() == 1 && mStep2Input.getBioassay() == 0) {
                    mView.showBioassayView();
                }
                break;
            case 2:
                if (mStep2Input.getInsurance() == 1 && mStep2Input.getBioassay() == 1 && mStep2Input.getPersonal() == 0) {
                    mView.showInfoView();
                }
                break;
            case 3:
                if (mStep2Input.getInsurance() == 1 && mStep2Input.getBioassay() == 1 && mStep2Input.getPersonal() == 1
                        && mStep2Input.getPhone() == 0) {
                    mView.showPhoneView();
                }
                break;
            case 4:
                if (mStep2Input.getInsurance() == 1 && mStep2Input.getBioassay() == 1 && mStep2Input.getPersonal() == 1
                        && mStep2Input.getPhone() == 1 && mStep2Input.getTaobao() == 0) {
                    mView.showTaobaoView();
                }
                break;
            case 5:
                if (mStep2Input.getInsurance() == 1 && mStep2Input.getBioassay() == 1 && mStep2Input.getPersonal() == 1
                        && mStep2Input.getPhone() == 1 && mStep2Input.getTaobao() == 1 && mStep2Input.getCar() == 0) {
                    mView.showVehicleView();
                }
                break;
        }
    }

    @Override
    public void onClickNext() {
        if (mStep2Input.getInsurance() == 1 && mStep2Input.getBioassay() == 1 && mStep2Input.getPersonal() == 1
                && mStep2Input.getPhone() == 1 && mStep2Input.getTaobao() == 1 && mStep2Input.getCar() == 1) {
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
        }
    }
}
