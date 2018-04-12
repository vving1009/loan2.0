package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

import java.util.List;

/**
 * FileState
 *
 * @author 贾博瑄
 */
public class FileState extends SatcatcheChildResponse {

    @SerializedName("pic_info_list")
    private List<Data> list;

    public List<Data> getList() {
        return list;
    }

    public void setList(List<Data> list) {
        this.list = list;
    }

    public class Data {

        @SerializedName("pic_name")
        private String name;

        @SerializedName("pic_type")
        private int type;

        @SerializedName("pic_count")
        private int count;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
