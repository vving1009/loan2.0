package com.jiaye.cashloan.http.data.home;

import com.google.gson.annotations.SerializedName;

/**
 * Product
 *
 * @author 贾博瑄
 */

public class Product {

    /*产品编号*/
    @SerializedName("jpd_id")
    private String id;

    /*产品名称*/
    @SerializedName("jpd_name")
    private String name;

    /*借款金额*/
    @SerializedName("jpd_limitmoney")
    private int amount;

    /*还款期限*/
    @SerializedName("jpd_months")
    private String deadline;

    /*还款方式*/
    @SerializedName("jpd_refundname")
    private String paymentMethod;

    /*是否开放 0关闭 1开放*/
    @SerializedName("jpd_ifstart")
    private String isOpen;

    /*标签资源Id*/
    private int labelResId;

    /*字体颜色*/
    private int color;

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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public int getLabelResId() {
        return labelResId;
    }

    public void setLabelResId(int labelResId) {
        this.labelResId = labelResId;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
