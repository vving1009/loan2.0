package com.jiaye.cashloan.view.view.loan.auth.info;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.Contact;
import com.jiaye.cashloan.http.data.loan.ContactData;
import com.jiaye.cashloan.http.data.loan.SaveContact;
import com.jiaye.cashloan.http.data.loan.SaveContactRequest;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.http.data.dictionary.Relation;
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

    private String mFamilyId;

    private String mFriendId;

    public LoanAuthContactInfoPresenter(LoanAuthContactInfoContract.View view, LoanAuthContactInfoDataSource dataSource) {
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
                    case "relation.json":
                        transformRelationFamily(br, gson);
                        break;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        for (File file : files) {
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
                                mFamilyId = contact.getData()[i].getId();
                                mView.setFamilyName(contact.getData()[i].getName());
                                mView.setFamilyPhone(contact.getData()[i].getPhone());
                            } else if (i == 1) {
                                for (int j = 0; j < mRelationFriend.size(); j++) {
                                    if (mRelationFriend.get(j).getKey().equals(contact.getData()[i].getType())) {
                                        mRelationFriend.get(j).setSelect(true);
                                        mView.setFriend(mRelationFriend.get(j).getValue());
                                    }
                                }
                                mFriendId = contact.getData()[i].getId();
                                mView.setFriendName(contact.getData()[i].getName());
                                mView.setFriendPhone(contact.getData()[i].getPhone());
                            }
                            mView.dismissProgressDialog();
                        }
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void submit() {
        if (TextUtils.isEmpty(mView.getFamilyName())) {
            mView.showToastById(R.string.error_loan_contact_family_name);
        } else if (TextUtils.isEmpty(mView.getFamily())) {
            mView.showToastById(R.string.error_loan_contact_family);
        } else if (TextUtils.isEmpty(mView.getFamilyPhone())) {
            mView.showToastById(R.string.error_loan_contact_family_phone);
        } else if (TextUtils.isEmpty(mView.getFriendName())) {
            mView.showToastById(R.string.error_loan_contact_friend_name);
        } else if (TextUtils.isEmpty(mView.getFriend())) {
            mView.showToastById(R.string.error_loan_contact_friend);
        } else if (TextUtils.isEmpty(mView.getFriendPhone())) {
            mView.showToastById(R.string.error_loan_contact_friend_phone);
        } else {
            SaveContactRequest request = new SaveContactRequest();
            ContactData[] data = new ContactData[2];
            data[0] = new ContactData();
            data[0].setId(mFamilyId);
            data[0].setName(mView.getFamilyName());
            data[0].setPhone(mView.getFamilyPhone());
            for (int i = 0; i < mRelationFamily.size(); i++) {
                if (mRelationFamily.get(i).isSelect()) {
                    data[0].setType(mRelationFamily.get(i).getKey());
                    data[0].setRelation(mRelationFamily.get(i).getValue());
                }
            }
            data[1] = new ContactData();
            data[1].setId(mFriendId);
            data[1].setName(mView.getFriendName());
            data[1].setPhone(mView.getFriendPhone());
            for (int i = 0; i < mRelationFriend.size(); i++) {
                if (mRelationFriend.get(i).isSelect()) {
                    data[1].setType(mRelationFriend.get(i).getKey());
                    data[1].setRelation(mRelationFriend.get(i).getValue());
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

    private void transformRelationFriend(BufferedReader br, Gson gson) {
        mRelationFriend = gson.fromJson(br, new TypeToken<List<Relation>>() {
        }.getType());
        mView.initFriend(mRelationFriend);
    }
}
