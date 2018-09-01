package com.jiaye.cashloan.view.step3.input;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.search.SaveSalesman;
import com.jiaye.cashloan.http.data.search.SaveSalesmanRequest;
import com.jiaye.cashloan.persistence.Salesman;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.step3.input.source.Step3InputDataSource;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Step3InputPresenter
 *
 * @author 贾博瑄
 */

public class Step3InputPresenter extends BasePresenterImpl implements Step3InputContract.Presenter {

    private final Step3InputContract.View mView;

    private final Step3InputDataSource mDataSource;

    public Step3InputPresenter(Step3InputContract.View view, Step3InputDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        /*Disposable disposable = mDataSource.salesman()
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
        mCompositeDisposable.add(disposable);*/
    }

    @Override
    public void requestStep() {
        /*Disposable disposable = mDataSource.requestStep3()
                .compose(new ViewTransformer<Step3>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(step3 -> {
                    mView.dismissProgressDialog();
                    mView.setStep3(step3);
                    mStep3 = step3;
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);*/
    }

    @Override
    public void onClickItem(int position) {
        switch (position) {
            case 0:
                /*if (mStep3.getTaobao() == 0) {
                    mView.showTaoBaoView();
                }*/
                mView.showCompanyView();
                break;
            case 1:
                /*if (mStep3.getTaobao() == 1 && mStep3.getFile() == 0) {
                    mView.showFileView();
                }*/
                mView.showSalesmanView();
                break;
        }
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
}
