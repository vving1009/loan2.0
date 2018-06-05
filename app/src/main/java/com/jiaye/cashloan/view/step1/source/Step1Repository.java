package com.jiaye.cashloan.view.step1.source;

import com.jiaye.cashloan.http.data.step1.Step1;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * Step1Repository
 *
 * @author 贾博瑄
 */

public class Step1Repository implements Step1DataSource {

    @Override
    public Flowable<List<Step1>> requestStep1() {
        List<Step1> list = new ArrayList<>();
        Step1 step1_1 = new Step1();
        step1_1.setName("身份认证");
        step1_1.setState(0);
        Step1 step1_2 = new Step1();
        step1_2.setName("人像对比");
        step1_2.setState(0);
        Step1 step1_3 = new Step1();
        step1_3.setName("个人资料");
        step1_3.setState(0);
        Step1 step1_4 = new Step1();
        step1_4.setName("手机运营商");
        step1_4.setState(0);
        Step1 step1_5 = new Step1();
        step1_5.setName("车辆证件");
        step1_5.setState(0);
        list.add(step1_1);
        list.add(step1_2);
        list.add(step1_3);
        list.add(step1_4);
        list.add(step1_5);
        return Flowable.just(list);
    }
}
