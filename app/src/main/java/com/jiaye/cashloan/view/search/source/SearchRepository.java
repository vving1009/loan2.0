package com.jiaye.cashloan.view.search.source;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.persistence.DbHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * SearchRepository
 *
 * @author 贾博�?
 */

public class SearchRepository implements SearchDataSource {

    private SQLiteDatabase db;
    private String[] companysArray;
    private String[] firstNamesArray;
    private String[] lastNamesArray;
    private String[] phonePrefixsArray;

    public SearchRepository(Context context) {
        db = new DbHelper(context).getWritableDatabase();
        companysArray = context.getResources().getStringArray(R.array.company_items);
        firstNamesArray = context.getResources().getStringArray(R.array.first_name);
        lastNamesArray = context.getResources().getStringArray(R.array.last_name);
        phonePrefixsArray = context.getResources().getStringArray(R.array.phone_prefix);
        initSalesTable();
    }

    private void initSalesTable() {
        db.execSQL("delete from " + DbContract.Salesman.TABLE_NAME);
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            String company = companysArray[random.nextInt(companysArray.length)];
            StringBuilder name = new StringBuilder();
            name.append(lastNamesArray[random.nextInt(lastNamesArray.length)]);
            name.append(firstNamesArray[random.nextInt(firstNamesArray.length)]);
            if (random.nextBoolean()) {
                name.append(firstNamesArray[random.nextInt(firstNamesArray.length)]);
            }
            String phone = phonePrefixsArray[random.nextInt(phonePrefixsArray.length)] + (random.nextInt(90000000) + 10000000);
            db.execSQL("insert into " + DbContract.Salesman.TABLE_NAME + " (" +
                    DbContract.Salesman.COLUMN_COMPANY + "," +
                    DbContract.Salesman.COLUMN_NAME + "," +
                    DbContract.Salesman.COLUMN_WORK_ID + ") values ('" + company + "','" +
                    name.toString() + "','" + phone + "')");
        }
    }

    @Override
    public Flowable<List<Salesman>> queryPeople(String column, String value) {
        return Flowable.just(db.rawQuery("select * from " + DbContract.Salesman.TABLE_NAME + " where " + column + " like ?" +
                "order by " + DbContract.Salesman.COLUMN_NAME, new String[]{"%" + value + "%"}))
                .subscribeOn(Schedulers.io())
                .map(cursor -> {
                    List<Salesman> salesmen = new ArrayList<>();
                    while (cursor.moveToNext()) {
                        Salesman sp = new Salesman();
                        sp.setCompany(cursor.getString(cursor.getColumnIndexOrThrow(DbContract.Salesman.COLUMN_COMPANY)));
                        sp.setName(cursor.getString(cursor.getColumnIndexOrThrow(DbContract.Salesman.COLUMN_NAME)));
                        sp.setWorkId(cursor.getString(cursor.getColumnIndexOrThrow(DbContract.Salesman.COLUMN_WORK_ID)));
                        salesmen.add(sp);
                    }
                    cursor.close();
                    return salesmen;
                });
    }

    @Override
    public Flowable<List<String>> queryCompany() {
        return Flowable.just("load network resources here.")
                .map((string) -> db.rawQuery("select " + DbContract.Salesman.COLUMN_COMPANY + " from " +
                        DbContract.Salesman.TABLE_NAME + " order by " + DbContract.Salesman.COLUMN_COMPANY, null))
                .subscribeOn(Schedulers.io())
                .map(cursor -> {
                    List<String> companys = new ArrayList<>();
                    String company;
                    while (cursor.moveToNext()) {
                        company = cursor.getString(cursor.getColumnIndexOrThrow(DbContract.Salesman.COLUMN_COMPANY));
                        if (!companys.contains(company)) {
                            companys.add(company);
                        }
                    }
                    cursor.close();
                    return companys;
                });
    }
}

