package com.jiaye.cashloan.view.step3.source;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.amount.AmountMoney;
import com.jiaye.cashloan.http.data.amount.AmountMoneyRequest;
import com.jiaye.cashloan.http.data.certification.Step;
import com.jiaye.cashloan.http.data.certification.StepRequest;
import com.jiaye.cashloan.http.data.certification.UpdateStepRequest;
import com.jiaye.cashloan.http.data.home.CheckCompany;
import com.jiaye.cashloan.http.data.home.CheckCompanyRequest;
import com.jiaye.cashloan.http.data.loan.LoanVisaSign;
import com.jiaye.cashloan.http.data.loan.LoanVisaSignRequest;
import com.jiaye.cashloan.http.data.loan.Visa;
import com.jiaye.cashloan.http.data.loan.VisaRequest;
import com.jiaye.cashloan.http.data.my.CreditInfo;
import com.jiaye.cashloan.http.data.my.CreditInfoRequest;
import com.jiaye.cashloan.http.data.search.Salesman;
import com.jiaye.cashloan.http.data.search.SalesmanRequest;
import com.jiaye.cashloan.http.data.search.SaveSalesmanRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * Step3Repository
 *
 * @author 贾博瑄
 */

public class Step3Repository implements Step3DataSource {

    @Override
    public Flowable<EmptyResponse> requestUpdateStep(int step, String msg) {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    UpdateStepRequest request = new UpdateStepRequest();
                    request.setLoanId(user.getLoanId());
                    request.setStatus(step);
                    request.setMsg(msg);
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<UpdateStepRequest, EmptyResponse>("updateStep"));
    }

    /**
     * 下载业务员列表并保存在数据库中
     *
     * @return
     */
    @Override
    public Flowable<Salesman> salesman() {
        return Flowable.just(new SalesmanRequest())
                .compose(new SatcatcheResponseTransformer<SalesmanRequest, Salesman>("salesman"))
                .map(search -> {
                    LoanApplication.getInstance().getDbHelper().deleteSales();
                    LoanApplication.getInstance().getDbHelper().insertSales(search.getList());
                    return search;
                });
    }

    /**
     * 保存用户选择的业务员
     *
     * @param request
     * @return
     */
    @Override
    public Flowable<EmptyResponse> saveSalesman(SaveSalesmanRequest request) {
        return Flowable.just(request)
                .doOnNext(request1 -> {
                    String loadId = LoanApplication.getInstance().getDbHelper().queryUser().getLoanId();
                    request1.setLoanId(loadId);
                })
                .compose(new SatcatcheResponseTransformer<SaveSalesmanRequest, EmptyResponse>
                        ("saveSalesman"));
    }

    @Override
    public Flowable<Step> requestStep() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    StepRequest request = new StepRequest();
                    request.setLoanId(user.getLoanId());
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<StepRequest, Step>("step"));
    }

    @Override
    public Flowable<AmountMoney> requestAmountMoney() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    AmountMoneyRequest request = new AmountMoneyRequest();
                    request.setLoanId(user.getLoanId());
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<AmountMoneyRequest, AmountMoney>("queryAmountMoney"));
    }

    /**
     * 查询是否开户
     *
     * @return
     */
    @Override
    public Flowable<CreditInfo> creditInfo() {
        return Flowable.just(new CreditInfoRequest())
                .compose(new ResponseTransformer<CreditInfoRequest, CreditInfo>("creditInfo"));
    }

    /**
     * 电子签章
     *
     * @return
     */
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

    /**
     * 检查业务员是否已经上传
     *
     * @return
     */
    @Override
    public Flowable<CheckCompany> checkCompany() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    CheckCompanyRequest request = new CheckCompanyRequest();
                    request.setLoanId(user.getLoanId());
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<CheckCompanyRequest, CheckCompany>("checkCompany"));

    }
}
