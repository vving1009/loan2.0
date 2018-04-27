package com.jiaye.cashloan.view.data.home;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.UploadClient;
import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.data.loan.CheckLoan;
import com.jiaye.cashloan.http.data.loan.CheckLoanRequest;
import com.jiaye.cashloan.http.data.loan.DefaultProduct;
import com.jiaye.cashloan.http.data.loan.DefaultProductRequest;
import com.jiaye.cashloan.http.data.loan.LoanAuth;
import com.jiaye.cashloan.http.data.loan.LoanAuthRequest;
import com.jiaye.cashloan.http.data.loan.Upload;
import com.jiaye.cashloan.http.data.loan.UploadAuthRequest;
import com.jiaye.cashloan.http.data.my.User;
import com.jiaye.cashloan.http.tongdun.TongDunOCRBack;
import com.jiaye.cashloan.http.tongdun.TongDunOCRFront;
import com.jiaye.cashloan.http.utils.RequestFunction;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.view.LocalException;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * HomeRepository
 *
 * @author 贾博瑄
 */

public class HomeRepository implements HomeDataSource {

    @Override
    public Flowable<Upload> requestUpload() {
        // 判断是否已经登录
        return queryUser()
                .map(new Function<User, CheckLoanRequest>() {
                    @Override
                    public CheckLoanRequest apply(User user) throws Exception {
                        return new CheckLoanRequest();
                    }
                })
                // 判断是否可以借款
                .compose(new ResponseTransformer<CheckLoanRequest, CheckLoan>("checkLoan"))
                .map(new Function<CheckLoan, DefaultProductRequest>() {
                    @Override
                    public DefaultProductRequest apply(CheckLoan checkLoan) throws Exception {
                        return new DefaultProductRequest();
                    }
                })
                // 请求默认产品
                .compose(new ResponseTransformer<DefaultProductRequest, DefaultProduct>("defaultProduct"))
                .map(new Function<DefaultProduct, LoanAuthRequest>() {
                    @Override
                    public LoanAuthRequest apply(DefaultProduct defaultProduct) throws Exception {
                        // 保存产品信息
                        ContentValues values = new ContentValues();
                        values.put("product_id", defaultProduct.getId());
                        values.put("product_name", defaultProduct.getName());
                        String amount = defaultProduct.getAmount();
                        values.put("amount", amount.substring(0, amount.indexOf(".")));
                        values.put("deadline", defaultProduct.getDeadline());
                        values.put("is_default", 1);
                        LoanApplication.getInstance().getSQLiteDatabase().delete("product", null, null);
                        LoanApplication.getInstance().getSQLiteDatabase().insert("product", null, values);

                        LoanAuthRequest request = new LoanAuthRequest();
                        Cursor cursorUser = LoanApplication.getInstance().getSQLiteDatabase().rawQuery("SELECT phone FROM user", null);
                        if (cursorUser != null) {
                            if (cursorUser.moveToNext()) {
                                String phone = cursorUser.getString(cursorUser.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
                                request.setPhone(phone);
                            }
                            cursorUser.close();
                        }
                        request.setProductId(defaultProduct.getId());
                        return request;
                    }
                })
                // 请求身份证信息
                .compose(new ResponseTransformer<LoanAuthRequest, LoanAuth>("loanAuth"))
                .map(new Function<LoanAuth, UploadAuthRequest>() {
                    @Override
                    public UploadAuthRequest apply(LoanAuth loanAuth) throws Exception {
                        UploadAuthRequest request = new UploadAuthRequest();
                        request.setLoanId(loanAuth.getLoanId());
                        request.setId(loanAuth.getOcrID());
                        request.setName(loanAuth.getOcrName());
                        TongDunOCRFront tongDunOCRFront = new TongDunOCRFront();
                        request.setDataFront(tongDunOCRFront);
                        TongDunOCRBack tongDunOCRBack = new TongDunOCRBack();
                        request.setDataBack(tongDunOCRBack);
                        return request;
                    }
                })
                .map(new RequestFunction<UploadAuthRequest>())
                // 上传身份证信息
                .flatMap(new Function<Request<UploadAuthRequest>, Publisher<Upload>>() {
                    @Override
                    public Publisher<Upload> apply(Request<UploadAuthRequest> request) throws Exception {
                        return UploadClient.INSTANCE.getService().uploadAuth(request);
                    }
                });
    }

    private Flowable<User> queryUser() {
        return Flowable.just("SELECT * FROM user").map(new Function<String, User>() {
            @Override
            public User apply(String sql) throws Exception {
                String token = "";
                String name = "";
                String phone = "";
                Cursor cursor = LoanApplication.getInstance().getSQLiteDatabase().rawQuery(sql, null);
                if (cursor != null) {
                    if (cursor.moveToNext()) {
                        token = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_TOKEN));
                        name = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_NAME));
                        phone = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
                    }
                    cursor.close();
                }
                if (TextUtils.isEmpty(token)) {
                    throw new LocalException(R.string.error_auth_not_log_in);
                } else {
                    User user = new User();
                    user.setToken(token);
                    user.setName(name);
                    user.setPhone(phone);
                    return user;
                }
            }
        });
    }
}
