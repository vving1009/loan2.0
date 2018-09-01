package com.jiaye.cashloan.view.step3;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.search.SaveSalesman;
import com.jiaye.cashloan.http.data.search.SaveSalesmanRequest;
import com.jiaye.cashloan.http.data.step3.Step3;
import com.jiaye.cashloan.persistence.Salesman;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.step3.source.Step3DataSource;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

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
    public void onClickNext(Salesman salesman) {
        if (salesman == null || TextUtils.isEmpty(salesman.getName())) {
            mView.showToastById(R.string.search_error);
        } else {
            Disposable disposable = Flowable.just(salesman)
                    .flatMap((Function<Salesman, Publisher<SaveSalesman>>) bean -> {
                        SaveSalesmanRequest request = new SaveSalesmanRequest();
                        request.setCompanyId(bean.getCompanyId());
                        request.setCompanyName(bean.getCompany());
                        request.setName(bean.getName());
                        request.setNumber(bean.getWorkId());
                        return mDataSource.saveSalesman(request);
                    })
                    .compose(new ViewTransformer<SaveSalesman>() {
                        @Override
                        public void accept() {
                            super.accept();
                            mView.showProgressDialog();
                        }
                    })
                    .subscribe(saveSalesman -> {
                        mView.dismissProgressDialog();
                        //mView.exit();
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        }
    }

    @Override
    public void requestUpdateStep() {
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

    @Override
    public void requestStep() {

    }
}
