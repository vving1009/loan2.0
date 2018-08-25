package com.jiaye.cashloan.view.step1.input;

import java.util.ArrayList;
import java.util.List;

public class Step1InputState {
    private boolean finishItem0;
    private boolean finishItem1;
    private boolean finishItem2;
    private boolean finishItem3;
    private List<String> list;

    public Step1InputState() {
        list = new ArrayList<>();
        list.add("请选择");
        list.add("请选择");
        list.add("请输入");
        list.add("请选择");
    }

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

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
