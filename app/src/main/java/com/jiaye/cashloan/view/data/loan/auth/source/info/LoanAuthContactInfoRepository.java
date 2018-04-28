package com.jiaye.cashloan.view.data.loan.auth.source.info;

import com.jiaye.cashloan.http.UploadClient;
import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.data.loan.Contact;
import com.jiaye.cashloan.http.data.loan.ContactRequest;
import com.jiaye.cashloan.http.data.loan.SaveContact;
import com.jiaye.cashloan.http.data.loan.SaveContactRequest;
import com.jiaye.cashloan.http.data.loan.Upload;
import com.jiaye.cashloan.http.data.loan.UploadLinkmanRequest;
import com.jiaye.cashloan.http.utils.RequestFunction;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * LoanAuthContactInfoRepository
 *
 * @author 贾博瑄
 */

public class LoanAuthContactInfoRepository implements LoanAuthContactInfoDataSource {

    private SaveContactRequest mRequest;

    @Override
    public Flowable<Contact> requestContact() {
        return Flowable.just(new ContactRequest())
                .compose(new SatcatcheResponseTransformer<ContactRequest, Contact>("contact"));
    }

    @Override
    public Flowable<SaveContact> requestSaveContact(SaveContactRequest request) {
        mRequest = request;
        return Flowable.just(request)
                .map(new Function<SaveContactRequest, UploadLinkmanRequest>() {
                    @Override
                    public UploadLinkmanRequest apply(SaveContactRequest saveContactRequest) throws Exception {
                        UploadLinkmanRequest linkmanRequest = new UploadLinkmanRequest();
                        linkmanRequest.setData(saveContactRequest.getData());
                        return linkmanRequest;
                    }
                })
                .map(new RequestFunction<UploadLinkmanRequest>())
                .flatMap(new Function<Request<UploadLinkmanRequest>, Publisher<Upload>>() {
                    @Override
                    public Publisher<Upload> apply(Request<UploadLinkmanRequest> saveContactRequest) throws Exception {
                        return UploadClient.INSTANCE.getService().uploadLinkman(saveContactRequest);
                    }
                })
                .map(new Function<Upload, SaveContactRequest>() {
                    @Override
                    public SaveContactRequest apply(Upload upload) throws Exception {
                        return mRequest;
                    }
                })
                .compose(new SatcatcheResponseTransformer<SaveContactRequest, SaveContact>("saveContact"));
    }
}
