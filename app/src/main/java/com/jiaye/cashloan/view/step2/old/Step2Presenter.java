package com.jiaye.cashloan.view.step2.old;

import android.text.TextUtils;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.http.data.step2.Step2Result;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.step2.old.source.Step2DataSource;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Step2InputPresenter
 *
 * @author 贾博瑄
 */

public class Step2Presenter extends BasePresenterImpl implements Step2Contract.Presenter {

    private final Step2Contract.View mView;

    private final Step2DataSource mDataSource;

    private Step mStep;

    public Step2Presenter(Step2Contract.View view, Step2DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void requestStep() {
        Disposable disposable = mDataSource.requestStep()
                .doOnNext(step -> mStep = step)
                .filter(step -> step.getStep() == 2 || step.getStep() == 3 || step.getStep() == 4)
                .flatMap((Function<Step, Publisher<Step2Result>>) step -> {
                    if (step.getStep() == 2) {
                        Step2Result step2Result = new Step2Result();
                        step2Result.setMsg(LoanApplication.getInstance().getResources().getString(R.string.step2_progress));
                        return Flowable.just(step2Result);
                    } else {
                        return mDataSource.requestStep2();
                    }
                })
                .compose(new ViewTransformer<Step2Result>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(step2Result -> {
                    mView.dismissProgressDialog();
                    if (mStep.getStep() == 2 || mStep.getStep() == 3) {
                        if (!TextUtils.isEmpty(step2Result.getMsg())) {
                            mView.setText(step2Result.getMsg());
                        }
                    } else if (mStep.getStep() == 4) {
                        if (!TextUtils.isEmpty(step2Result.getAmount())) {
                            mView.setText("您的爱车估值" + step2Result.getAmount());
                        }
                    }
                }, new ThrowableConsumer(mView), mView::dismissProgressDialog);
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onClickNext() {
        if (mStep != null) {
            if (mStep.getStep() == 2) {
                mView.showToastById(R.string.step2_progress);
            } else if (mStep.getStep() == 3) {
                mView.finish();
            } else if (mStep.getStep() == 4) {
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
            }
        }
    }
}
