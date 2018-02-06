package com.jiaye.cashloan.view.view.loan;

import com.google.gson.Gson;
import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.data.loan.WatchContractRequest;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.source.LoanContractDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * LoanContractPresenter
 *
 * @author 贾博瑄
 */

public class LoanContractPresenter extends BasePresenterImpl implements LoanContractContract.Presenter {

    private final LoanContractContract.View mView;

    private final LoanContractDataSource mDataSource;

    private String mContractId;

    public LoanContractPresenter(LoanContractContract.View view, LoanContractDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void setContractId(String contractId) {
        mContractId = contractId;
    }

    @Override
    public void showContract() {
        Disposable disposable = mDataSource.watchContract(mContractId)
                .compose(new ViewTransformer<Request<WatchContractRequest>>())
                .subscribe(new Consumer<Request<WatchContractRequest>>() {
                    @Override
                    public void accept(Request<WatchContractRequest> request) throws Exception {
                        mView.postUrl(BuildConfig.BASE_URL + "shCompactShow/shCompactDetail", new Gson().toJson(request).getBytes());
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
