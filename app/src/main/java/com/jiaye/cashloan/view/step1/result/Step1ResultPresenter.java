package com.jiaye.cashloan.view.step1.result;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.http.data.my.CreditInfo;
import com.jiaye.cashloan.http.data.step4.Step4;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.step1.result.source.Step1ResultDataSource;

import org.reactivestreams.Publisher;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Step3ResultPresenter
 *
 * @author 贾博瑄
 */

public class Step1ResultPresenter extends BasePresenterImpl implements Step1ResultContract.Presenter {

    private final Step1ResultContract.View mView;

    private final Step1ResultDataSource mDataSource;


    public Step1ResultPresenter(Step1ResultContract.View view, Step1ResultDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void onClickConfirm() {
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
