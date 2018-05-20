package com.jiaye.cashloan.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.loan.UploadLocation;
import com.jiaye.cashloan.http.data.loan.UploadLocationRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.orhanobut.logger.Logger;

import org.reactivestreams.Publisher;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LocationService extends Service {

    private UploadLocationRequest request = new UploadLocationRequest();
    private Disposable mDisposable;
    LocationManager locationManager;

    public LocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) LoanApplication.getInstance().getSystemService(Context.LOCATION_SERVICE);
        mDisposable = uploadLocation()
                .subscribe(new Consumer<UploadLocation>() {
                    @Override
                    public void accept(UploadLocation uploadLocation) throws Exception {
                        Logger.d("地理位置上传成功");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.d("地理位置上传失败");
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
    }

    private Flowable<UploadLocation> uploadLocation() {
        return Flowable.interval(0, 300, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<Long, UploadLocationRequest>() {
                    @Override
                    public UploadLocationRequest apply(Long aLong) throws Exception {
                        return getLocationRequest();
                    }
                })
                .compose(new SatcatcheResponseTransformer<UploadLocationRequest, UploadLocation>("uploadLocation"))
                .retryWhen(new Function<Flowable<Throwable>, Publisher<Long>>() {
                    @Override
                    public Publisher<Long> apply(Flowable<Throwable> throwableFlowable) throws Exception {
                        return throwableFlowable.flatMap(new Function<Throwable, Publisher<Long>>() {
                            @Override
                            public Publisher<Long> apply(Throwable throwable) throws Exception {
                                return Flowable.timer(300,TimeUnit.SECONDS);
                            }
                        });
                    }
                });
    }

    private UploadLocationRequest getLocationRequest() {
        if (locationManager != null) {
            List<String> providers = locationManager.getProviders(true);
            Location location = null;
            if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                try {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                } catch (SecurityException e) {
                    Logger.d("没有定位权限。");
                    e.printStackTrace();
                }
            }
            if (location != null) {
                Logger.d("location= :" + location.getLatitude() + "," + location.getLongitude());
                request.setLongitude(String.valueOf(location.getLongitude()));
                request.setLatitude(String.valueOf(location.getLatitude()));
            }
        }
        return request;
    }
}
