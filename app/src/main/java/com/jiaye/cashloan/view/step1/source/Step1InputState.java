package com.jiaye.cashloan.view.step1.source;

public class Step1InputState {

    /**
     * step1项目完了标记
     */
    private boolean finishItem0;
    private boolean finishItem1;
    private boolean finishItem2;
    private boolean finishItem3;

    /**
     * GET请求参数
     */
    private String carId;
    private String carYear;
    private String carMonth;
    private String carMiles;
    private String carProvince;
    private String carCity;
    private String carMaxPrice;

    public boolean isFinishItem0() {
        return finishItem0;
    }

    public void setFinishItem0(boolean finishItem0) {
        this.finishItem0 = finishItem0;
    }

    public boolean isFinishItem1() {
        return finishItem1;
    }

    public void setFinishItem1(boolean finishItem1) {
        this.finishItem1 = finishItem1;
    }

    public boolean isFinishItem2() {
        return finishItem2;
    }

    public void setFinishItem2(boolean finishItem2) {
        this.finishItem2 = finishItem2;
    }

    public boolean isFinishItem3() {
        return finishItem3;
    }

    public void setFinishItem3(boolean finishItem3) {
        this.finishItem3 = finishItem3;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarYear() {
        return carYear;
    }

    public void setCarYear(String carYear) {
        this.carYear = carYear;
    }

    public String getCarMonth() {
        return carMonth;
    }

    public void setCarMonth(String carMonth) {
        this.carMonth = carMonth;
    }

    public String getCarMiles() {
        return carMiles;
    }

    public void setCarMiles(String carMiles) {
        this.carMiles = carMiles;
    }

    public String getCarProvince() {
        return carProvince;
    }

    public void setCarProvince(String carProvince) {
        this.carProvince = carProvince;
    }

    public String getCarCity() {
        return carCity;
    }

    public void setCarCity(String carCity) {
        this.carCity = carCity;
    }

    public String getCarMaxPrice() {
        return carMaxPrice;
    }

    public void setCarMaxPrice(String carMaxPrice) {
        this.carMaxPrice = carMaxPrice;
    }
}
