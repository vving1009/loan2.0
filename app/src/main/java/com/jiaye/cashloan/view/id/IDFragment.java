package com.jiaye.cashloan.view.id;

import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.PhotoFunctionFragment;
import com.jiaye.cashloan.view.id.source.IDRepository;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;

import java.io.File;

/**
 * IDFragment
 *
 * @author 贾博瑄
 */

public class IDFragment extends PhotoFunctionFragment implements IDContract.View, TakePhoto.TakeResultListener, InvokeListener {

    private IDContract.Presenter mPresenter;

    private EditText mEditName;

    private TextView mTextId;

    private TextView mTextDate;

    private ImageView mImgFront;

    private ImageView mImgBack;

    private Button mBtnCheck;

    private Button mBtnCommit;

    public static IDFragment newInstance() {
        Bundle args = new Bundle();
        IDFragment fragment = new IDFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getTitleId() {
        return R.string.id_title;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View view = inflater.inflate(R.layout.id_fragment, frameLayout, true);
        mEditName = view.findViewById(R.id.edit_name);
        mTextId = view.findViewById(R.id.text_id);
        mTextDate = view.findViewById(R.id.text_date);
        mBtnCheck = view.findViewById(R.id.btn_check);
        mBtnCommit = view.findViewById(R.id.btn_commit);
        mImgFront = view.findViewById(R.id.img_front);
        mImgBack = view.findViewById(R.id.img_back);
        mImgFront.setOnClickListener(v -> mPresenter.pickFront());
        mImgBack.setOnClickListener(v -> mPresenter.pickBack());
        mBtnCheck.setOnClickListener(v -> mPresenter.check());
        mBtnCommit.setOnClickListener(v -> mPresenter.submit());
        mPresenter = new IDPresenter(this, new IDRepository());
        mPresenter.subscribe();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void takeSuccess(TResult result) {
        mPresenter.savePath(result.getImage().getCompressPath());
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

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
        getActivity().finish();
    }
}
