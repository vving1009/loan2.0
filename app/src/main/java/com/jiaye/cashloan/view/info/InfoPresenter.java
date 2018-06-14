package com.jiaye.cashloan.view.info;

import android.net.Uri;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.config.FileConfig;
import com.jiaye.cashloan.http.data.dictionary.Area;
import com.jiaye.cashloan.http.data.dictionary.Relation;
import com.jiaye.cashloan.http.data.loan.ContactData;
import com.jiaye.cashloan.http.data.loan.SaveContact;
import com.jiaye.cashloan.http.data.loan.SaveContactRequest;
import com.jiaye.cashloan.http.data.loan.SavePerson;
import com.jiaye.cashloan.http.data.loan.SavePersonRequest;
import com.jiaye.cashloan.utils.RegexUtil;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.LocalException;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.info.source.InfoDataSource;

import org.reactivestreams.Publisher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * InfoPresenter
 *
 * @author 贾博瑄
 */
public class InfoPresenter extends BasePresenterImpl implements InfoContact.Presenter {

    private InfoContact.View mView;

    private InfoDataSource mDataSource;

    private ArrayList<Relation> mRelationFamily;

    private ArrayList<Relation> mRelationFriend;

    public InfoPresenter(InfoContact.View view, InfoDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        File file = new File(FileConfig.RELATION_PATH);
        try {
            InputStream input = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            Gson gson = new Gson();
            switch (file.getName()) {
                case "relation.json":
                    transformRelationFamily(br, gson);
                    break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            InputStream input = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            Gson gson = new Gson();
            switch (file.getName()) {
                case "relation.json":
                    transformRelationFriend(br, gson);
                    break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            File fileArea = new File(FileConfig.AREA_PATH);
            InputStream input = new FileInputStream(fileArea);
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            Gson gson = new Gson();
            switch (fileArea.getName()) {
                case "area.json":
                    transformArea(br, gson);
                    break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectPhone(Uri uri, int requestCode) {
        Disposable disposable = mDataSource.selectPhone(uri)
                .compose(new ViewTransformer<>())
                .subscribe(phone -> mView.setPhone(requestCode, phone), new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void upLoadContact() {
        Disposable disposable = mDataSource.uploadContact()
                .compose(new ViewTransformer<>())
                .subscribe(uploadContact -> {
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void submit() {
        Disposable disposable = canSubmit()
                .flatMap((Function<Boolean, Publisher<SavePerson>>) aBoolean -> submitPerson())
                .flatMap((Function<SavePerson, Publisher<SaveContact>>) savePerson -> submitContact())
                .compose(new ViewTransformer<SaveContact>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(saveContact -> {
                    mView.dismissProgressDialog();
                    mView.finish();
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    private Flowable<Boolean> canSubmit() {
        if (TextUtils.isEmpty(mView.getRegisterCity())) {
            return Flowable.error(new LocalException(R.string.error_loan_person_register_city));
        } else if (TextUtils.isEmpty(mView.getCity())) {
            return Flowable.error(new LocalException(R.string.error_loan_person_city));
        } else if (TextUtils.isEmpty(mView.getAddress())) {
            return Flowable.error(new LocalException(R.string.error_loan_person_address));
        } else if (TextUtils.isEmpty(mView.getEmail()) || !mView.getEmail().matches(RegexUtil.email())) {
            return Flowable.error(new LocalException(R.string.error_loan_person_email));
        } else if (TextUtils.isEmpty(mView.get1Name()) || mView.get1Name().length() > 5) {
            return Flowable.error(new LocalException(R.string.error_contact_1_name));
        } else if (TextUtils.isEmpty(mView.get1Relation())) {
            return Flowable.error(new LocalException(R.string.error_contact_1_relation));
        } else if (TextUtils.isEmpty(mView.get1Phone()) ||
                !mView.get1Phone().substring(mView.get1Phone().length() - 11, mView.get1Phone().length())
                        .matches(RegexUtil.phone())) {
            return Flowable.error(new LocalException(R.string.error_contact_1_phone));
        } else if (TextUtils.isEmpty(mView.get2Name()) || mView.get2Name().length() > 5) {
            return Flowable.error(new LocalException(R.string.error_contact_2_name));
        } else if (TextUtils.isEmpty(mView.get2Relation())) {
            return Flowable.error(new LocalException(R.string.error_contact_2_relation));
        } else if (TextUtils.isEmpty(mView.get2Phone()) ||
                !mView.get2Phone().substring(mView.get2Phone().length() - 11, mView.get2Phone().length())
                        .matches(RegexUtil.phone())) {
            return Flowable.error(new LocalException(R.string.error_contact_2_phone));
        } else {
            return Flowable.just(true);
        }
    }

    private Flowable<SaveContact> submitContact() {
        SaveContactRequest request = new SaveContactRequest();
        ContactData[] data = new ContactData[2];
        data[0] = new ContactData();
        data[0].setName(mView.get1Name());
        data[0].setPhone(mView.get1Phone());
        for (int i = 0; i < mRelationFamily.size(); i++) {
            if (mRelationFamily.get(i).isSelect()) {
                data[0].setType(mRelationFamily.get(i).getKey());
                data[0].setRelation(mRelationFamily.get(i).getValue());
            }
        }
        data[1] = new ContactData();
        data[1].setName(mView.get2Name());
        data[1].setPhone(mView.get2Phone());
        for (int i = 0; i < mRelationFriend.size(); i++) {
            if (mRelationFriend.get(i).isSelect()) {
                data[1].setType(mRelationFriend.get(i).getKey());
                data[1].setRelation(mRelationFriend.get(i).getValue());
            }
        }
        request.setData(data);
        return mDataSource.requestSaveContact(request);
    }

    private Flowable<SavePerson> submitPerson() {
        SavePersonRequest request = new SavePersonRequest();
        request.setRegisterCity(mView.getRegisterCity());
        request.setCity(mView.getCity());
        request.setAddress(mView.getAddress());
        request.setEmail(mView.getEmail());
        return mDataSource.requestSavePerson(request);
    }

    private void transformRelationFamily(BufferedReader br, Gson gson) {
        mRelationFamily = gson.fromJson(br, new TypeToken<List<Relation>>() {
        }.getType());
        mView.init1Relation(mRelationFamily);
    }

    private void transformRelationFriend(BufferedReader br, Gson gson) {
        mRelationFriend = gson.fromJson(br, new TypeToken<List<Relation>>() {
        }.getType());
        mView.init2Relation(mRelationFriend);
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
