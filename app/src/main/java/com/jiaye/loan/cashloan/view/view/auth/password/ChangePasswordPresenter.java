package com.jiaye.loan.cashloan.view.view.auth.password;

import android.text.TextUtils;

import com.jiaye.loan.cashloan.BuildConfig;
import com.jiaye.loan.cashloan.R;
import com.jiaye.loan.cashloan.http.data.auth.password.ChangePassword;
import com.jiaye.loan.cashloan.http.data.auth.password.ChangePasswordRequest;
import com.jiaye.loan.cashloan.http.utils.NetworkTransformer;
import com.jiaye.loan.cashloan.utils.RSAUtil;
import com.jiaye.loan.cashloan.view.BasePresenterImpl;
import com.jiaye.loan.cashloan.view.ThrowableConsumer;
import com.jiaye.loan.cashloan.view.data.BaseDataSource;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * ChangePasswordPresenter
 *
 * @author 贾博瑄
 */

public class ChangePasswordPresenter extends BasePresenterImpl implements ChangePasswordContract.Presenter {

    private ChangePasswordContract.View mView;

    public ChangePasswordPresenter(ChangePasswordContract.View view, BaseDataSource dataSource) {
        super(dataSource);
        mView = view;
        mView.setPresenter(this);
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
            Observable.just(request)
                    .compose(new NetworkTransformer<ChangePasswordRequest, ChangePassword>(mView, "changePassword"))
                    .subscribe(new Consumer<ChangePassword>() {
                        @Override
                        public void accept(ChangePassword changePassword) throws Exception {
                            mView.finish();
                        }
                    }, new ThrowableConsumer(mView));
        }
    }
}
