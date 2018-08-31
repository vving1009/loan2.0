package com.jiaye.cashloan.http;

import com.jiaye.cashloan.http.data.car.CarResponse;
import com.jiaye.cashloan.http.data.car.CarBrand;
import com.jiaye.cashloan.http.data.car.CarCity;
import com.jiaye.cashloan.http.data.car.CarSeries;
import com.jiaye.cashloan.http.data.car.CarModel;
import com.jiaye.cashloan.http.data.car.CarPrice;
import com.jiaye.cashloan.http.data.car.CarProvince;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * SatcatcheService
 *
 * @author 贾博瑄
 */
public interface CarService {

    /**
     * 获得全部的品牌列表
     */
    @GET("brand")
    Flowable<CarResponse<CarBrand>> getCarBrand(@Query("key") String key, @Query("vehicle") String vehicle);  //vehicle: passenger(乘用车)/commercial(商用车)

    /**
     * 获得指定品牌的全部的车系
     */
    @GET("series")
    Flowable<CarResponse<CarSeries>> getCarSeries(@Query("key") String key, @Query("brand") String brandId);

    /**
     * 获取指定车系的具体的车型列表
     */
    @GET("car")
    Flowable<CarResponse<CarModel>> getCarModel(@Query("key") String key, @Query("series") String seriesId);

    /**
     * 估值支持的省份的接口
     */
    @GET("province")
    Flowable<CarResponse<List<CarProvince>>> getCarProvince(@Query("key") String key);

    /**
     * 估值支持的城市接口
     */
    @GET("city")
    Flowable<CarResponse<List<CarCity>>> getCarCity(@Query("key") String key, @Query("province") String provinceId);

    /**
     * 购买年份列表
     */
    @GET("year")
    Flowable<CarResponse<List<String>>> getCarSalesYear(@Query("key") String key, @Query("car") String provinceId);

    /**
     * 精准定价接口
     *
     * @param carstatus 车况，较差3，一般2，优秀1
     * @param purpose 车辆用途: 1自用 2公务商用 3营运
     * @return
     */
    @GET("assess")
    Flowable<CarResponse<CarPrice>> getCarPrice(@Query("key") String key, @Query("carstatus") String carstatus,
                                                @Query("purpose") String purpose, @Query("car") String car,
                                                @Query("province") String province, @Query("city") String city,
                                                @Query("useddate") String year, @Query("useddateMonth") String month,
                                                @Query("mileage") String miles);
}
