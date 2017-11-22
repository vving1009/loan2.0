package com.jiaye.cashloan.view.view.loan.auth.ocr;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jiaye.cashloan.R;
import com.jph.takephoto.app.TakePhotoAppCompatActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TResult;

import java.io.File;

/**
 * LoanAuthOCRActivity
 *
 * @author 贾博瑄
 */

public class LoanAuthOCRActivity extends TakePhotoAppCompatActivity implements LoanAuthOCRContract.View {

    public static final int REQUEST_OCR = 201;

    private LoanAuthOCRContract.Presenter mPresenter;

    private ImageView mImgFront;

    private ImageView mImgBack;

    private Button mBtnCommit;

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
                mPresenter.pickFront();
            }
        });
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.pickBack();
            }
        });
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.commit();
            }
        });
        mPresenter = new LoanAuthOCRPresenter(this);
        mPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void takeSuccess(TResult result) {
        mPresenter.savePath(result.getImage().getCompressPath());
    }

    @Override
    public void showToast(String string) {

    }

    @Override
    public void showToastById(int resId) {

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void pickFront(String path) {
        File file = new File(getFilesDir(), path);
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

    @Override
    public void pickBack(String path) {
        File file = new File(getFilesDir(), path);
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

    @Override
    public void setFrontDrawable(String path) {
        BitmapDrawable drawable = new BitmapDrawable(getResources(), path);
        mImgFront.setImageDrawable(drawable);
    }

    @Override
    public void setBackDrawable(String path) {
        BitmapDrawable drawable = new BitmapDrawable(getResources(), path);
        mImgBack.setImageDrawable(drawable);
    }

    @Override
    public void setButtonEnable() {
        mBtnCommit.setEnabled(true);
    }

    @Override
    public void result() {
        Intent intent = new Intent();
        intent.putExtra("is_success", true);
        setResult(REQUEST_OCR, intent);
        finish();
    }
}
