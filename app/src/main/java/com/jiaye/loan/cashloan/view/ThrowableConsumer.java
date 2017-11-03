package com.jiaye.loan.cashloan.view;

import com.jiaye.loan.cashloan.R;
import com.jiaye.loan.cashloan.http.base.NetworkException;

import io.reactivex.functions.Consumer;

/**
 * ThrowableConsumer
 *
 * @author 贾博瑄
 */

public class ThrowableConsumer implements Consumer<Throwable> {

    private BaseViewContract mContract;

    public ThrowableConsumer() {
        this(null);
    }

    public ThrowableConsumer(BaseViewContract contract) {
        mContract = contract;
        if (mContract == null) {
            mContract = new BaseViewContractSample();
        }
    }

    @Override
    public void accept(Throwable t) throws Exception {
        if (t instanceof LocalException) {
            switch (((LocalException) t).getErrorId()) {
                case R.string.error_auth_not_log_in:
                    if (mContract instanceof AuthView) {
                        ((AuthView) mContract).startAuthView();
                    }
                    break;
                default:
                    mContract.showToastById(((LocalException) t).getErrorId());
                    break;
            }
        } else if (t instanceof NetworkException) {
            mContract.showToast(((NetworkException) t).getErrorMessage());
        } else {
            mContract.showToast(t.getMessage());
        }
    }

    private static class BaseViewContractSample implements BaseViewContract {

        @Override
        public void showToast(String string) {

        }

        @Override
        public void showToastById(int resId) {

        }

        @Override
        public void showProgressDialog() {

        }
    }
}
