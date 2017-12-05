package com.jiaye.cashloan.view.view.loan.auth.sesame;

import com.google.gson.Gson;
import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.data.loan.SesameRequest;
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
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.sesame()
                .compose(new ViewTransformer<Request<SesameRequest>>())
                .subscribe(new Consumer<Request<SesameRequest>>() {
                    @Override
                    public void accept(Request<SesameRequest> request) throws Exception {
                        mView.postUrl(BuildConfig.BASE_URL + "zmfQuery", new Gson().toJson(request).getBytes());
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
