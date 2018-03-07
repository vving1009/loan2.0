package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.http.data.loan.SupportBankList;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.source.LoanSupportBankDataSource;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * LoanSupportBankPresenter
 *
 * @author 贾博瑄
 */

public class LoanSupportBankPresenter extends BasePresenterImpl implements LoanSupportBankContract.Presenter {

    private LoanSupportBankContract.View mView;

    private LoanSupportBankDataSource mDataSource;

    public LoanSupportBankPresenter(LoanSupportBankContract.View view, LoanSupportBankDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.supportBankList()
                .compose(new ViewTransformer<List<SupportBankList.Data>>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<List<SupportBankList.Data>>() {
                    @Override
                    public void accept(List<SupportBankList.Data> list) throws Exception {
                        mView.dismissProgressDialog();
                        mView.setList(list);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
