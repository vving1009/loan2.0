package com.jiaye.cashloan.view.step3;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.http.data.loan.Visa;
import com.jiaye.cashloan.http.data.my.CreditInfo;
import com.jiaye.cashloan.http.data.search.SaveSalesmanRequest;
import com.jiaye.cashloan.persistence.Salesman;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.step3.source.Step3DataSource;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Step3Presenter
 *
 * @author 贾博瑄
 */

public class Step3Presenter extends BasePresenterImpl implements Step3Contract.Presenter {

    private final Step3Contract.View mView;

    private final Step3DataSource mDataSource;

    public Step3Presenter(Step3Contract.View view, Step3DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.salesman()
                .compose(new ViewTransformer<com.jiaye.cashloan.http.data.search.Salesman>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(list -> {
                    mView.dismissProgressDialog();
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onClickNext(Salesman salesman) {
        if (salesman == null || TextUtils.isEmpty(salesman.getName())) {
            mView.showToastById(R.string.search_error);
        } else {
            Disposable disposable = Flowable.just(salesman)
                    .flatMap((Function<Salesman, Publisher<EmptyResponse>>) bean -> {
                        SaveSalesmanRequest request = new SaveSalesmanRequest();
                        request.setCompanyId(bean.getCompanyId());
                        request.setCompanyName(bean.getCompany());
                        request.setName(bean.getName());
                        request.setNumber(bean.getWorkId());
                        return mDataSource.saveSalesman(request);
                    })
                    .flatMap(response -> mDataSource.requestUpdateStep())
                    .compose(new ViewTransformer<EmptyResponse>() {
                        @Override
                        public void accept() {
                            super.accept();
                            mView.showProgressDialog();
                        }
                    })
                    .subscribe(response -> {
                        mView.dismissProgressDialog();
                        mView.sendBroadcast();
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        }
    }

    @Override
    public void requestNextStep() {
        Disposable disposable = mDataSource.requestStep()
                .filter(step -> step.getStep() == 7)
                .flatMap(step -> mDataSource.sign())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(visa -> mView.showMoreInfoView())
                .defaultIfEmpty(new Visa())
                .observeOn(Schedulers.io())
                .flatMap(visa -> mDataSource.requestStep())
                .filter(step -> step.getStep() == 10)
                .flatMap(step -> mDataSource.creditInfo())
                .compose(new ViewTransformer<CreditInfo>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(creditInfo -> {
                    if (creditInfo.getBankStatus().equals("01")) {
                        // 如果没开户显示开户页面
                        mView.showOpenAccountView();
                    } else {
                        mView.sendBroadcast();
                    }
                }, new ThrowableConsumer(mView), mView::dismissProgressDialog);
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void requestStep() {
        Disposable disposable = mDataSource.requestStep()
                .compose(new ViewTransformer<Step>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .doOnNext(step -> {
                    switch (step.getStep()) {
                        case 5:
                            mView.showInputView();
                            break;
                        case 6:
                            mView.showWaitView();
                            break;
                        case 3:
                            mView.showRejectView();
                            break;
                    }
                })
                .filter(step -> step.getStep() == 7 || step.getStep() == 10)
                .observeOn(Schedulers.io())
                .flatMap(step -> mDataSource.requestAmountMoney())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(money -> {
                    mView.showSuccessView(money.getAmount());
                })
                .subscribe(money -> {}, new ThrowableConsumer(mView), mView::dismissProgressDialog);
        mCompositeDisposable.add(disposable);
    }
}
