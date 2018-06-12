package com.jiaye.cashloan.view.step2;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.http.data.step2.Step2;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.step2.source.Step2DataSource;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Step2Presenter
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
                .filter(step -> step.getStep() == 2 || step.getStep() == 3 || step.getStep() == 4)
                .flatMap((Function<Step, Publisher<Step2>>) step -> {
                    mStep = step;
                    if (step.getStep() == 2) {
                        return Flowable.just(new Step2());
                    } else {
                        return mDataSource.requestStep2();
                    }
                })
                .compose(new ViewTransformer<Step2>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(step2 -> {
                    mView.dismissProgressDialog();
                    if (!TextUtils.isEmpty(step2.getAmount())) {
                        mView.setText("您的爱车估值" + step2.getAmount());
                    } else {
                        if (!TextUtils.isEmpty(step2.getMsg())) {
                            mView.setText(step2.getMsg());
                        }
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onClickNext() {
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
