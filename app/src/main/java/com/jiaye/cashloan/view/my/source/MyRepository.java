package com.jiaye.cashloan.view.my.source;

import android.text.TextUtils;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.my.CheckAccount;
import com.jiaye.cashloan.http.data.my.CheckAccountRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.persistence.User;
import com.jiaye.cashloan.view.LocalException;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * MyRepository
 *
 * @author 贾博瑄
 */

public class MyRepository implements MyDataSource {

    @Override
    public Flowable<User> queryUser() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser());
    }

    @Override
    public Flowable<Boolean> exit() {
        return Flowable.just(true)
                .map(delete -> {
                    LoanApplication.getInstance().getDbHelper().deleteUser();
                    return delete;
                });
    }

    @Override
    public Flowable<CheckAccount> checkBank() {
        return queryUser().map(user -> {
            if (TextUtils.isEmpty(user.getToken())) {
                throw new LocalException(R.string.error_auth_not_log_in);
            } else {
                return user;
            }
        }).map(user -> new CheckAccountRequest())
                .compose(new SatcatcheResponseTransformer<CheckAccountRequest, CheckAccount>
                        ("checkAccount"));
    }

    @Override
    public Flowable<Boolean> checkPlan() {
        // todo 还款计划接口
        return queryUser().map(user -> {
            if (TextUtils.isEmpty(user.getToken())) {
                throw new LocalException(R.string.error_auth_not_log_in);
            } else {
                return user;
            }
        }).flatMap((Function<User, Publisher<Boolean>>) user -> Flowable.just(false));
    }
}
