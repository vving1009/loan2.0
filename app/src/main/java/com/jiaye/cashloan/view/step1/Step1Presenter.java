package com.jiaye.cashloan.view.step1;

import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.http.data.step1.Step1;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.step1.source.Step1DataSource;

import org.reactivestreams.Publisher;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

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
        Disposable disposable = mDataSource.requestStep()
                .filter(step -> step.getStep() >= 1)
                .concatMap((Function<Step, Publisher<Step1>>) step -> mDataSource.requestStep1())
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
                    mView.showPersonalView();
                }
                break;
            case 3:
                if (mStep1.getId() == 1 && mStep1.getBioassay() == 1 && mStep1.getPersonal() == 1
                        && mStep1.getPhone() == 0) {
                    mView.showPhoneView();
                }
                break;
        }
    }
}
