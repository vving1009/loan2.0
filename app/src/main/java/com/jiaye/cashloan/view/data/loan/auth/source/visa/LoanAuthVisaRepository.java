package com.jiaye.cashloan.view.data.loan.auth.source.visa;

import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.base.RequestContent;
import com.jiaye.cashloan.http.base.RequestHeader;
import com.jiaye.cashloan.http.data.loan.LoanVisaRequest;
import com.jiaye.cashloan.http.data.loan.LoanVisaSMS;
import com.jiaye.cashloan.http.data.loan.LoanVisaSMSRequest;
import com.jiaye.cashloan.http.data.loan.LoanVisaSign;
import com.jiaye.cashloan.http.data.loan.LoanVisaSignRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * LoanAuthVisaRepository
 *
 * @author 贾博瑄
 */

public class LoanAuthVisaRepository implements LoanAuthVisaDataSource {

    @Override
    public Flowable<Request<LoanVisaRequest>> visa() {
        Request<LoanVisaRequest> request = new Request<>();
        RequestContent<LoanVisaRequest> content = new RequestContent<>();
        List<LoanVisaRequest> list = new ArrayList<>();
        LoanVisaRequest visaRequest = new LoanVisaRequest();
        list.add(visaRequest);
        content.setBody(list);
        RequestHeader header = RequestHeader.create();
        header.setPhone("");
        content.setHeader(header);
        request.setContent(content);
        return Flowable.just(request);
    }

    @Override
    public Flowable<LoanVisaSMS> sendSMS() {
        LoanVisaSMSRequest request = new LoanVisaSMSRequest();
        return Flowable.just(request).compose(new ResponseTransformer<LoanVisaSMSRequest, LoanVisaSMS>("loanVisaSMS"));
    }

    @Override
    public Flowable<LoanVisaSign> sign(String sms) {
        LoanVisaSignRequest request = new LoanVisaSignRequest();
        request.setSms(sms);
        return Flowable.just(request).compose(new ResponseTransformer<LoanVisaSignRequest, LoanVisaSign>("loanVisaSign"));
    }
}
