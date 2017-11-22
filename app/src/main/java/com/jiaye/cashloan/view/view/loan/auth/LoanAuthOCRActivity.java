package com.jiaye.cashloan.view.view.loan.auth;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.tongdun.TongDunClient;
import com.jiaye.cashloan.http.tongdun.TongDunOCRBack;
import com.jiaye.cashloan.http.tongdun.TongDunOCRFront;
import com.jiaye.cashloan.http.tongdun.TongDunResponse;
import com.jiaye.cashloan.http.tongdun.TongDunResponseFunction;
import com.jiaye.cashloan.utils.Base64Util;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jph.takephoto.app.TakePhotoAppCompatActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TResult;
import com.orhanobut.logger.Logger;

import org.reactivestreams.Publisher;

import java.io.File;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * LoanAuthOCRActivity
 *
 * @author 贾博瑄
 */

public class LoanAuthOCRActivity extends TakePhotoAppCompatActivity {

    public static final int REQUEST_OCR = 201;

    private ImageView mImgFront;

    private ImageView mImgBack;

    private Button mBtnCommit;

    private int mState;

    private String mFront;

    private String mBack;

    private Disposable mDisposable;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_auth_ocr_activity);
        mBtnCommit = findViewById(R.id.btn_commit);
        mImgFront = findViewById(R.id.img_card_front);
        mImgBack = findViewById(R.id.img_card_back);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mImgFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mFront)) {
                    mState = 1;
                    File file = new File(getFilesDir(), "/card/" + "front.jpg");
                    if (!file.getParentFile().exists()) {
                        //noinspection ResultOfMethodCallIgnored
                        file.getParentFile().mkdirs();
                    }
                    Uri imageUri = Uri.fromFile(file);
                    CompressConfig compressConfig = CompressConfig.ofDefaultConfig();
                    compressConfig.enableReserveRaw(false);
                    getTakePhoto().onEnableCompress(compressConfig, false);
                    getTakePhoto().onPickFromCapture(imageUri);
                }
            }
        });
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mBack)) {
                    mState = 2;
                    File file = new File(getFilesDir(), "/card/" + "back.jpg");
                    if (!file.getParentFile().exists()) {
                        //noinspection ResultOfMethodCallIgnored
                        file.getParentFile().mkdirs();
                    }
                    Uri imageUri = Uri.fromFile(file);
                    CompressConfig compressConfig = CompressConfig.ofDefaultConfig();
                    compressConfig.enableReserveRaw(false);
                    getTakePhoto().onEnableCompress(compressConfig, false);
                    getTakePhoto().onPickFromCapture(imageUri);
                }
            }
        });
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        Logger.d(result.getImage().getCompressPath());
        BitmapDrawable drawable = new BitmapDrawable(getResources(), result.getImage().getCompressPath());
        switch (mState) {
            case 1:
                mFront = result.getImage().getCompressPath();
                mImgFront.setImageDrawable(drawable);
                break;
            case 2:
                mBack = result.getImage().getCompressPath();
                mImgBack.setImageDrawable(drawable);
                break;
        }
        if (!TextUtils.isEmpty(mFront) && !TextUtils.isEmpty(mBack)) {
            mBtnCommit.setEnabled(true);
        }
    }

    private void commit() {
        mDisposable = Flowable.just(Base64Util.fileToBase64(new File(mFront)))
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
                .compose(new ViewTransformer<TongDunOCRBack>())
                .subscribe(new Consumer<TongDunOCRBack>() {
                    @Override
                    public void accept(TongDunOCRBack tongDunOCRBack) throws Exception {
                        result();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(LoanAuthOCRActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void result() {
        Intent intent = new Intent();
        intent.putExtra("is_success", true);
        setResult(REQUEST_OCR, intent);
        finish();
    }
}
