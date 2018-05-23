package com.jiaye.cashloan.view.view.loan.auth.ocr;

import android.app.ProgressDialog;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.data.loan.auth.source.ocr.LoanAuthOCRRepository;
import com.jiaye.cashloan.view.view.help.LoanAuthHelpActivity;
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

    private EditText mEditName;

    private TextView mTextId;

    private TextView mTextDate;

    private ImageView mImgFront;

    private ImageView mImgBack;

    private Button mBtnCheck;

    private Button mBtnCommit;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_auth_ocr_activity);
        mEditName = findViewById(R.id.edit_name);
        mTextId = findViewById(R.id.text_id);
        mTextDate = findViewById(R.id.text_date);
        mBtnCheck = findViewById(R.id.btn_check);
        mBtnCommit = findViewById(R.id.btn_commit);
        mImgFront = findViewById(R.id.img_card_front);
        mImgBack = findViewById(R.id.img_card_back);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.img_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoanAuthHelpActivity.show(LoanAuthOCRActivity.this, R.string.loan_auth_ocr, "cardAuth/identityAuth");
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
        mBtnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.check();
            }
        });
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.submit();
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
        File file = new File(Environment.getExternalStorageDirectory(), path);
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
        File file = new File(Environment.getExternalStorageDirectory(), path);
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
    public void setCheckEnable() {
        mBtnCheck.setEnabled(true);
    }

    @Override
    public void showInfo(String name, String id, String date) {
        mEditName.setText(name);
        mTextId.setText(id);
        mTextDate.setText(date);
    }

    @Override
    public void setSubmitEnable() {
        mBtnCommit.setEnabled(true);
    }

    @Override
    public String getName() {
        return mEditName.getText().toString();
    }

    @Override
    public String getIdCard() {
        return mTextId.getText().toString();
    }

    @Override
    public String getIdCardDate() {
        return mTextDate.getText().toString();
    }

    @Override
    public void result() {
        finish();
    }
}
