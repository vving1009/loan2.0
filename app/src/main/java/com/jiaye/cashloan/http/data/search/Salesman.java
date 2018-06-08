package com.jiaye.cashloan.http.data.search;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

import java.util.List;

/**
 * Salesman
 *
 * @author 贾博瑄
 */
public class Salesman extends SatcatcheChildResponse {

    @SerializedName("branch_office")
    private List<Company> list;

    public List<Company> getList() {
        return list;
    }

    public void setList(List<Company> list) {
        this.list = list;
    }

    public static class Company {

        @SerializedName("office_id")
        private String id;

        @SerializedName("office_name")
        private String name;

        @SerializedName("members")
        private List<Employee> list;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Employee> getList() {
            return list;
        }

        public void setList(List<Employee> list) {
            this.list = list;
        }
    }

    public static class Employee {

        @SerializedName("staff_name")
        private String name;

        @SerializedName("staff_number")
        private String number;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
