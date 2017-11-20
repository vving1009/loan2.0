package com.jiaye.cashloan.view.view.auth.password;

import android.text.TextUtils;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.auth.password.ChangePassword;
import com.jiaye.cashloan.http.data.auth.password.ChangePasswordRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.utils.RSAUtil;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * ChangePasswordPresenter
 *
 * @author 贾博瑄
 */

public class ChangePasswordPresenter extends BasePresenterImpl implements ChangePasswordContract.Presenter {

    private ChangePasswordContract.View mView;

    public ChangePasswordPresenter(ChangePasswordContract.View view) {
        mView = view;
    }

    @Override
    public void save() {
        if (TextUtils.isEmpty(mView.getPassword()) || !mView.getPassword().matches("^[a-zA-Z0-9]{6,12}$")) {/*检测密码规则*/
            mView.showToastById(R.string.error_auth_password);
        } else if (!mView.getPassword().equals(mView.getPasswordSecond())) {
            mView.showToastById(R.string.error_auth_password_different);
        } else {
            ChangePasswordRequest request = new ChangePasswordRequest();
            request.setPhone(mView.getPhone());
            request.setPassword(RSAUtil.encryptByPublicKeyToBase64(mView.getPassword(), BuildConfig.PUBLIC_KEY));
            request.setStatus("1");
            Disposable disposable = Flowable.just(request)
                    .compose(new ResponseTransformer<ChangePasswordRequest, ChangePassword>("changePassword"))
                    .compose(new ViewTransformer<ChangePassword>(mView))
                    .subscribe(new Consumer<ChangePassword>() {
                        @Override
                        public void accept(ChangePassword changePassword) throws Exception {
                            mView.finish();
                        }
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        }
    }
}
