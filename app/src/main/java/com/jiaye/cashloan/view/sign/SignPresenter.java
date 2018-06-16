package com.jiaye.cashloan.view.sign;

import com.google.gson.Gson;
import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.data.loan.LoanVisaRequest;
import com.jiaye.cashloan.http.data.loan.Visa;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.sign.source.SignDataSource;

import org.reactivestreams.Publisher;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * SignPresenter
 *
 * @author 贾博瑄
 */

public class SignPresenter extends BasePresenterImpl implements SignContract.Presenter {

    private final SignContract.View mView;

    private final SignDataSource mDataSource;

    public SignPresenter(SignContract.View view, SignDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        show();
    }

    @Override
    public void sign() {
        Disposable disposable = mDataSource.sign()
                .flatMap((Function<Visa, Publisher<Request<LoanVisaRequest>>>) loanVisaSign -> mDataSource.show())
                .compose(new ViewTransformer<Request<LoanVisaRequest>>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(request -> {
                    mView.dismissProgressDialog();
                    mView.hideBtn();
                    loadUrl(request);
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    private void show() {
        Disposable disposable = mDataSource.show()
                .compose(new ViewTransformer<>())
                .subscribe(this::loadUrl, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    private void loadUrl(Request<LoanVisaRequest> request) {
        mView.postUrl(BuildConfig.BASE_URL + "show", new Gson().toJson(request).getBytes());
    }
}
