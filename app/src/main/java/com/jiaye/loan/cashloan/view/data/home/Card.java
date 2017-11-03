package com.jiaye.loan.cashloan.view.data.home;

import android.text.SpannableString;

/**
 * Card
 *
 * @author 贾博瑄
 */

public class Card {

    /*标签资源Id*/
    private int labelResId;

    /*字体颜色*/
    private int color;

    /*借款金额*/
    private SpannableString amount;

    /*还款期限*/
    private String deadline;

    /*还款方式*/
    private String paymentMethod;

    /*借款类型 1 2 */
    private int type;

    /*是否开放*/
    private boolean isOpen;

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

    public SpannableString getAmount() {
        return amount;
    }

    public void setAmount(SpannableString amount) {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
