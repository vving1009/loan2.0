package com.jiaye.cashloan.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.jiaye.cashloan.http.data.loan.UploadLocation;
import com.jiaye.cashloan.http.data.loan.UploadLocationRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.orhanobut.logger.Logger;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LocationService extends Service {

    private Disposable mDisposable;
    /**
     * 声明AMapLocationClient类对象
     */

    private UploadLocationRequest request = new UploadLocationRequest();

    public AMapLocationClient mLocationClient = null;

    private OnLocationChangeListener mOnLocationChangeListener;

    public interface OnLocationChangeListener {
        void onChanged(String location);
    }

    public void setOnLocationChangeListener(OnLocationChangeListener listener) {
        mOnLocationChangeListener = listener;
    }

    public LocationService() {
    }

    public class LocationBinder extends Binder {
        public LocationService getService() {
            return LocationService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new LocationBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initAmap();
        mDisposable = uploadLocation()
                .subscribe(uploadLocation ->
                        Logger.d("地理位置上传成功"),
                throwable-> {
                    Logger.d("地理位置上传失败");
                    throwable.printStackTrace();
                    mLocationClient.stopLocation();
                });
    }

    private void initAmap() {
        /**
         * 初始化定位
         */
        mLocationClient = new AMapLocationClient(getApplicationContext());

        /**
         * 初始化AMapLocationClientOption对象
         */
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();

        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）,设置场景后不用再做别的设置
         */
        //mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        /*if(null != mLocationClient){
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }*/
        /**
         * 设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
         */
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        /**
         * 获取一次定位结果：该方法默认为false。
         */
        //mLocationOption.setOnceLocation(true);
        /**
         * 设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
         * 如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
         */
        //mLocationOption.setOnceLocationLatest(true);
        /**
         * 设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
         */
        mLocationOption.setInterval(300000);
        /**
         * 设置是否返回地址信息（默认返回地址信息）
         */
        mLocationOption.setNeedAddress(true);
        /**
         * 设置是否允许模拟位置,默认为true，允许模拟位置
         */
        mLocationOption.setMockEnable(true);
        /**
         * 单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
         */
        mLocationOption.setHttpTimeOut(10000);
        /**
         * 关闭缓存机制
         * mLocationOption.setLocationCacheEnable(false);
         * 给定位客户端对象设置定位参数
         */
        mLocationClient.setLocationOption(mLocationOption);
        /**
         * 启动定位
         */
        mLocationClient.startLocation();
    }

    private Flowable<UploadLocation> uploadLocation() {
        return Flowable.create(new FlowableOnSubscribe<AMapLocation>() {
            @Override
            public void subscribe(FlowableEmitter<AMapLocation> e) throws Exception {
                mLocationClient.setLocationListener(e::onNext);
            }
        }, BackpressureStrategy.DROP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(location -> mOnLocationChangeListener.onChanged(location.getCity()))
                .observeOn(Schedulers.io())
                .map(new Function<AMapLocation, UploadLocationRequest>() {
                    @Override
                    public UploadLocationRequest apply(AMapLocation aMapLocation) throws Exception {
                        request.setLatitude(String.valueOf(aMapLocation.getLatitude()));
                        request.setLongitude(String.valueOf(aMapLocation.getLongitude()));
                        return request;
                    }
                })
                .compose(new SatcatcheResponseTransformer<UploadLocationRequest, UploadLocation>("uploadLocation"))
                .retryWhen(new Function<Flowable<Throwable>, Publisher<Long>>() {
                    @Override
                    public Publisher<Long> apply(Flowable<Throwable> throwableFlowable) throws Exception {
                        return throwableFlowable.flatMap(new Function<Throwable, Publisher<Long>>() {
                            @Override
                            public Publisher<Long> apply(Throwable throwable) throws Exception {
                                return Flowable.timer(300, TimeUnit.SECONDS);
                            }
                        });
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
    }
}
