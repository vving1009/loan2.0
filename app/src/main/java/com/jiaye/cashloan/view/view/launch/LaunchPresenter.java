package com.jiaye.cashloan.view.view.launch;

import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.data.launch.LaunchDataSource;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * LaunchPresenter
 *
 * @author 贾博瑄
 */

public class LaunchPresenter extends BasePresenterImpl implements LaunchContract.Presenter {

    private final LaunchContract.View mView;

    private final LaunchDataSource mDataSource;

    public LaunchPresenter(LaunchContract.View view, LaunchDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Disposable disposable = Flowable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long countDown) throws Exception {
                        if (mDataSource.isNeedGuide()) {
                            mView.showGuideView();
                        } else {
                            mView.showMainView();
                        }
                    }
                });
        mCompositeDisposable.add(disposable);
    }
}
