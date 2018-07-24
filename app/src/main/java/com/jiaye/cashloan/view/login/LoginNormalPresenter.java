package com.jiaye.cashloan.view.login;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.login.Login;
import com.jiaye.cashloan.utils.RegexUtil;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.login.source.LoginNormalDataSource;

import io.reactivex.disposables.Disposable;

/**
 * LoginNormalPresenter
 *
 * @author 贾博瑄
 */
public class LoginNormalPresenter extends BasePresenterImpl implements LoginNormalContract.Presenter {

    private final LoginNormalContract.View mView;

    private final LoginNormalDataSource mDataSource;

    public LoginNormalPresenter(LoginNormalContract.View view, LoginNormalDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void login() {
        if (TextUtils.isEmpty(mView.getPhone()) || !mView.getPhone().matches(RegexUtil.phone())) {/*检测手机号*/
            mView.showToastById(R.string.error_auth_phone);
        } else if (TextUtils.isEmpty(mView.getPassword()) || !mView.getPassword().matches(RegexUtil.password())) {/*检测密码规则*/
            mView.showToastById(R.string.error_auth_password);
        } else {
            Disposable disposable = mDataSource.requestLogin(mView.getPhone(), mView.getPassword())
                    .compose(new ViewTransformer<Login>() {
                        @Override
                        public void accept() {
                            mView.showProgressDialog();
                        }
                    })
                    .subscribe(login -> {
                        mView.dismissProgressDialog();
                        mView.finish();
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        }
    }
}
