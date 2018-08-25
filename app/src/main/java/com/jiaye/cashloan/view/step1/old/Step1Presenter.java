package com.jiaye.cashloan.view.step1.old;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.step2.Step2Input;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.step1.old.source.Step1DataSource;

import io.reactivex.disposables.Disposable;

/**
 * Step2InputPresenter
 *
 * @author 贾博瑄
 */

public class Step1Presenter extends BasePresenterImpl implements Step1Contract.Presenter {

    private final Step1Contract.View mView;

    private final Step1DataSource mDataSource;

    private Step2Input mStep2Input;

    public Step1Presenter(Step1Contract.View view, Step1DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void requestStep() {
        Disposable disposable = mDataSource.requestStep1()
                .compose(new ViewTransformer<>())
                .subscribe(step1 -> {
                    mStep2Input = step1;
                    mView.setStep1(step1);
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onClickItem(int position) {
        switch (position) {
            /*case 0:
                if (mStep2Input.getId() == 0) {
                    mView.showIDView();
                }
                break;
            case 1:
                if (mStep2Input.getId() == 1 && mStep2Input.getBioassay() == 0) {
                    mView.showBioassayView();
                }
                break;
            case 2:
                if (mStep2Input.getId() == 1 && mStep2Input.getBioassay() == 1 && mStep2Input.getPersonal() == 0) {
                    mView.showInfoView();
                }
                break;
            case 3:
                if (mStep2Input.getId() == 1 && mStep2Input.getBioassay() == 1 && mStep2Input.getPersonal() == 1
                        && mStep2Input.getPhone() == 0) {
                    mView.showPhoneView();
                }
                break;
            case 4:
                if (mStep2Input.getId() == 1 && mStep2Input.getBioassay() == 1 && mStep2Input.getPersonal() == 1
                        && mStep2Input.getPhone() == 1 && mStep2Input.getCar() == 0) {
                    mView.showVehicleView();
                }
                break;*/
        }
    }

    @Override
    public void onClickNext() {
/*        if (mStep2Input.getId() == 1 && mStep2Input.getBioassay() == 1 && mStep2Input.getPersonal() == 1
                && mStep2Input.getPhone() == 1 && mStep2Input.getCar() == 1) {
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
    }
}
