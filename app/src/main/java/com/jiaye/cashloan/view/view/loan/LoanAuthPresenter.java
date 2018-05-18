package com.jiaye.cashloan.view.view.loan;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.FaceDetector;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.LoanAuth;
import com.jiaye.cashloan.http.data.loan.QueryUploadPhoto;
import com.jiaye.cashloan.http.data.loan.UploadContact;
import com.jiaye.cashloan.http.data.loan.UploadPhoto;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.LoanAuthModel;
import com.jiaye.cashloan.view.data.loan.source.LoanAuthDataSource;
import com.orhanobut.logger.Logger;

import org.reactivestreams.Publisher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * LoanAuthPresenter
 *
 * @author 贾博瑄
 */

public class LoanAuthPresenter extends BasePresenterImpl implements LoanAuthContract.Presenter {

    private final LoanAuthContract.View mView;

    private final LoanAuthDataSource mDataSource;

    private int mStep;

    private Context mContext = LoanApplication.getInstance();

    public LoanAuthPresenter(LoanAuthContract.View view, LoanAuthDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void requestLoanAuth() {
        Disposable disposable = mDataSource.requestLoanAuth()
                .compose(new ViewTransformer<LoanAuth>() {
                    @Override
                    public void accept() {
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<LoanAuth>() {
                    @Override
                    public void accept(LoanAuth loanAuth) throws Exception {
                        List<LoanAuthModel> list = new ArrayList<>();
                        boolean isVerify;

                        LoanAuthModel card = new LoanAuthModel();
                        card.setIcon(R.drawable.loan_auth_ic_card);
                        card.setName(R.string.loan_auth_ocr);
                        setLoanAuthModel(loanAuth.getCardState(), card, false);
                        isVerify = card.isVerify();
                        if (isVerify) {
                            mStep = 1;
                        }
                        list.add(card);

                        LoanAuthModel file = new LoanAuthModel();
                        file.setIcon(R.drawable.loan_auth_ic_file);
                        file.setName(R.string.loan_auth_file);
                        setLoanAuthModel(loanAuth.getFileUploadState(), file, true);
                        isVerify = isVerify && file.isVerify();
                        if (isVerify) {
                            mStep = 2;
                        }
                        list.add(file);

                        LoanAuthModel phone = new LoanAuthModel();
                        phone.setIcon(R.drawable.loan_auth_ic_phone);
                        phone.setName(R.string.loan_auth_phone);
                        setLoanAuthModel(loanAuth.getPhoneState(), phone, false);
                        list.add(phone);

                        LoanAuthModel face = new LoanAuthModel();
                        face.setIcon(R.drawable.loan_auth_ic_face);
                        face.setName(R.string.loan_auth_face);
                        setLoanAuthModel(loanAuth.getFaceState(), face, false);
                        isVerify = isVerify && face.isVerify();
                        if (isVerify) {
                            mStep = 3;
                        }
                        list.add(face);

                        LoanAuthModel person = new LoanAuthModel();
                        person.setIcon(R.drawable.loan_auth_ic_person);
                        person.setName(R.string.loan_auth_info);
                        setLoanAuthModel(loanAuth.getPersonState(), person, true);
                        isVerify = isVerify && person.isVerify();
                        if (isVerify) {
                            mStep = 4;
                        }
                        list.add(person);

                        LoanAuthModel visa = new LoanAuthModel();
                        visa.setIcon(R.drawable.loan_auth_ic_visa);
                        visa.setName(R.string.loan_auth_visa);
                        setLoanAuthModel(loanAuth.getSignState(), visa, false);
                        isVerify = isVerify && visa.isVerify();
                        if (isVerify) {
                            mStep = 5;
                        }
                        list.add(visa);

                        LoanAuthModel taobao = new LoanAuthModel();
                        taobao.setIcon(R.drawable.loan_auth_ic_taobao);
                        taobao.setName(R.string.loan_auth_taobao);
                        setLoanAuthModel(loanAuth.getTaobaoState(), taobao, false);
                        list.add(taobao);

                        mView.setList(list);
                        if (isVerify) {
                            mView.setNextEnable();
                        }
                        mView.dismissProgressDialog();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void selectLoanAuthModel(LoanAuthModel model) {
        /*认证失败*/
        if (model.isFailure()) {
            mView.showToastById(R.string.error_loan_auth_card_failure);
            return;
        }
        if (!model.isVerify() || model.isCanModify()) {
            switch (model.getName()) {
                case R.string.loan_auth_ocr:
                    mView.showLoanAuthOCRView();
                    break;
                case R.string.loan_auth_file:
                    if (mStep < 1) {
                        mView.showToastById(R.string.error_loan_auth_step);
                    } else {
                        mView.showLoanFileView();
                    }
                    break;
                case R.string.loan_auth_phone:
                    if (mStep < 2) {
                        mView.showToastById(R.string.error_loan_auth_step);
                    } else {
                        mView.showLoanAuthPhoneView();
                    }
                    break;
                case R.string.loan_auth_face:
                    if (mStep < 2) {
                        mView.showToastById(R.string.error_loan_auth_step);
                    } else {
                        mView.showLoanAuthFaceView();
                    }
                    break;
                case R.string.loan_auth_info:
                    if (mStep < 3) {
                        mView.showToastById(R.string.error_loan_auth_step);
                    } else {
                        mView.showLoanAuthInfoView();
                    }
                    break;
                case R.string.loan_auth_visa:
                    if (mStep < 4) {
                        mView.showToastById(R.string.error_loan_auth_step);
                    } else {
                        mView.showLoanAuthVisaView();
                    }
                    break;
                case R.string.loan_auth_visa_history:
                    mView.showLoanAuthVisaHistoryView();
                    break;
                case R.string.loan_auth_taobao:
                    if (mStep < 5) {
                        mView.showToastById(R.string.error_loan_auth_step);
                    } else {
                        mView.showLoanAuthTaoBaoView();
                    }
                    break;
            }
        }
    }

    @Override
    public void uploadContact() {
        Disposable disposable = mDataSource.uploadContact()
                .compose(new ViewTransformer<UploadContact>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<UploadContact>() {
                    @Override
                    public void accept(UploadContact uploadContact) throws Exception {
                        Logger.d("通讯录上传成功");
                        mView.dismissProgressDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissProgressDialog();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void confirm() {
        Disposable disposable = mDataSource.requestLoanConfirm()
                .compose(new ViewTransformer<String>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String loanId) throws Exception {
                        mView.dismissProgressDialog();
                        mView.exitView();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    private void setLoanAuthModel(String state, LoanAuthModel model, boolean canModify) {
        if (TextUtils.isEmpty(state) || state.equals("0")) {
            model.setIcState(R.drawable.ic_angle_blue);
            model.setIcBackground(R.drawable.loan_auth_ic_bg_gray);
            model.setColor(R.color.color_gray);
            model.setBackground(R.drawable.loan_auth_bg_gray);
            model.setVerify(false);
            model.setCanModify(true);
        } else if (state.equals("1")) {
            if (canModify) {
                model.setIcState(R.drawable.ic_angle_blue);
            } else {
                model.setIcState(R.drawable.loan_auth_ic_pass);
            }
            model.setIcBackground(R.drawable.loan_auth_ic_bg_blue);
            model.setColor(R.color.color_blue);
            model.setBackground(R.drawable.loan_auth_bg_blue);
            model.setVerify(true);
            model.setCanModify(canModify);
        } else if (state.equals("2")) {
            model.setIcState(R.drawable.ic_angle_blue);
            model.setIcBackground(R.drawable.loan_auth_ic_bg_gray);
            model.setColor(R.color.color_gray);
            model.setBackground(R.drawable.loan_auth_bg_gray);
            model.setVerify(false);
            model.setCanModify(true);
        }
    }

    @Override
    public void uploadPhoto() {
        // 清除照片缓存
        Disposable disposable = mDataSource.clearPhotoCache()
                .flatMap(new Function<Object, Publisher<QueryUploadPhoto>>() {
                    @Override
                    public Publisher<QueryUploadPhoto> apply(Object o) throws Exception {
                        // 查询上传照片数量
                        return mDataSource.queryUploadPhoto();
                    }
                })
                .filter(new Predicate<QueryUploadPhoto>() {
                    @Override
                    public boolean test(QueryUploadPhoto queryUploadPhoto) throws Exception {
                        // 当发现上传的照片数量大于1时不再上传
                        return queryUploadPhoto.getCount() < 1;
                    }
                })
                .flatMap(new Function<QueryUploadPhoto, Publisher<String>>() {
                    @Override
                    public Publisher<String> apply(QueryUploadPhoto queryUploadPhoto) throws Exception {
                        // 将照片转化为数据流分别发射
                        return Flowable.fromIterable(loadAllPicture());
                    }
                })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String path) throws Exception {
                        // 过滤没有人像的照片
                        Bitmap bmp = Glide.with(mContext)
                                .load(path)
                                .asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .fitCenter()
                                .into(1500, 1500)
                                .get();
                        FaceDetector faceDetector = new FaceDetector(bmp.getWidth(), bmp.getHeight(), 5);
                        FaceDetector.Face[] faces = new FaceDetector.Face[5];
                        int realFaceNum = faceDetector.findFaces(bmp, faces);
                        bmp.recycle();
                        return realFaceNum > 0;
                    }
                })
                // 限制最大数量为20
                .take(20)
                .map(new Function<String, File>() {
                    @Override
                    public File apply(String path) throws Exception {
                        // 进行图形压缩
                        return new Compressor.Builder(mContext)
                                .setMaxWidth(2000)
                                .setMaxHeight(2000)
                                .setQuality(75)
                                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                                .setDestinationDirectoryPath(mContext.getCacheDir() + "/portrait/")
                                .build()
                                .compressToFile(new File(path));
                    }
                })
                .concatMap(new Function<File, Publisher<UploadPhoto>>() {
                    @Override
                    public Publisher<UploadPhoto> apply(File file) throws Exception {
                        // 依次上传服务器
                        return mDataSource.uploadPhoto(file);
                    }
                })
                // 将多个流处理为列表
                .toList()
                .map(new Function<List<UploadPhoto>, Object>() {
                    @Override
                    public Object apply(List<UploadPhoto> uploadPhotos) throws Exception {
                        // 清除照片缓存
                        return mDataSource.clearPhotoCache();
                    }
                })
                // 定义事件发生时的线程
                .subscribeOn(Schedulers.io())
                // 定义最终订阅时的线程
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    private List<String> loadAllPicture() {
        List<String> paths = new ArrayList<>();
        String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.SIZE, MediaStore.Images.Media.DATE_ADDED};
        try {
            Cursor cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    columns, null, null, MediaStore.Images.Media.DATE_ADDED + " DESC");
            int fileColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            while (cursor.moveToNext()) {
                if (cursor.getInt(sizeColumn) < 100000) continue;  //不使用小于100K的图片
                String imageFilePath = cursor.getString(fileColumn);
                BitmapFactory.decodeFile(imageFilePath, options);
                int maxLength = Math.max(options.outWidth, options.outHeight);
                if (maxLength >= 900 && maxLength < 10000) {
                    paths.add(imageFilePath);
                }
            }
            cursor.close();
        } catch (SecurityException e) {
            Logger.d("Permission Denial : READ_EXTERNAL_STORAGE");
            e.printStackTrace();
        }
        return paths;
    }
}
