package com.jiaye.cashloan.view.step1;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.step1.Step1;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.step1.source.Step1DataSource;

import io.reactivex.disposables.Disposable;

/**
 * Step1Presenter
 *
 * @author 贾博瑄
 */

public class Step1Presenter extends BasePresenterImpl implements Step1Contract.Presenter {

    private final Step1Contract.View mView;

    private final Step1DataSource mDataSource;

    private Step1 mStep1;

    public Step1Presenter(Step1Contract.View view, Step1DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void requestStep() {
        Disposable disposable = mDataSource.requestStep1()
                .compose(new ViewTransformer<>())
                .subscribe(step1 -> {
                    mStep1 = step1;
                    mView.setStep1(step1);
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onClickItem(int position) {
        switch (position) {
            case 0:
                if (mStep1.getId() == 0) {
                    mView.showIDView();
                }
                break;
            case 1:
                if (mStep1.getId() == 1 && mStep1.getBioassay() == 0) {
                    mView.showBioassayView();
                }
                break;
            case 2:
                if (mStep1.getId() == 1 && mStep1.getBioassay() == 1 && mStep1.getPersonal() == 0) {
                    mView.showInfoView();
                }
                break;
            case 3:
                if (mStep1.getId() == 1 && mStep1.getBioassay() == 1 && mStep1.getPersonal() == 1
                        && mStep1.getPhone() == 0) {
                    mView.showPhoneView();
                }
                break;
            case 4:
                if (mStep1.getId() == 1 && mStep1.getBioassay() == 1 && mStep1.getPersonal() == 1
                        && mStep1.getPhone() == 1 && mStep1.getCar() == 0) {
                    mView.showVehicleView();
                }
                break;
        }
    }

    @Override
    public void onClickNext() {
        if (mStep1.getId() == 1 && mStep1.getBioassay() == 1 && mStep1.getPersonal() == 1
                && mStep1.getPhone() == 1 && mStep1.getCar() == 1) {
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
