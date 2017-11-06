package com.jiaye.cashloan.view;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * ViewTransformer
 *
 * @author 贾博瑄
 */

public class ViewTransformer<Upstream> implements FlowableTransformer<Upstream, Upstream> {

    private BaseViewContract mContract;

    public ViewTransformer() {
    }

    public ViewTransformer(BaseViewContract contract) {
        mContract = contract;
    }

    @Override
    public Publisher<Upstream> apply(Flowable<Upstream> upstream) {
        return upstream.subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        if (mContract != null) {
                            mContract.showProgressDialog();
                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
