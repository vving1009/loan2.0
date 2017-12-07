package com.jiaye.cashloan.view.data.loan;

/**
 * LoanAuthModel
 *
 * @author 贾博瑄
 */

public class LoanAuthModel {

    /*名称*/
    private int name;

    /*图标*/
    private int icon;

    /*图标背景*/
    private int icBackground;

    /*文字颜色*/
    private int color;

    /*状态图标*/
    private int icState;

    /*背景*/
    private int background;

    /*是否认证*/
    private boolean isVerify;

    /*可以修改*/
    private boolean canModify;

    /*认证失败*/
    private boolean isFailure;

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIcBackground() {
        return icBackground;
    }

    public void setIcBackground(int icBackground) {
        this.icBackground = icBackground;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getIcState() {
        return icState;
    }

    public void setIcState(int icState) {
        this.icState = icState;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public boolean isVerify() {
        return isVerify;
    }

    public void setVerify(boolean verify) {
        isVerify = verify;
    }

    public boolean isCanModify() {
        return canModify;
    }

    public void setCanModify(boolean canModify) {
        this.canModify = canModify;
    }

    public boolean isFailure() {
        return isFailure;
    }

    public void setFailure(boolean failure) {
        isFailure = failure;
    }
}
