package com.jiaye.cashloan.view.step4;

import android.text.TextUtils;

import com.jiaye.cashloan.http.data.loan.LoanDate;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.step4.source.Step4DataSource;

import io.reactivex.disposables.Disposable;

/**
 * Step3ResultPresenter
 *
 * @author 贾博瑄
 */

public class Step4Presenter extends BasePresenterImpl implements Step4Contract.Presenter {

    private final Step4Contract.View mView;

    private final Step4DataSource mDataSource;

    public Step4Presenter(Step4Contract.View view, Step4DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void requestLoanStatus() {
        Disposable disposable = mDataSource.requestLoanStatus()
                .compose(new ViewTransformer<LoanDate>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(response -> {
                    mView.dismissProgressDialog();
                    if (TextUtils.isEmpty(response.getContractDate())) {
                        mView.showWaitLoanView();
                    } else {
                        mView.showFinishLoanView();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
