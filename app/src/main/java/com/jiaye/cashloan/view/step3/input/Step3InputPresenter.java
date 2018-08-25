package com.jiaye.cashloan.view.step3.input;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.step3.Step3;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.step3.input.source.Step3InputDataSource;

import io.reactivex.disposables.Disposable;

/**
 * Step3InputPresenter
 *
 * @author 贾博瑄
 */

public class Step3InputPresenter extends BasePresenterImpl implements Step3InputContract.Presenter {

    private final Step3InputContract.View mView;

    private final Step3InputDataSource mDataSource;

    private Step3InputState mStep3;

    public Step3InputPresenter(Step3InputContract.View view, Step3InputDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
        mStep3 = new Step3InputState();
        mView.setStep3(mStep3);
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
    public void onClickNext() {
        /*if (mStep3.isFinishItem0() && mStep3.isFinishItem1()) {
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
            mView.showToastById(R.string.step3_error);
        }*/
        mView.showResultView();
    }
}
