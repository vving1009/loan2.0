package com.jiaye.cashloan.view.sign.source;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.base.RequestContent;
import com.jiaye.cashloan.http.base.RequestHeader;
import com.jiaye.cashloan.http.data.loan.LoanVisaRequest;
import com.jiaye.cashloan.http.data.loan.LoanVisaSign;
import com.jiaye.cashloan.http.data.loan.LoanVisaSignRequest;
import com.jiaye.cashloan.http.data.loan.Visa;
import com.jiaye.cashloan.http.data.loan.VisaRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * SignRepository
 *
 * @author 贾博瑄
 */

public class SignRepository implements SignDataSource {

    @Override
    public Flowable<Request<LoanVisaRequest>> show() {
        Request<LoanVisaRequest> request = new Request<>();
        RequestContent<LoanVisaRequest> content = new RequestContent<>();
        List<LoanVisaRequest> list = new ArrayList<>();
        LoanVisaRequest visaRequest = new LoanVisaRequest();
        visaRequest.setType("01");
        list.add(visaRequest);
        content.setBody(list);
        content.setHeader(RequestHeader.create());
        request.setContent(content);
        return Flowable.just(request);
    }

    @Override
    public Flowable<Visa> sign() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    LoanVisaSignRequest request = new LoanVisaSignRequest();
                    request.setLoanId(user.getLoanId());
                    return request;
                })
                .compose(new ResponseTransformer<LoanVisaSignRequest, LoanVisaSign>("loanVisaSign"))
                .map(loanVisaSign -> LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    VisaRequest request = new VisaRequest();
                    request.setLoanId(user.getLoanId());
                    return request;
                })
                .flatMap((Function<VisaRequest, Publisher<Visa>>) visaRequest -> Flowable.just(visaRequest)
                        .compose(new SatcatcheResponseTransformer<VisaRequest, Visa>("visa")));
    }
}
