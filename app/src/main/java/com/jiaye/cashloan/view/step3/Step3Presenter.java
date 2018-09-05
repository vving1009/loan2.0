package com.jiaye.cashloan.view.step3;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.home.CheckCompany;
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
    public void onInputViewShown() {
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
    public void uploadSelectSalesman(Salesman salesman) {
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
                    .onErrorReturnItem(new EmptyResponse())
                    .flatMap(response -> mDataSource.requestUpdateStep(6, "到店借款"))
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
                .filter(creditInfo -> creditInfo.getBankStatus().equals("01"))  //如果未开户进入开户界面
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(creditInfo -> mView.showOpenAccountView())
                .defaultIfEmpty(new CreditInfo())  //如果已开户进入等待放款页面
                .observeOn(Schedulers.io())
                .flatMap(creditInfo -> mDataSource.requestUpdateStep(9, "等待放款"))
                .compose(new ViewTransformer<EmptyResponse>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(emptyResponse -> mView.sendBroadcast(), new ThrowableConsumer(mView), mView::dismissProgressDialog);
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void requestStep() {
        Disposable disposable = mDataSource.checkCompany()
                .filter(CheckCompany::isNeed)
                .compose(new ViewTransformer<CheckCompany>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .doOnNext(checkCompany -> mView.showInputView())
                .defaultIfEmpty(new CheckCompany())
                .observeOn(Schedulers.io())
                .flatMap(checkCompany -> mDataSource.requestStep())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(step -> {
                    switch (step.getStep()) {
                        case 5:
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
                .subscribe(money -> mView.showSuccessView(money.getAmount()), new ThrowableConsumer(mView), mView::dismissProgressDialog);
        mCompositeDisposable.add(disposable);
    }

    /**
     * 如果已开户直接进入等待放款
     */
    @Override
    public void onSuccessViewShown() {
        Disposable disposable = Flowable.zip(mDataSource.creditInfo(), mDataSource.requestStep(),
                (creditInfo, step) -> step.getStep() == 10 && !creditInfo.getBankStatus().equals("01"))
                .filter(result -> result)
                .flatMap(result -> mDataSource.requestUpdateStep(9, "等待放款"))
                .compose(new ViewTransformer<EmptyResponse>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                }).subscribe(response -> mView.sendBroadcast(), new ThrowableConsumer(mView), mView::dismissProgressDialog);
        mCompositeDisposable.add(disposable);
    }
}
