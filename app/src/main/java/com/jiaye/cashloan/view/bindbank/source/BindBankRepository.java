package com.jiaye.cashloan.view.bindbank.source;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.bindbank.SaveBankCardRequest;
import com.jiaye.cashloan.http.data.loan.LoanBindBank;
import com.jiaye.cashloan.http.data.loan.LoanBindBankRequest;
import com.jiaye.cashloan.http.data.loan.LoanOpenSMS;
import com.jiaye.cashloan.http.data.loan.LoanOpenSMSRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * BindBankRepository
 *
 * @author 贾博瑄
 */

public class BindBankRepository implements BindBankDataSource {

    private String mCode;

    private LoanBindBankRequest mRequest;

    @Override
    public Flowable<String> queryName() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser().getName());
    }

    @Override
    public Flowable<LoanOpenSMS> requestBindBankSMS(LoanOpenSMSRequest request) {
        return Flowable.just(request).flatMap((Function<LoanOpenSMSRequest, Publisher<LoanOpenSMS>>) request1 -> Flowable.just(request1).compose(new ResponseTransformer<LoanOpenSMSRequest, LoanOpenSMS>("loanOpenSMS"))).map(loanBindBankSMS -> {
            mCode = loanBindBankSMS.getCode();
            return loanBindBankSMS;
        });
    }

    @Override
    public Flowable<EmptyResponse> requestBindBank(LoanBindBankRequest request) {
        request.setCode(mCode);
        mRequest = request;
        return Flowable.just(request)
                .compose(new ResponseTransformer<LoanBindBankRequest, LoanBindBank>("loanBindBank"))
                .flatMap((Function<LoanBindBank, Publisher<EmptyResponse>>) loanBindBank -> saveBankCard());
    }

    @Override
    public Flowable<EmptyResponse> requestBindBankAgain(LoanBindBankRequest request) {
        request.setCode(mCode);
        mRequest = request;
        return Flowable.just(request)
                .compose(new ResponseTransformer<LoanBindBankRequest, LoanBindBank>("bindBankAgain"))
                .flatMap((Function<LoanBindBank, Publisher<EmptyResponse>>) loanBindBank -> saveBankCard());
    }

    private Flowable<EmptyResponse> saveBankCard() {
        SaveBankCardRequest request = new SaveBankCardRequest();
        request.setName(mRequest.getBank());
        request.setNumber(mRequest.getNumber());
        request.setPhone(mRequest.getPhone());
        return Flowable.just(request)
                .compose(new SatcatcheResponseTransformer<SaveBankCardRequest, EmptyResponse>("saveBankCard"));
    }
}
