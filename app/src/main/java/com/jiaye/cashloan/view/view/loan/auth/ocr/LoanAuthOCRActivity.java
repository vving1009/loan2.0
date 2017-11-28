package com.jiaye.cashloan.view.view.loan.auth.ocr;

import android.app.ProgressDialog;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.data.loan.auth.ocr.LoanAuthOCRRepository;
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

    private LoanAuthOCRContract.Presenter mPresenter;

    private ProgressDialog mDialog;

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
        mPresenter = new LoanAuthOCRPresenter(this, new LoanAuthOCRRepository());
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
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToastById(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(this);
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
        }
        mDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
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
        finish();
    }
}
