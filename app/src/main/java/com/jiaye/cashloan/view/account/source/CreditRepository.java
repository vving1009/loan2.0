package com.jiaye.cashloan.view.account.source;

import com.jiaye.cashloan.http.LoanClient;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.bindbank.SaveBankCardRequest;
import com.jiaye.cashloan.http.data.my.CreditBalance;
import com.jiaye.cashloan.http.data.my.CreditBalanceRequest;
import com.jiaye.cashloan.http.data.my.CreditInfo;
import com.jiaye.cashloan.http.data.my.CreditInfoRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordResetRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordStatus;
import com.jiaye.cashloan.http.data.my.CreditPasswordStatusRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;

import org.reactivestreams.Publisher;

import java.net.URLEncoder;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;
import retrofit2.Response;

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
    public Flowable<CreditInfo> creditInfo() {
        return Flowable.just(new CreditInfoRequest())
                .compose(new ResponseTransformer<CreditInfoRequest, CreditInfo>("creditInfo"));
    }

    @Override
    public Flowable<CreditBalance> balance() {
        return Flowable.just(new CreditBalanceRequest())
                .compose(new ResponseTransformer<CreditBalanceRequest, CreditBalance>("creditBalance"));
    }

    @Override
    public Flowable<EmptyResponse> saveBankCard(CreditInfo creditInfo) {
        SaveBankCardRequest request = new SaveBankCardRequest();
        request.setName(creditInfo.getAccountName());
        request.setNumber(creditInfo.getBankNo());
        request.setPhone(creditInfo.getPhone());
        return Flowable.just(request)
                .compose(new SatcatcheResponseTransformer<SaveBankCardRequest, EmptyResponse>("saveBankCard"));
    }

    @Override
    public Flowable<CreditPasswordRequest> password() {
        return creditInfo().map(creditInfo -> {
            mPasswordRequest = new CreditPasswordRequest();
            mPasswordRequest.setAccountId(creditInfo.getAccountId());
            mPasswordRequest.setIdNo(creditInfo.getId());
            mPasswordRequest.setName(creditInfo.getName());
            mPasswordRequest.setMobile(creditInfo.getPhone());
            return mPasswordRequest;
        }).flatMap((Function<CreditPasswordRequest, Publisher<Response<ResponseBody>>>) request -> LoanClient.INSTANCE.getService().sign(URLEncoder.encode(request.toString()))).flatMap((Function<Response<ResponseBody>, Publisher<CreditPasswordRequest>>) sign -> {
            mPasswordRequest.setSign(new String(sign.body().string().getBytes("UTF-8")));
            return Flowable.just(mPasswordRequest);
        });
    }

    @Override
    public Flowable<CreditPasswordResetRequest> passwordReset() {
        return creditInfo().map(creditInfo -> {
            mPasswordResetRequest = new CreditPasswordResetRequest();
            mPasswordResetRequest.setAccountId(creditInfo.getAccountId());
            mPasswordResetRequest.setIdNo(creditInfo.getId());
            mPasswordResetRequest.setName(creditInfo.getName());
            mPasswordResetRequest.setMobile(creditInfo.getPhone());
            return mPasswordResetRequest;
        }).flatMap((Function<CreditPasswordResetRequest, Publisher<Response<ResponseBody>>>) request -> LoanClient.INSTANCE.getService().sign(URLEncoder.encode(request.toString()))).flatMap((Function<Response<ResponseBody>, Publisher<CreditPasswordResetRequest>>) sign -> {
            mPasswordResetRequest.setSign(new String(sign.body().string().getBytes("UTF-8")));
            return Flowable.just(mPasswordResetRequest);
        });
    }
}
