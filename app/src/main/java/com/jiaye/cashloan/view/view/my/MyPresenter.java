package com.jiaye.cashloan.view.view.my;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.my.User;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.my.source.MyDataSource;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import java.io.ByteArrayOutputStream;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

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
                        } else if (TextUtils.isEmpty(user.getName())) {/*姓名为空手机号不为空*/
                            String phone = user.getPhone();
                            String start = phone.substring(0, 3);
                            String end = phone.substring(7, 11);
                            user.setShowName(start + "****" + end);
                        } else {/*姓名不为空*/
                            String name = user.getName();
                            int l = name.length();
                            if (l == 1) {
                                user.setShowName(name);
                            } else {
                                StringBuilder s = new StringBuilder();
                                for (int i = 0; i < l - 1; i++) {
                                    s.append("*");
                                }
                                user.setShowName(s + name.substring(s.length()));
                            }
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
