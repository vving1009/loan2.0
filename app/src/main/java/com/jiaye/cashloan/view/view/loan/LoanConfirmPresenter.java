package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.LoanConfirmInfo;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.source.LoanConfirmDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * LoanConfirmPresenter
 *
 * @author 贾博瑄
 */

public class LoanConfirmPresenter extends BasePresenterImpl implements LoanConfirmContract.Presenter {

    private final LoanConfirmContract.View mView;

    private final LoanConfirmDataSource mDataSource;

    public LoanConfirmPresenter(LoanConfirmContract.View view, LoanConfirmDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.requestLoanConfirmInfo()
                .compose(new ViewTransformer<LoanConfirmInfo>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<LoanConfirmInfo>() {
                    @Override
                    public void accept(LoanConfirmInfo loanConfirm) throws Exception {
                        String unit = LoanApplication.getInstance().getString(R.string.loan_confirm_unit);
                        mView.setLoan(loanConfirm.getLoan() + unit);
                        mView.setService(loanConfirm.getService() + unit);
                        mView.setConsult(loanConfirm.getConsult() + unit);
                        mView.setInterest(loanConfirm.getInterest() + unit);
                        mView.setDeadline(loanConfirm.getDeadline());
                        mView.setPaymentMethod(loanConfirm.getPaymentMethod());
                        mView.setAmount(loanConfirm.getAmount() + unit);
                        mView.dismissProgressDialog();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void details() {
        Disposable disposable = mDataSource.queryLoanId()
                .compose(new ViewTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String loanId) throws Exception {
                        mView.showLoanDetailsView(loanId);
                    }
                },new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void confirm() {
        Disposable disposable = mDataSource.requestLoanConfirm()
                .compose(new ViewTransformer<String>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String loanId) throws Exception {
                        mView.dismissProgressDialog();
                        mView.showLoanProgressView(loanId);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
