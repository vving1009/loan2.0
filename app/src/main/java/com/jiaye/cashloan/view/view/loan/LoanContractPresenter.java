package com.jiaye.cashloan.view.view.loan;

import com.google.gson.Gson;
import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.data.loan.Contract;
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

    private String mLoanId;

    public LoanContractPresenter(LoanContractContract.View view, LoanContractDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void setLoanId(String loanId) {
        mLoanId = loanId;
    }

    @Override
    public void showContract() {
        Disposable disposable = mDataSource.watchContract(mLoanId)
                .compose(new ViewTransformer<Request<WatchContractRequest>>())
                .subscribe(new Consumer<Request<WatchContractRequest>>() {
                    @Override
                    public void accept(Request<WatchContractRequest> request) throws Exception {
                        mView.postUrl(BuildConfig.BASE_URL + "compact/cashLoan", new Gson().toJson(request).getBytes());
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void requestContract() {
        Disposable disposable = mDataSource.contract()
                .compose(new ViewTransformer<Contract>(){
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<Contract>() {
                    @Override
                    public void accept(Contract contract) throws Exception {
                        mView.dismissProgressDialog();
                        mView.result();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
