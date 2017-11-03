package com.jiaye.loan.cashloan.http.utils;

import com.jiaye.loan.cashloan.http.LoanClient;
import com.jiaye.loan.cashloan.http.LoanService;
import com.jiaye.loan.cashloan.http.base.Request;
import com.jiaye.loan.cashloan.http.base.Response;
import com.jiaye.loan.cashloan.view.BaseViewContract;

import java.lang.reflect.Method;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * NetworkTransformer
 *
 * @author 贾博瑄
 */

public class NetworkTransformer<Upstream, Downstream> implements ObservableTransformer<Upstream, Downstream> {

    private BaseViewContract mContract;

    private String mMethodName;

    public NetworkTransformer(String methodName) {
        mMethodName = methodName;
    }

    public NetworkTransformer(BaseViewContract contract, String methodName) {
        mContract = contract;
        mMethodName = methodName;
    }

    @Override
    public ObservableSource<Downstream> apply(Observable<Upstream> upstream) {
        return upstream.map(new RequestFunction<Upstream>())
                .flatMap(new Function<Request<Upstream>, ObservableSource<Response<Downstream>>>() {
                    @Override
                    public ObservableSource<Response<Downstream>> apply(Request<Upstream> upstreamRequest) throws Exception {
                        LoanService loanService = LoanClient.INSTANCE.getService();
                        Method method = loanService.getClass().getMethod(mMethodName, upstreamRequest.getClass());
                        //noinspection unchecked
                        return (ObservableSource<Response<Downstream>>) method.invoke(loanService, upstreamRequest);
                    }
                })
                .map(new ResponseFunction<Downstream>())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (mContract != null) {
                            mContract.showProgressDialog();
                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
