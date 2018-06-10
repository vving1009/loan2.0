package com.jiaye.cashloan.view.account.source;

import com.jiaye.cashloan.http.LoanClient;
import com.jiaye.cashloan.http.data.my.CreditBalance;
import com.jiaye.cashloan.http.data.my.CreditBalanceRequest;
import com.jiaye.cashloan.http.data.my.CreditInfo;
import com.jiaye.cashloan.http.data.my.CreditInfoRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordResetRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordStatus;
import com.jiaye.cashloan.http.data.my.CreditPasswordStatusRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;

import org.reactivestreams.Publisher;

import java.net.URLEncoder;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * CreditRepository
 *
 * @author 贾博瑄
 */

public class CreditRepository implements CreditDataSource {

    private CreditPasswordRequest mPasswordRequest;

    private CreditPasswordResetRequest mPasswordResetRequest;

    @Override
    public Flowable<CreditPasswordStatus> passwordStatus() {
        return Flowable.just(new CreditPasswordStatusRequest())
                .compose(new ResponseTransformer<CreditPasswordStatusRequest, CreditPasswordStatus>("creditPasswordStatus"));
    }

    @Override
    public Flowable<CreditPasswordRequest> password() {
        return creditInfo().map(new Function<CreditInfo, CreditPasswordRequest>() {
            @Override
            public CreditPasswordRequest apply(CreditInfo creditInfo) throws Exception {
                mPasswordRequest = new CreditPasswordRequest();
                mPasswordRequest.setAccountId(creditInfo.getAccountId());
                mPasswordRequest.setIdNo(creditInfo.getId());
                mPasswordRequest.setName(creditInfo.getName());
                mPasswordRequest.setMobile(creditInfo.getPhone());
                return mPasswordRequest;
            }
        }).flatMap(new Function<CreditPasswordRequest, Publisher<retrofit2.Response<ResponseBody>>>() {
            @Override
            public Publisher<retrofit2.Response<ResponseBody>> apply(CreditPasswordRequest request) throws Exception {
                return LoanClient.INSTANCE.getService().sign(URLEncoder.encode(request.toString()));
            }
        }).flatMap(new Function<retrofit2.Response<ResponseBody>, Publisher<CreditPasswordRequest>>() {
            @Override
            public Publisher<CreditPasswordRequest> apply(retrofit2.Response<ResponseBody> sign) throws Exception {
                mPasswordRequest.setSign(new String(sign.body().string().getBytes("UTF-8")));
                return Flowable.just(mPasswordRequest);
            }
        });
    }

    @Override
    public Flowable<CreditPasswordResetRequest> passwordReset() {
        return creditInfo().map(new Function<CreditInfo, CreditPasswordResetRequest>() {
            @Override
            public CreditPasswordResetRequest apply(CreditInfo creditInfo) throws Exception {
                mPasswordResetRequest = new CreditPasswordResetRequest();
                mPasswordResetRequest.setAccountId(creditInfo.getAccountId());
                mPasswordResetRequest.setIdNo(creditInfo.getId());
                mPasswordResetRequest.setName(creditInfo.getName());
                mPasswordResetRequest.setMobile(creditInfo.getPhone());
                return mPasswordResetRequest;
            }
        }).flatMap(new Function<CreditPasswordResetRequest, Publisher<retrofit2.Response<ResponseBody>>>() {
            @Override
            public Publisher<retrofit2.Response<ResponseBody>> apply(CreditPasswordResetRequest request) throws Exception {
                return LoanClient.INSTANCE.getService().sign(URLEncoder.encode(request.toString()));
            }
        }).flatMap(new Function<retrofit2.Response<ResponseBody>, Publisher<CreditPasswordResetRequest>>() {
            @Override
            public Publisher<CreditPasswordResetRequest> apply(retrofit2.Response<ResponseBody> sign) throws Exception {
                mPasswordResetRequest.setSign(new String(sign.body().string().getBytes("UTF-8")));
                return Flowable.just(mPasswordResetRequest);
            }
        });
    }

    @Override
    public Flowable<CreditBalance> balance() {
        return Flowable.just(new CreditBalanceRequest())
                .compose(new ResponseTransformer<CreditBalanceRequest, CreditBalance>("creditBalance"));
    }

    @Override
    public Flowable<CreditInfo> creditInfo() {
        return Flowable.just(new CreditInfoRequest())
                .compose(new ResponseTransformer<CreditInfoRequest, CreditInfo>("creditInfo"));
    }
}
