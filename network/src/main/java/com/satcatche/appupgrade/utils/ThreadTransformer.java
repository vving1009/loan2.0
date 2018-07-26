package com.satcatche.appupgrade.utils;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * ThreadTransformer
 *
 * @author 贾博瑄
 */

public class ThreadTransformer<Upstream> implements FlowableTransformer<Upstream, Upstream> {

    @Override
    public Publisher<Upstream> apply(Flowable<Upstream> upstream) {
        return upstream.subscribeOn(Schedulers.io())
                .doOnSubscribe(subscription -> accept())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void accept() {

    }
}
