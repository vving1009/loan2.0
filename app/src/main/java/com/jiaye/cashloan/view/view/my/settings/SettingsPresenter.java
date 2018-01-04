package com.jiaye.cashloan.view.view.my.settings;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.my.settings.source.SettingsDataSource;
import com.syd.oden.gesturelock.view.GesturePreference;

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

    private GesturePreference mPreference;

    public SettingsPresenter(SettingsContract.View view, SettingsDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
        mPreference = new GesturePreference(LoanApplication.getInstance(), -1);
    }

    @Override
    public void getGestureStatus() {
        if (mPreference.ReadStringPreference().equals("null")) {
            mView.setSwitch(false);
        } else {
            mView.setSwitch(true);
        }
    }

    @Override
    public void removeGesturePassword() {
        mPreference.WriteStringPreference("null");
    }

    @Override
    public void exit() {
        Disposable disposable = mDataSource.exit()
                .compose(new ViewTransformer<Boolean>())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        removeGesturePassword();
                        mView.result();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
