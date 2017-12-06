package com.jiaye.cashloan.view.view.my.settings;

import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.my.settings.source.SettingsDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * SettingsPresenter
 *
 * @author 贾博瑄
 */

public class SettingsPresenter extends BasePresenterImpl implements SettingsContract.Presenter {

    private final SettingsContract.View mView;

    private final SettingsDataSource mDataSource;

    public SettingsPresenter(SettingsContract.View view, SettingsDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void exit() {
        Disposable disposable = mDataSource.exit()
                .compose(new ViewTransformer<Boolean>())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mView.result();
                    }
                },new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
