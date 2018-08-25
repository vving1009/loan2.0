package com.jiaye.cashloan.http;

import com.jiaye.cashloan.http.data.jdcar.JdResponse;
import com.jiaye.cashloan.http.data.jdcar.JdCarBrand;
import com.jiaye.cashloan.http.data.jdcar.JdCarCity;
import com.jiaye.cashloan.http.data.jdcar.JdCarFamily;
import com.jiaye.cashloan.http.data.jdcar.JdCarModel;
import com.jiaye.cashloan.http.data.jdcar.JdCarPrice;
import com.jiaye.cashloan.http.data.jdcar.JdCarProvince;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * SatcatcheService
 *
 * @author 贾博瑄
 */
public interface JdService {

    /**
     * 获得全部的品牌列表
     */
    @GET("PredmpAllBrands")
    Flowable<JdResponse<JdCarBrand>> getCarBrand(@Query("appkey") String key);

    /**
     * 获得指定品牌的全部的车系
     */
    @GET("predmpfamilyInfo")
    Flowable<JdResponse<JdCarFamily>> getCarFamily(@Query("appkey") String key, @Query("brandid") String brandId);

    /**
     * 获取指定车系的具体的车型列表
     */
    @GET("predmpdetailVehicleInfo")
    Flowable<JdResponse<JdCarModel>> getCarModel(@Query("appkey") String key, @Query("familyid") String familyId);

    /**
     * 估值支持的省份的接口
     */
    @GET("predmpAllProv")
    Flowable<JdResponse<JdCarProvince>> getCarProvince(@Query("appkey") String key);

    /**
     * 估值支持的城市接口
     */
    @GET("predmpCities")
    Flowable<JdResponse<JdCarCity>> getCarCity(@Query("appkey") String key, @Query("upid") String provinceId);

    /**
     * 精准定价接口
     */
    @GET("preaccuratelyEvaluate")
    Flowable<JdResponse<JdCarPrice>> getCarPrice(@Query("appkey") String key, @Query("unicdatakey") String unicdatakey,
                                                 @Query("province") String province, @Query("city") String city,
                                                 @Query("regdate") String regdate, @Query("miles") String miles,
                                                 @Query("inner") String inner, @Query("outer") String outer,
                                                 @Query("grade") String grade, @Query("accident") String accident);
}
