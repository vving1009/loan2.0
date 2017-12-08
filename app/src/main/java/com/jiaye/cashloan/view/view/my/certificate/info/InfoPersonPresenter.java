package com.jiaye.cashloan.view.view.my.certificate.info;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.dictionary.Area;
import com.jiaye.cashloan.http.data.dictionary.Education;
import com.jiaye.cashloan.http.data.dictionary.Marriage;
import com.jiaye.cashloan.http.data.loan.Person;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.my.certificate.info.person.source.InfoPersonDataSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * InfoPersonPresenter
 *
 * @author 贾博瑄
 */

public class InfoPersonPresenter extends BasePresenterImpl implements InfoPersonContract.Presenter {

    private final InfoPersonContract.View mView;

    private final InfoPersonDataSource mDataSource;

    private ArrayList<Education> mEducations;

    private ArrayList<Marriage> mMarriages;

    public InfoPersonPresenter(InfoPersonContract.View view, InfoPersonDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        File dir = LoanApplication.getInstance().getFilesDir();
        File[] files = dir.listFiles();
        for (File file : files) {
            try {
                InputStream input = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(input));
                Gson gson = new Gson();
                switch (file.getName()) {
                    case "area.json":
                        transformArea(br, gson);
                        break;
                    case "education.json":
                        transformEducation(br, gson);
                        break;
                    case "marriage.json":
                        transformMarriage(br, gson);
                        break;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        Disposable disposable = mDataSource.requestPerson()
                .compose(new ViewTransformer<Person>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<Person>() {
                    @Override
                    public void accept(Person person) throws Exception {
                        for (int i = 0; i < mEducations.size(); i++) {
                            if (mEducations.get(i).getKey().equals(person.getEducation())) {
                                mEducations.get(i).setSelect(true);
                                mView.setEducation(mEducations.get(i).getValue());
                            }
                        }
                        for (int i = 0; i < mMarriages.size(); i++) {
                            if (mMarriages.get(i).getKey().equals(person.getMarriage())) {
                                mMarriages.get(i).setSelect(true);
                                mView.setMarriage(mMarriages.get(i).getValue());
                            }
                        }
                        mView.setRegisterCity(person.getRegisterCity());
                        mView.setCity(person.getCity());
                        mView.setAddress(person.getAddress());
                        mView.setEmail(person.getEmail());
                        mView.dismissProgressDialog();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
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
    }

    private void transformEducation(BufferedReader br, Gson gson) {
        mEducations = gson.fromJson(br, new TypeToken<List<Education>>() {
        }.getType());
    }

    private void transformMarriage(BufferedReader br, Gson gson) {
        mMarriages = gson.fromJson(br, new TypeToken<List<Marriage>>() {
        }.getType());
    }
}
