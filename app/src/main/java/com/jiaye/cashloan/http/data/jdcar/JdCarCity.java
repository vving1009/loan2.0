package com.jiaye.cashloan.http.data.jdcar;

import com.google.gson.annotations.SerializedName;

public class JdCarCity {

    /**
     * id : 348
     * name : 北京市
     */

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
