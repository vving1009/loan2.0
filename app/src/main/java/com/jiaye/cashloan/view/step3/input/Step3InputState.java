package com.jiaye.cashloan.view.step3.input;

import java.util.ArrayList;
import java.util.List;

public class Step3InputState {
    private boolean finishItem0;
    private boolean finishItem1;
    private List<String> list;

    public Step3InputState() {
        list = new ArrayList<>();
        list.add("请选择");
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

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
