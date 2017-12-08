package com.jiaye.cashloan.view.view.my.certificate.info;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.dictionary.Relation;
import com.jiaye.cashloan.http.data.loan.Contact;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.my.certificate.info.contact.source.InfoContactDataSource;

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
 * InfoContactPresenter
 *
 * @author 贾博瑄
 */

public class InfoContactPresenter extends BasePresenterImpl implements InfoContactContract.Presenter {

    private final InfoContactContract.View mView;

    private final InfoContactDataSource mDataSource;

    private ArrayList<Relation> mRelationFamily;

    private ArrayList<Relation> mRelationFriend;

    public InfoContactPresenter(InfoContactContract.View view, InfoContactDataSource dataSource) {
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
                                mView.setFamilyName(contact.getData()[i].getName());
                                mView.setFamilyPhone(contact.getData()[i].getPhone());
                            } else if (i == 1) {
                                for (int j = 0; j < mRelationFriend.size(); j++) {
                                    if (mRelationFriend.get(j).getKey().equals(contact.getData()[i].getType())) {
                                        mRelationFriend.get(j).setSelect(true);
                                        mView.setFriend(mRelationFriend.get(j).getValue());
                                    }
                                }
                                mView.setFriendName(contact.getData()[i].getName());
                                mView.setFriendPhone(contact.getData()[i].getPhone());
                            }
                            mView.dismissProgressDialog();
                        }
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    private void transformRelationFamily(BufferedReader br, Gson gson) {
        mRelationFamily = gson.fromJson(br, new TypeToken<List<Relation>>() {
        }.getType());
    }

    private void transformRelationFriend(BufferedReader br, Gson gson) {
        mRelationFriend = gson.fromJson(br, new TypeToken<List<Relation>>() {
        }.getType());
    }
}
