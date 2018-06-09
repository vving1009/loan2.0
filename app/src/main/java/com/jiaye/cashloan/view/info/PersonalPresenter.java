package com.jiaye.cashloan.view.info;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.config.FileConfig;
import com.jiaye.cashloan.http.data.dictionary.Area;
import com.jiaye.cashloan.http.data.loan.SavePerson;
import com.jiaye.cashloan.http.data.loan.SavePersonRequest;
import com.jiaye.cashloan.utils.RegexUtil;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.LocalException;
import com.jiaye.cashloan.view.info.source.PersonalDataSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * PersonalPresenter
 *
 * @author 贾博瑄
 */

public class PersonalPresenter extends BasePresenterImpl implements PersonalContract.Presenter {

    private final PersonalContract.View mView;

    private final PersonalDataSource mDataSource;

    public PersonalPresenter(PersonalContract.View view, PersonalDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        try {
            File file = new File(FileConfig.AREA_PATH);
            InputStream input = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            Gson gson = new Gson();
            switch (file.getName()) {
                case "area.json":
                    transformArea(br, gson);
                    break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Flowable<Boolean> canSubmit() {
        if (TextUtils.isEmpty(mView.getRegisterCity())) {
            return Flowable.error(new LocalException(R.string.error_loan_person_register_city));
        } else if (TextUtils.isEmpty(mView.getCity())) {
            return Flowable.error(new LocalException(R.string.error_loan_person_city));
        } else if (TextUtils.isEmpty(mView.getAddress())) {
            return Flowable.error(new LocalException(R.string.error_loan_person_address));
        } else if (TextUtils.isEmpty(mView.getEmail()) || !mView.getEmail().matches(RegexUtil.email())) {
            return Flowable.error(new LocalException(R.string.error_loan_person_email));
        } else {
            return Flowable.just(true);
        }
    }

    @Override
    public Flowable<SavePerson> submit() {
        SavePersonRequest request = new SavePersonRequest();
        request.setRegisterCity(mView.getRegisterCity());
        request.setCity(mView.getCity());
        request.setAddress(mView.getAddress());
        request.setEmail(mView.getEmail());
        return mDataSource.requestSavePerson(request);
    }

    private void transformArea(BufferedReader br, Gson gson) {
        ArrayList<Area> areas = gson.fromJson(br, new TypeToken<List<Area>>() {
        }.getType());
        ArrayList<ArrayList<String>> areas2 = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<String>>> areas3 = new ArrayList<>();
        for (int i = 0; i < areas.size(); i++) {//遍历省份
            ArrayList<String> cities = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> provinces = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c = 0; c < areas.get(i).getCity().size(); c++) {//遍历该省份的所有城市
                String CityName = areas.get(i).getCity().get(c).getName();
                cities.add(CityName);//添加城市
                ArrayList<String> strings = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (areas.get(i).getCity().get(c).getAreas() == null || areas.get(i).getCity().get(c).getAreas().size() == 0) {
                    strings.add("");
                } else {
                    strings.addAll(areas.get(i).getCity().get(c).getAreas());
                }
                provinces.add(strings);//添加该省所有地区数据
            }
            areas2.add(cities);
            areas3.add(provinces);
        }
        mView.initArea(areas, areas2, areas3);
    }
}
