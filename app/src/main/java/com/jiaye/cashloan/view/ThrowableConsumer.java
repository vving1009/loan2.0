package com.jiaye.cashloan.view;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.ErrorCode;
import com.jiaye.cashloan.http.base.NetworkException;
import com.orhanobut.logger.Logger;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

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
        if (t.getMessage() != null) {
            Logger.e(t.getMessage());
        }
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
            if (!((NetworkException) t).getErrorCode().equals(ErrorCode.EMPTY.getCode())) {
                mContract.showToast(((NetworkException) t).getErrorMessage());
                if (((NetworkException) t).getErrorCode().equals(ErrorCode.TOKEN_OVERDUE.getCode())) {
                    LoanApplication.getInstance().reLogin();
                }
            }
        } else if (t instanceof ConnectException || t instanceof UnknownHostException || t instanceof SocketException) {
            mContract.showToastById(R.string.error_connect);
        } else if (t instanceof HttpException) {
            mContract.showToastById(R.string.error_http);
        } else if (t instanceof SocketTimeoutException) {
            mContract.showToastById(R.string.error_socket_timeout);
        } else {
            if (t.getMessage() != null) {
                mContract.showToast(t.getMessage());
            }
        }
        mContract.dismissProgressDialog();
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

        @Override
        public void dismissProgressDialog() {

        }
    }
}
