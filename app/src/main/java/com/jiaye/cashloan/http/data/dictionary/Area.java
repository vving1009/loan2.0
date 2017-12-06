package com.jiaye.cashloan.http.data.dictionary;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Area
 *
 * @author 贾博瑄
 */

public class Area implements IPickerViewData {

    @SerializedName("city")
    private List<City> city;

    @SerializedName("name")
    private String name;

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }

    public static class City {

        @SerializedName("name")
        private String name;

        @SerializedName("area")
        private List<String> areas;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getAreas() {
            return areas;
        }

        public void setAreas(List<String> areas) {
            this.areas = areas;
        }
    }
}
