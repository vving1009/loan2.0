package com.jiaye.cashloan.http.data.home;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * BannerList
 *
 * @author 贾博瑄
 */
public class BannerList extends SatcatcheChildResponse {

    @SerializedName("carousel_picture")
    private Banner[] bannerArray;

    public Banner[] getBannerArray() {
        return bannerArray;
    }

    public void setBannerArray(Banner[] bannerArray) {
        this.bannerArray = bannerArray;
    }

    public class Banner {

        /**
         * 图片地址
         */
        @SerializedName("picture_url")
        private String imgUrl;

        /**
         * 商品链接
         */
        @SerializedName("commodity_url")
        private String url;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
