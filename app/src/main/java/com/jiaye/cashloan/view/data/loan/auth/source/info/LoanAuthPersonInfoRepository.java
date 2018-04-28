package com.jiaye.cashloan.view.data.loan.auth.source.info;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.UploadClient;
import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.data.loan.Person;
import com.jiaye.cashloan.http.data.loan.PersonRequest;
import com.jiaye.cashloan.http.data.loan.SavePerson;
import com.jiaye.cashloan.http.data.loan.SavePersonRequest;
import com.jiaye.cashloan.http.data.loan.Upload;
import com.jiaye.cashloan.http.data.loan.UploadPersonalRequest;
import com.jiaye.cashloan.http.utils.RequestFunction;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * LoanAuthPersonInfoRepository
 *
 * @author 贾博瑄
 */

public class LoanAuthPersonInfoRepository implements LoanAuthPersonInfoDataSource {

    private SavePersonRequest mRequest;

    @Override
    public Flowable<Person> requestPerson() {
        return Flowable.just("SELECT phone FROM user;")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String sql) throws Exception {
                        return getString(sql);
                    }
                })
                .map(new Function<String, PersonRequest>() {
                    @Override
                    public PersonRequest apply(String phone) throws Exception {
                        PersonRequest request = new PersonRequest();
                        request.setPhone(phone);
                        return request;
                    }
                })
                .compose(new SatcatcheResponseTransformer<PersonRequest, Person>("person"));
    }

    @Override
    public Flowable<SavePerson> requestSavePerson(SavePersonRequest request) {
        mRequest = request;
        return Flowable.just("SELECT phone FROM user;")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String sql) throws Exception {
                        return getString(sql);
                    }
                })
                .map(new Function<String, SavePersonRequest>() {
                    @Override
                    public SavePersonRequest apply(String phone) throws Exception {
                        mRequest.setPhone(phone);
                        return mRequest;
                    }
                })
                .map(new Function<SavePersonRequest, UploadPersonalRequest>() {
                    @Override
                    public UploadPersonalRequest apply(SavePersonRequest savePersonRequest) throws Exception {
                        UploadPersonalRequest uploadPersonalRequest = new UploadPersonalRequest();
                        uploadPersonalRequest.setAddress(savePersonRequest.getAddress());
                        uploadPersonalRequest.setCity(savePersonRequest.getCity());
                        uploadPersonalRequest.setEducation(savePersonRequest.getEducation());
                        uploadPersonalRequest.setEmail(savePersonRequest.getEmail());
                        uploadPersonalRequest.setMarriage(savePersonRequest.getMarriage());
                        uploadPersonalRequest.setPhone(savePersonRequest.getPhone());
                        uploadPersonalRequest.setRegisterCity(savePersonRequest.getRegisterCity());
                        return uploadPersonalRequest;
                    }
                })
                .map(new RequestFunction<UploadPersonalRequest>())
                .flatMap(new Function<Request<UploadPersonalRequest>, Publisher<Upload>>() {
                    @Override
                    public Publisher<Upload> apply(Request<UploadPersonalRequest> savePersonRequest) throws Exception {
                        return UploadClient.INSTANCE.getService().uploadPersonal(savePersonRequest);
                    }
                })
                .map(new Function<Upload, SavePersonRequest>() {
                    @Override
                    public SavePersonRequest apply(Upload upload) throws Exception {
                        return mRequest;
                    }
                })
                .compose(new SatcatcheResponseTransformer<SavePersonRequest, SavePerson>("savePerson"));
    }

    private String getString(String sql) {
        String phone = "";
        SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                phone = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
            }
            cursor.close();
        }
        return phone;
    }
}
