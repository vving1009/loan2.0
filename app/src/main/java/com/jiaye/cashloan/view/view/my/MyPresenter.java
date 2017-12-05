package com.jiaye.cashloan.view.view.my;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.data.auth.User;
import com.jiaye.cashloan.view.data.my.source.MyDataSource;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import java.io.ByteArrayOutputStream;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.requestUser().subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                mView.showUserInfo(user);
            }
        }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onClickMyCertificate() {
        Disposable disposable = mDataSource.queryUser().subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                mView.showMyCertificateView(user);
            }
        }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void approve() {
        Disposable disposable = mDataSource.queryUser().subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                mView.showApproveView();
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

    private byte[] bmpToByteArray(final Bitmap bmp) {
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
}
