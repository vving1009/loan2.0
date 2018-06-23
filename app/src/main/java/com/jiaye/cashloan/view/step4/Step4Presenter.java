package com.jiaye.cashloan.view.step4;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.http.data.my.CreditInfo;
import com.jiaye.cashloan.http.data.step4.Step4;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.step4.source.Step4DataSource;

import org.reactivestreams.Publisher;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Step4Presenter
 *
 * @author 贾博瑄
 */

public class Step4Presenter extends BasePresenterImpl implements Step4Contract.Presenter {

    private final Step4Contract.View mView;

    private final Step4DataSource mDataSource;

    private Step mStep;

    public Step4Presenter(Step4Contract.View view, Step4DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void requestStep() {
        Disposable disposable = mDataSource.requestStep()
                .doOnNext(step -> mStep = step)
                .filter(step -> step.getStep() == 7 || step.getStep() == 10)
                .flatMap((Function<Step, Publisher<Step4>>) step -> mDataSource.requestStep4())
                .compose(new ViewTransformer<Step4>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(step4 -> {
                    mView.dismissProgressDialog();
                    if (!TextUtils.isEmpty(step4.getAmount())) {
                        mView.setText("您的批核金额为" + step4.getAmount());
                    }
                    mView.setLayoutVisibility();
                    switch (mStep.getStep()) {
                        case 7:
                            mView.setBtnTextById(R.string.step4_confirm);
                            break;
                        case 10:
                            mView.setBtnTextById(R.string.step4_open);
                            break;
                    }


                }, new ThrowableConsumer(mView), mView::dismissProgressDialog);
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onClickConfirm() {
        if (mStep != null) {
            if (mStep.getStep() == 7) {
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

    @Override
    public void onClickOpen() {
        Disposable disposable = mDataSource.creditInfo()
                .compose(new ViewTransformer<CreditInfo>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(creditInfo -> {
                    mView.dismissProgressDialog();
                    //01-未开户;02-已开户未绑卡;03-已开户已绑卡
                    switch (creditInfo.getBankStatus()) {
                        case "01":
                            mView.showBindBankView();
                            break;
                        default:
                            mView.showToastById(R.string.my_credit_account_error);
                            break;
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
