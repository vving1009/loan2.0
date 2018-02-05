package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.http.data.loan.ContractList;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.source.LoanContractListDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * LoanContractListPresenter
 *
 * @author 贾博瑄
 */

public class LoanContractListPresenter extends BasePresenterImpl implements LoanContractListContract.Presenter {

    private final LoanContractListContract.View mView;

    private final LoanContractListDataSource mDataSource;

    public LoanContractListPresenter(LoanContractListContract.View view, LoanContractListDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void requestContractList(String loanId) {
        Disposable disposable = mDataSource.requestContractList(loanId)
                .compose(new ViewTransformer<ContractList.Contract[]>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<ContractList.Contract[]>() {
                    @Override
                    public void accept(ContractList.Contract[] contracts) throws Exception {
                        mView.dismissProgressDialog();
                        mView.setContracts(contracts);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
