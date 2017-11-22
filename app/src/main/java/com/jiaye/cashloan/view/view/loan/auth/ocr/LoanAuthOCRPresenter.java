package com.jiaye.cashloan.view.view.loan.auth.ocr;

import android.content.ContentValues;
import android.text.TextUtils;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.tongdun.TongDunClient;
import com.jiaye.cashloan.http.tongdun.TongDunOCRBack;
import com.jiaye.cashloan.http.tongdun.TongDunOCRFront;
import com.jiaye.cashloan.http.tongdun.TongDunResponse;
import com.jiaye.cashloan.http.tongdun.TongDunResponseFunction;
import com.jiaye.cashloan.utils.Base64Util;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;

import org.reactivestreams.Publisher;

import java.io.File;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * LoanAuthOCRPresenter
 *
 * @author 贾博瑄
 */

public class LoanAuthOCRPresenter extends BasePresenterImpl implements LoanAuthOCRContract.Presenter {

    private final LoanAuthOCRContract.View mView;

    private int mState;

    private String mFront;

    private String mBack;

    public LoanAuthOCRPresenter(LoanAuthOCRContract.View view) {
        mView = view;
    }

    @Override
    public void pickFront() {
        if (TextUtils.isEmpty(mFront)) {
            mState = 1;
            mView.pickFront("/card/" + "front.jpg");
        }
    }

    @Override
    public void pickBack() {
        if (TextUtils.isEmpty(mFront)) {
            mState = 2;
            mView.pickBack("/card/" + "back.jpg");
        }
    }

    @Override
    public void savePath(String path) {
        switch (mState) {
            case 1:
                mFront = path;
                mView.setFrontDrawable(mFront);
                break;
            case 2:
                mBack = path;
                mView.setBackDrawable(mBack);
                break;
        }
        if (!TextUtils.isEmpty(mFront) && !TextUtils.isEmpty(mBack)) {
            mView.setButtonEnable();
        }
    }

    @Override
    public void commit() {
        Disposable disposable = Flowable.just(Base64Util.fileToBase64(new File(mFront)))
                .flatMap(new Function<String, Publisher<TongDunResponse<TongDunOCRFront>>>() {
                    @Override
                    public Publisher<TongDunResponse<TongDunOCRFront>> apply(String base64) throws Exception {
                        return TongDunClient.INSTANCE.getService().ocrFront(BuildConfig.TONGDUN_CODE, BuildConfig.TONGDUN_KEY, base64.replace("\n", ""));
                    }
                })
                .map(new TongDunResponseFunction<TongDunOCRFront>())
                .map(new Function<TongDunOCRFront, TongDunOCRFront>() {
                    @Override
                    public TongDunOCRFront apply(TongDunOCRFront tongDunOCRFront) throws Exception {
                        ContentValues values = new ContentValues();
                        values.put("name", tongDunOCRFront.getName());
                        values.put("ocr_id", tongDunOCRFront.getIdNumber());
                        values.put("ocr_name", tongDunOCRFront.getName());
                        values.put("ocr_birthday", tongDunOCRFront.getBirthday());
                        values.put("ocr_gender", tongDunOCRFront.getGender());
                        values.put("ocr_nation", tongDunOCRFront.getNation());
                        values.put("ocr_address", tongDunOCRFront.getAddress());
                        LoanApplication.getInstance().getSQLiteDatabase().update("user", values, null, null);
                        return tongDunOCRFront;
                    }
                })
                .map(new Function<TongDunOCRFront, String>() {
                    @Override
                    public String apply(TongDunOCRFront tongDunOCRFront) throws Exception {
                        return Base64Util.fileToBase64(new File(mBack));
                    }
                })
                .flatMap(new Function<String, Publisher<TongDunResponse<TongDunOCRBack>>>() {
                    @Override
                    public Publisher<TongDunResponse<TongDunOCRBack>> apply(String base64) throws Exception {
                        return TongDunClient.INSTANCE.getService().ocrBack(BuildConfig.TONGDUN_CODE, BuildConfig.TONGDUN_KEY, base64.replace("\n", ""));
                    }
                })
                .map(new TongDunResponseFunction<TongDunOCRBack>())
                .map(new Function<TongDunOCRBack, TongDunOCRBack>() {
                    @Override
                    public TongDunOCRBack apply(TongDunOCRBack tongDunOCRBack) throws Exception {
                        ContentValues values = new ContentValues();
                        values.put("ocr_date_begin", tongDunOCRBack.getDateBegin());
                        values.put("ocr_date_end", tongDunOCRBack.getDateEnd());
                        values.put("ocr_agency", tongDunOCRBack.getAgency());
                        LoanApplication.getInstance().getSQLiteDatabase().update("user", values, null, null);
                        return tongDunOCRBack;
                    }
                })
                // TODO: 2017/11/22 查询数据库生成Request请求网络[服务器尚未提供接口]
                .compose(new ViewTransformer<TongDunOCRBack>())
                .subscribe(new Consumer<TongDunOCRBack>() {
                    @Override
                    public void accept(TongDunOCRBack tongDunOCRBack) throws Exception {
                        mView.result();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
