package com.jiaye.cashloan.view.support;

import com.jiaye.cashloan.http.data.loan.SupportBankList;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.support.source.SupportDataSource;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * SupportPresenter
 *
 * @author 贾博瑄
 */

public class SupportPresenter extends BasePresenterImpl implements SupportContract.Presenter {

    private final SupportContract.View mView;

    private final SupportDataSource mDataSource;

    public SupportPresenter(SupportContract.View view, SupportDataSource dataSource) {
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
                .subscribe(list -> {
                    mView.dismissProgressDialog();
                    mView.setList(list);
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
