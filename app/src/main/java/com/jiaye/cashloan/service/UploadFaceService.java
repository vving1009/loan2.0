package com.jiaye.cashloan.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.FaceDetector;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.loan.QueryUploadPhoto;
import com.jiaye.cashloan.http.data.loan.QueryUploadPhotoRequest;
import com.jiaye.cashloan.http.data.vehcile.UploadCarPapersRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.persistence.User;
import com.jiaye.cashloan.utils.DateUtil;
import com.jiaye.cashloan.utils.FileUtils;
import com.jiaye.cashloan.utils.OssManager;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.orhanobut.logger.Logger;

import org.reactivestreams.Publisher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class UploadFaceService extends Service {

    private final String FACE_FOLDER = "facePhoto";

    private Disposable mDisposable;

    private String picName;

    public static void startUploadFaceService(Context context) {
        Intent intent = new Intent(context, UploadFaceService.class);
        context.startService(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        uploadPhoto();
    }

    private void uploadPhoto() {
        User user = LoanApplication.getInstance().getDbHelper().queryUser();
        // 清除照片缓存
        mDisposable = clearPhotoCache()
                .flatMap((Function<Object, Publisher<QueryUploadPhoto>>) o -> {
                    // 查询上传照片数量
                    return queryUploadPhoto();
                })
                // 当发现上传的照片数量大于1时不再上传
                .filter(queryUploadPhoto -> queryUploadPhoto.getCount() < 1)
                // 将照片转化为数据流分别发射
                .flatMap((Function<QueryUploadPhoto, Publisher<String>>) queryUploadPhoto -> Flowable.fromIterable(loadAllPicture()))
                .filter(path -> {
                    Bitmap bmp = Glide.with(UploadFaceService.this)
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
                })
                // 限制最大数量为20
                .take(20)
                // 进行图形压缩
                .map(path -> new Compressor.Builder(UploadFaceService.this)
                        .setMaxWidth(2000)
                        .setMaxHeight(2000)
                        .setQuality(75)
                        .setCompressFormat(Bitmap.CompressFormat.JPEG)
                        .setDestinationDirectoryPath(UploadFaceService.this.getCacheDir() + "/portrait/")
                        .build()
                        .compressToFile(new File(path)))
                //上传到OSS
                .map(file -> {
                    picName = DateUtil.formatDateTimeMillis(System.currentTimeMillis()) + ".jpg";
                    String ossPath = user.getPhone() + "/" + user.getLoanId() + "/" + FACE_FOLDER + "/" + picName;
                    OssManager.getInstance().upload(ossPath, file.getAbsolutePath());
                    return ossPath;
                })
                //把OSS路径传给服务器
                .map(ossPath -> {
                    UploadCarPapersRequest request = new UploadCarPapersRequest();
                    request.setLoanId(user.getLoanId());
                    request.setPicName(picName);
                    request.setPicType("11");
                    request.setPicUrl(BuildConfig.OSS_BASE_URL + ossPath);
                    return request;
                })
                //.compose(new SatcatcheResponseTransformer<UploadCarPapersRequest, UploadCarPapers>("uploadCarPapers"));
                // 将多个流处理为列表
                .toList()
                // 清除照片缓存
                .map((Function<List<UploadCarPapersRequest>, Object>) uploadPhotos -> clearPhotoCache())
                // 定义事件发生时的线程
                .subscribeOn(Schedulers.io())
                // 定义最终订阅时的线程
                .observeOn(Schedulers.io())
                .subscribe(o -> stopSelf(),
                        new ThrowableConsumer() {
                            @Override
                            public void accept(Throwable t) throws Exception {
                                super.accept(t);
                                stopSelf();
                            }
                        });
    }

    private Flowable<Object> clearPhotoCache() {
        return Flowable.just(new Object()).doOnNext(o ->
                FileUtils.deleteFile(new File(LoanApplication.getInstance().getCacheDir() + "/portrait/")));
    }

    private Flowable<QueryUploadPhoto> queryUploadPhoto() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser().getLoanId())
                .map(loanId -> {
                    QueryUploadPhotoRequest request = new QueryUploadPhotoRequest();
                    request.setLoanId(loanId);
                    return request;
                }).compose(new SatcatcheResponseTransformer<QueryUploadPhotoRequest, QueryUploadPhoto>("queryUploadPhoto"));
    }

    private List<String> loadAllPicture() {
        List<String> paths = new ArrayList<>();
        String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.SIZE, MediaStore.Images.Media.DATE_ADDED};
        try {
            Cursor cursor = UploadFaceService.this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
    }
}
