package com.jiaye.cashloan.view.taobao.source;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.taobao.UpdateTaoBaoRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;

import io.reactivex.Flowable;

/**
 * TaoBaoRepository
 *
 * @author 贾博瑄
 */

public class TaoBaoRepository implements TaoBaoDataSource {

    @Override
    public Flowable<EmptyResponse> requestTaoBao() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    UpdateTaoBaoRequest request = new UpdateTaoBaoRequest();
                    request.setToken(user.getToken());
                    request.setLoanId(user.getLoanId());
                    return request;
                }).compose(new SatcatcheResponseTransformer<UpdateTaoBaoRequest, EmptyResponse>("updateTaoBao"));
    }
}
