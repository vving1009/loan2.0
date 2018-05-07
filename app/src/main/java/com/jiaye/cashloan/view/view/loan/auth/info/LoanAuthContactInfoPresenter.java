package com.jiaye.cashloan.view.view.loan.auth.info;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.config.FileConfig;
import com.jiaye.cashloan.http.data.dictionary.Relation;
import com.jiaye.cashloan.http.data.loan.Contact;
import com.jiaye.cashloan.http.data.loan.ContactData;
import com.jiaye.cashloan.http.data.loan.SaveContact;
import com.jiaye.cashloan.http.data.loan.SaveContactRequest;
import com.jiaye.cashloan.utils.RegexUtil;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.auth.source.info.LoanAuthContactInfoDataSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * LoanAuthContactInfoPresenter
 *
 * @author 贾博瑄
 */

public class LoanAuthContactInfoPresenter extends BasePresenterImpl implements LoanAuthContactInfoContract.Presenter {

    private final LoanAuthContactInfoContract.View mView;

    private final LoanAuthContactInfoDataSource mDataSource;

    private ArrayList<Relation> mRelationFamily;

    private ArrayList<Relation> mRelationFriend;

    private ArrayList<Relation> mRelationFamily2;

    private ArrayList<Relation> mRelationFriend2;

    public LoanAuthContactInfoPresenter(LoanAuthContactInfoContract.View view, LoanAuthContactInfoDataSource dataSource) {
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
                    transformRelationFamily2(br, gson);
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
            InputStream input = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            Gson gson = new Gson();
            switch (file.getName()) {
                case "relation.json":
                    transformRelationFriend2(br, gson);
                    break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Disposable disposable = mDataSource.requestContact()
                .compose(new ViewTransformer<Contact>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<Contact>() {
                    @Override
                    public void accept(Contact contact) throws Exception {
                        for (int i = 0; i < contact.getData().length; i++) {
                            if (i == 0) {
                                for (int j = 0; j < mRelationFamily.size(); j++) {
                                    if (mRelationFamily.get(j).getKey().equals(contact.getData()[i].getType())) {
                                        mRelationFamily.get(j).setSelect(true);
                                        mView.setFamily(mRelationFamily.get(j).getValue());
                                    }
                                }
                                mView.setFamilyName(contact.getData()[i].getName());
                                mView.setFamilyPhone(contact.getData()[i].getPhone());
                            } else if (i == 1) {
                                for (int j = 0; j < mRelationFamily2.size(); j++) {
                                    if (mRelationFamily2.get(j).getKey().equals(contact.getData()[i].getType())) {
                                        mRelationFamily2.get(j).setSelect(true);
                                        mView.setFamily2(mRelationFamily2.get(j).getValue());
                                    }
                                }
                                mView.setFamilyName2(contact.getData()[i].getName());
                                mView.setFamilyPhone2(contact.getData()[i].getPhone());
                            } else if (i == 2) {
                                for (int j = 0; j < mRelationFriend.size(); j++) {
                                    if (mRelationFriend.get(j).getKey().equals(contact.getData()[i].getType())) {
                                        mRelationFriend.get(j).setSelect(true);
                                        mView.setFriend(mRelationFriend.get(j).getValue());
                                    }
                                }
                                mView.setFriendName(contact.getData()[i].getName());
                                mView.setFriendPhone(contact.getData()[i].getPhone());
                            } else if (i == 3) {
                                for (int j = 0; j < mRelationFriend2.size(); j++) {
                                    if (mRelationFriend2.get(j).getKey().equals(contact.getData()[i].getType())) {
                                        mRelationFriend2.get(j).setSelect(true);
                                        mView.setFriend2(mRelationFriend2.get(j).getValue());
                                    }
                                }
                                mView.setFriendName2(contact.getData()[i].getName());
                                mView.setFriendPhone2(contact.getData()[i].getPhone());
                            }
                            mView.dismissProgressDialog();
                        }
                    }
                }, new ThrowableConsumer(mView), new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.dismissProgressDialog();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void submit() {
        if (TextUtils.isEmpty(mView.getFamilyName()) || mView.getFamilyName().length() > 5) {
            mView.showToastById(R.string.error_loan_contact_family_name);
        } else if (TextUtils.isEmpty(mView.getFamily())) {
            mView.showToastById(R.string.error_loan_contact_family);
        } else if (TextUtils.isEmpty(mView.getFamilyPhone()) || !mView.getFamilyPhone().matches(RegexUtil.phone())) {
            mView.showToastById(R.string.error_loan_contact_family_phone);
        } else if (TextUtils.isEmpty(mView.getFriendName()) || mView.getFriendName().length() > 5) {
            mView.showToastById(R.string.error_loan_contact_friend_name);
        } else if (TextUtils.isEmpty(mView.getFriend())) {
            mView.showToastById(R.string.error_loan_contact_friend);
        } else if (TextUtils.isEmpty(mView.getFriendPhone()) || !mView.getFriendPhone().matches(RegexUtil.phone())) {
            mView.showToastById(R.string.error_loan_contact_friend_phone);
        } else if (TextUtils.isEmpty(mView.getFamilyName2()) || mView.getFamilyName2().length() > 5) {
            mView.showToastById(R.string.error_loan_contact_family_name);
        } else if (TextUtils.isEmpty(mView.getFamily2())) {
            mView.showToastById(R.string.error_loan_contact_family);
        } else if (TextUtils.isEmpty(mView.getFamilyPhone2()) || !mView.getFamilyPhone2().matches(RegexUtil.phone())) {
            mView.showToastById(R.string.error_loan_contact_family_phone);
        } else if (TextUtils.isEmpty(mView.getFriendName2()) || mView.getFriendName2().length() > 5) {
            mView.showToastById(R.string.error_loan_contact_friend_name);
        } else if (TextUtils.isEmpty(mView.getFriend2())) {
            mView.showToastById(R.string.error_loan_contact_friend);
        } else if (TextUtils.isEmpty(mView.getFriendPhone2()) || !mView.getFriendPhone2().matches(RegexUtil.phone())) {
            mView.showToastById(R.string.error_loan_contact_friend_phone);
        } else {
            SaveContactRequest request = new SaveContactRequest();
            ContactData[] data = new ContactData[4];
            data[0] = new ContactData();
            data[0].setName(mView.getFamilyName());
            data[0].setPhone(mView.getFamilyPhone());
            for (int i = 0; i < mRelationFamily.size(); i++) {
                if (mRelationFamily.get(i).isSelect()) {
                    data[0].setType(mRelationFamily.get(i).getKey());
                    data[0].setRelation(mRelationFamily.get(i).getValue());
                }
            }
            data[1] = new ContactData();
            data[1].setName(mView.getFamilyName2());
            data[1].setPhone(mView.getFamilyPhone2());
            for (int i = 0; i < mRelationFamily2.size(); i++) {
                if (mRelationFamily2.get(i).isSelect()) {
                    data[1].setType(mRelationFamily2.get(i).getKey());
                    data[1].setRelation(mRelationFamily2.get(i).getValue());
                }
            }
            data[2] = new ContactData();
            data[2].setName(mView.getFriendName());
            data[2].setPhone(mView.getFriendPhone());
            for (int i = 0; i < mRelationFriend.size(); i++) {
                if (mRelationFriend.get(i).isSelect()) {
                    data[2].setType(mRelationFriend.get(i).getKey());
                    data[2].setRelation(mRelationFriend.get(i).getValue());
                }
            }
            data[3] = new ContactData();
            data[3].setName(mView.getFriendName2());
            data[3].setPhone(mView.getFriendPhone2());
            for (int i = 0; i < mRelationFriend2.size(); i++) {
                if (mRelationFriend2.get(i).isSelect()) {
                    data[3].setType(mRelationFriend2.get(i).getKey());
                    data[3].setRelation(mRelationFriend2.get(i).getValue());
                }
            }
            request.setData(data);
            Disposable disposable = mDataSource.requestSaveContact(request)
                    .compose(new ViewTransformer<SaveContact>() {
                        @Override
                        public void accept() {
                            super.accept();
                            mView.showProgressDialog();
                        }
                    })
                    .subscribe(new Consumer<SaveContact>() {
                        @Override
                        public void accept(SaveContact saveContact) throws Exception {
                            mView.dismissProgressDialog();
                            mView.result();
                        }
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        }
    }

    private void transformRelationFamily(BufferedReader br, Gson gson) {
        mRelationFamily = gson.fromJson(br, new TypeToken<List<Relation>>() {
        }.getType());
        mView.initFamily(mRelationFamily);
    }

    private void transformRelationFamily2(BufferedReader br, Gson gson) {
        mRelationFamily2 = gson.fromJson(br, new TypeToken<List<Relation>>() {
        }.getType());
        mView.initFamily2(mRelationFamily2);
    }

    private void transformRelationFriend(BufferedReader br, Gson gson) {
        mRelationFriend = gson.fromJson(br, new TypeToken<List<Relation>>() {
        }.getType());
        mView.initFriend(mRelationFriend);
    }

    private void transformRelationFriend2(BufferedReader br, Gson gson) {
        mRelationFriend2 = gson.fromJson(br, new TypeToken<List<Relation>>() {
        }.getType());
        mView.initFriend2(mRelationFriend2);
    }
}
