package com.jiaye.cashloan.view.step3;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.step3.Step3;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.step3.source.Step3DataSource;

import io.reactivex.disposables.Disposable;

/**
 * Step3Presenter
 *
 * @author 贾博瑄
 */

public class Step3Presenter extends BasePresenterImpl implements Step3Contract.Presenter {

    private final Step3Contract.View mView;

    private final Step3DataSource mDataSource;

    private Step3 mStep3;

    public Step3Presenter(Step3Contract.View view, Step3DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void requestStep() {
        Disposable disposable = mDataSource.requestStep3()
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
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onClickItem(int position) {
        switch (position) {
            case 0:
                if (mStep3.getTaobao() == 0) {
                    mView.showTaoBaoView();
                }
                break;
            case 1:
                if (mStep3.getTaobao() == 1 && mStep3.getFile() == 0) {
                    mView.showFileView();
                }
                break;
            case 2:
                if (mStep3.getTaobao() == 1 && mStep3.getFile() == 1 && mStep3.getSign() == 0) {
                    mView.showSignView();
                }
                break;
        }
    }

    @Override
    public void onClickNext() {
        if (mStep3.getTaobao() == 1 && mStep3.getFile() == 1 && mStep3.getSign() == 1) {
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
        }
    }
}
