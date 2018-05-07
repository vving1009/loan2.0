package com.jiaye.cashloan.view.data.loan.auth.source.visa;

import android.database.Cursor;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.base.RequestContent;
import com.jiaye.cashloan.http.base.RequestHeader;
import com.jiaye.cashloan.http.data.loan.LoanVisaRequest;
import com.jiaye.cashloan.http.data.loan.LoanVisaSMS;
import com.jiaye.cashloan.http.data.loan.LoanVisaSMSRequest;
import com.jiaye.cashloan.http.data.loan.LoanVisaSign;
import com.jiaye.cashloan.http.data.loan.LoanVisaSignRequest;
import com.jiaye.cashloan.http.data.loan.Visa;
import com.jiaye.cashloan.http.data.loan.VisaRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * LoanAuthVisaRepository
 *
 * @author 贾博瑄
 */

public class LoanAuthVisaRepository implements LoanAuthVisaDataSource {

    @Override
    public Flowable<Request<LoanVisaRequest>> visa(String type) {
        Request<LoanVisaRequest> request = new Request<>();
        RequestContent<LoanVisaRequest> content = new RequestContent<>();
        List<LoanVisaRequest> list = new ArrayList<>();
        LoanVisaRequest visaRequest = new LoanVisaRequest();
        visaRequest.setType(type);
        list.add(visaRequest);
        content.setBody(list);
        content.setHeader(RequestHeader.create());
        request.setContent(content);
        return Flowable.just(request);
    }

    @Override
    public Flowable<LoanVisaSMS> sendSMS(String type) {
        LoanVisaSMSRequest request = new LoanVisaSMSRequest();
        request.setType(type);
        return Flowable.just(request).compose(new ResponseTransformer<LoanVisaSMSRequest, LoanVisaSMS>("loanVisaSMS"));
    }

    @Override
    public Flowable<Visa> sign(String type, String sms) {
        LoanVisaSignRequest request = new LoanVisaSignRequest();
        request.setSms(sms);
        request.setType(type);
        return Flowable.just(request).compose(new ResponseTransformer<LoanVisaSignRequest, LoanVisaSign>("loanVisaSign"))
                .map(new Function<LoanVisaSign, VisaRequest>() {
                    @Override
                    public VisaRequest apply(LoanVisaSign loanVisaSign) throws Exception {
                        String loanId = "";
                        Cursor cursorUser = LoanApplication.getInstance().getSQLiteDatabase().rawQuery("SELECT loan_id FROM user", null);
                        if (cursorUser != null) {
                            if (cursorUser.moveToNext()) {
                                loanId = cursorUser.getString(cursorUser.getColumnIndex(DbContract.User.COLUMN_NAME_LOAN_ID));
                            }
                            cursorUser.close();
                        }
                        VisaRequest request = new VisaRequest();
                        request.setLoanId(loanId);
                        return request;
                    }
                })
                .flatMap(new Function<VisaRequest, Publisher<Visa>>() {
                    @Override
                    public Publisher<Visa> apply(VisaRequest visaRequest) throws Exception {
                        return Flowable.just(visaRequest)
                                .compose(new SatcatcheResponseTransformer<VisaRequest, Visa>("visa"));
                    }
                });
    }
}
