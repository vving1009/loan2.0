package com.jiaye.cashloan.view.data.my.credit.source;

import com.jiaye.cashloan.http.LoanClient;
import com.jiaye.cashloan.view.data.my.credit.CreditBalanceRequest;
import com.jiaye.cashloan.view.data.my.credit.CreditCashRequest;
import com.jiaye.cashloan.view.data.my.credit.CreditPasswordRequest;

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

    private CreditPasswordRequest mRequest;

    @Override
    public Flowable<CreditPasswordRequest> password() {
        // TODO: 2018/1/2 查询服务器数据
        mRequest = new CreditPasswordRequest();
        mRequest.setIdNo("120101198610240518");
        mRequest.setName("贾博瑄");
        mRequest.setMobile("13752126558");
        mRequest.setRetUrl("www.baidu.com");
        mRequest.setNotifyUrl("www.baidu.com");
        mRequest.setAcqRes("www.baidu.com");
        return Flowable.just(mRequest).flatMap(new Function<CreditPasswordRequest, Publisher<retrofit2.Response<ResponseBody>>>() {
            @Override
            public Publisher<retrofit2.Response<ResponseBody>> apply(CreditPasswordRequest request) throws Exception {
                return LoanClient.INSTANCE.getService().sign(URLEncoder.encode(request.toString()));
            }
        }).flatMap(new Function<retrofit2.Response<ResponseBody>, Publisher<CreditPasswordRequest>>() {
            @Override
            public Publisher<CreditPasswordRequest> apply(retrofit2.Response<ResponseBody> sign) throws Exception {
                mRequest.setSign(new String(sign.body().string().getBytes("UTF-8")));
                return Flowable.just(mRequest);
            }
        });
    }

    @Override
    public Flowable<CreditCashRequest> cash() {
        return null;
    }

    @Override
    public Flowable<CreditBalanceRequest> balance() {
        return null;
    }
}
