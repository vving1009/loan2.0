package com.jiaye.cashloan.view.view.my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.auth.Auth;
import com.jiaye.cashloan.http.data.my.User;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.my.source.MyDataSource;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

/**
 * MyPresenter
 *
 * @author 贾博瑄
 */

public class MyPresenter extends BasePresenterImpl implements MyContract.Presenter {

    private final MyContract.View mView;

    private final MyDataSource mDataSource;

    private String mPhone;

    public MyPresenter(MyContract.View view, MyDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void requestUser() {
        Disposable disposable = mDataSource.requestUser()
                .map(new Function<User, User>() {
                    @Override
                    public User apply(User user) throws Exception {
                        if (TextUtils.isEmpty(user.getName()) && TextUtils.isEmpty(user.getPhone())) {/*姓名和手机号码均为空*/
                            user.setShowName("游客");
                        } else {
                            String phone = user.getPhone();
                            String start = phone.substring(0, 3);
                            String end = phone.substring(7, 11);
                            user.setShowName(start + "****" + end);
                        }
                        return user;
                    }
                })
                .compose(new ViewTransformer<User>())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        mView.showUserInfo(user);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onClickMyCertificate() {
        Disposable disposable = mDataSource.requestAuth()
                .compose(new ViewTransformer<Auth>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<Auth>() {
                    @Override
                    public void accept(Auth auth) throws Exception {
                        mView.dismissProgressDialog();
                        mView.showMyCertificateView();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void approve() {
        Disposable disposable = mDataSource.queryUser().subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                if (!TextUtils.isEmpty(user.getApproveNumber()) && !user.getApproveNumber().equals("0")) {
                    mView.showApproveView(user.getLoanApproveId());
                }
            }
        }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void progress() {
        Disposable disposable = mDataSource.queryUser().subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                if (!TextUtils.isEmpty(user.getProgressNumber()) && !user.getProgressNumber().equals("0")) {
                    mView.showProgressView(user.getLoanProgressId());
                }
            }
        }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void history() {
        Disposable disposable = mDataSource.queryUser().subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                if (!TextUtils.isEmpty(user.getHistoryNumber()) && !user.getHistoryNumber().equals("0")) {
                    mView.showHistoryView();
                }
            }
        }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void credit() {
        Disposable disposable = mDataSource.queryUser().subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                mView.showCreditView();
            }
        }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void save() {
        try {
            InputStream is = LoanApplication.getInstance().getAssets().open("QRCode.png");
            saveImageToGallery(BitmapFactory.decodeStream(is));
            mView.showToastById(R.string.my_qrcode_success);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void settings() {
        Disposable disposable = mDataSource.queryUser().subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                mView.showSettingsView();
            }
        }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void share() {
        Disposable disposable = mDataSource.queryUser().subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                mPhone = user.getPhone();
                mView.showShareView();
            }
        }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void shareWeChat() {
        share(SendMessageToWX.Req.WXSceneSession);
    }

    @Override
    public void shareMoments() {
        share(SendMessageToWX.Req.WXSceneTimeline);
    }

    private void share(int type) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = BuildConfig.BASE_SHARE_URL + mPhone;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = LoanApplication.getInstance().getResources().getString(R.string.my_wechat_title);
        msg.description = LoanApplication.getInstance().getResources().getString(R.string.my_wechat_description);
        Bitmap bmp = BitmapFactory.decodeResource(LoanApplication.getInstance().getResources(), R.mipmap.icon);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
        bmp.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "webpage" + System.currentTimeMillis();
        req.message = msg;
        req.scene = type;
        LoanApplication.getInstance().getIWXAPI().sendReq(req);
    }

    private byte[] bmpToByteArray(Bitmap bmp) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        bmp.recycle();
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void saveImageToGallery(Bitmap bmp) {
        String storePath = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getAbsolutePath();
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            appDir.mkdir();
        }
        String fileName = "QRCode.jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();
            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            LoanApplication.getInstance().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
