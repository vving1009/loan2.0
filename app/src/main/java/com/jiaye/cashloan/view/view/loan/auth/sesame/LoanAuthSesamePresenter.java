package com.jiaye.cashloan.view.view.loan.auth.sesame;

import com.jiaye.cashloan.http.data.loan.Sesame;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.auth.source.sesame.LoanAuthSesameDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * LoanAuthSesamePresenter
 *
 * @author 贾博瑄
 */

public class LoanAuthSesamePresenter extends BasePresenterImpl implements LoanAuthSesameContract.Presenter {

    private final LoanAuthSesameContract.View mView;

    private final LoanAuthSesameDataSource mDataSource;

    public LoanAuthSesamePresenter(LoanAuthSesameContract.View view, LoanAuthSesameDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void request() {
        Disposable disposable = mDataSource.sesame()
                .compose(new ViewTransformer<Sesame>(){
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<Sesame>() {
                    @Override
                    public void accept(Sesame sesame) throws Exception {
                        mView.dismissProgressDialog();
                        mView.result();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
