package com.jiaye.cashloan.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.jiaye.cashloan.BuildConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * DbHelper
 *
 * @author 贾博瑄
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "loan.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String REAL_TYPE = " REAL";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_USER =
            "CREATE TABLE " + DbContract.User.TABLE_NAME + " (" +
                    DbContract.User._ID + INTEGER_TYPE + " PRIMARY KEY," +
                    DbContract.User.COLUMN_NAME_TOKEN + TEXT_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_PHONE + TEXT_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_APPROVE_NUMBER + INTEGER_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_PROGRESS_NUMBER + INTEGER_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_HISTORY_NUMBER + INTEGER_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_LOAN_ID + TEXT_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_OCR_ID + TEXT_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_OCR_NAME + TEXT_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_OCR_BIRTHDAY + TEXT_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_OCR_GENDER + TEXT_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_OCR_NATION + TEXT_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_OCR_ADDRESS + TEXT_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_OCR_DATE_BEGIN + TEXT_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_OCR_DATE_END + TEXT_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_OCR_AGENCY + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_SALES =
            "CREATE TABLE " + DbContract.Salesman.TABLE_NAME + " (" +
                    DbContract.Salesman._ID + INTEGER_TYPE + " PRIMARY KEY," +
                    DbContract.Salesman.COLUMN_COMPANY_ID + TEXT_TYPE + COMMA_SEP +
                    DbContract.Salesman.COLUMN_COMPANY + TEXT_TYPE + COMMA_SEP +
                    DbContract.Salesman.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    DbContract.Salesman.COLUMN_WORK_ID + TEXT_TYPE + " )";

    public DbHelper(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, BuildConfig.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER);
        db.execSQL(SQL_CREATE_SALES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                // 2018.2.5 user 表 删除 loan_approve_id;loan_progress_id;
                db.execSQL("create table temp_user as select " +
                        "token, phone, " +
                        "approve_number, progress_number, history_number, loan_id, " +
                        "ocr_id, ocr_name, ocr_birthday, ocr_gender, ocr_nation, ocr_address, " +
                        "ocr_date_begin, ocr_date_end, ocr_agency" +
                        " from user");
                db.execSQL("drop table user");
                db.execSQL("alter table temp_user rename to user");
            case 2:
                // 2018.4.29 删除表 product
                db.execSQL("drop table product");
            case 3:
                // 2018.5.11 修改 approve_number progress_number history_number 字段类型
                db.execSQL("alter table user rename to temp_user");
                db.execSQL("create table " + DbContract.User.TABLE_NAME + " (" +
                        DbContract.User._ID + INTEGER_TYPE + " primary key," +
                        DbContract.User.COLUMN_NAME_TOKEN + TEXT_TYPE + COMMA_SEP +
                        DbContract.User.COLUMN_NAME_PHONE + TEXT_TYPE + COMMA_SEP +
                        DbContract.User.COLUMN_NAME_APPROVE_NUMBER + TEXT_TYPE + COMMA_SEP +
                        DbContract.User.COLUMN_NAME_PROGRESS_NUMBER + TEXT_TYPE + COMMA_SEP +
                        DbContract.User.COLUMN_NAME_HISTORY_NUMBER + TEXT_TYPE + COMMA_SEP +
                        DbContract.User.COLUMN_NAME_LOAN_ID + TEXT_TYPE + COMMA_SEP +
                        DbContract.User.COLUMN_NAME_OCR_ID + TEXT_TYPE + COMMA_SEP +
                        DbContract.User.COLUMN_NAME_OCR_NAME + TEXT_TYPE + COMMA_SEP +
                        DbContract.User.COLUMN_NAME_OCR_BIRTHDAY + TEXT_TYPE + COMMA_SEP +
                        DbContract.User.COLUMN_NAME_OCR_GENDER + TEXT_TYPE + COMMA_SEP +
                        DbContract.User.COLUMN_NAME_OCR_NATION + TEXT_TYPE + COMMA_SEP +
                        DbContract.User.COLUMN_NAME_OCR_ADDRESS + TEXT_TYPE + COMMA_SEP +
                        DbContract.User.COLUMN_NAME_OCR_DATE_BEGIN + TEXT_TYPE + COMMA_SEP +
                        DbContract.User.COLUMN_NAME_OCR_DATE_END + TEXT_TYPE + COMMA_SEP +
                        DbContract.User.COLUMN_NAME_OCR_AGENCY + TEXT_TYPE +
                        " )");
                db.execSQL("insert into user select * from temp_user");
                db.execSQL("drop table temp_user");
            case 4:
                db.execSQL("drop table if exists salesman");
                db.execSQL("create table " + DbContract.Salesman.TABLE_NAME + " (" +
                        DbContract.Salesman._ID + INTEGER_TYPE + " primary key," +
                        DbContract.Salesman.COLUMN_COMPANY + TEXT_TYPE + COMMA_SEP +
                        DbContract.Salesman.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                        DbContract.Salesman.COLUMN_WORK_ID + TEXT_TYPE + " )");
            case 5:
                // 2018.6.7 salesman 表 增加 company_id 字段
                db.execSQL("alter table salesman add column company_id text");
            default:
                break;
        }
    }

    /**
     * 插入用户信息
     *
     * @param phone    手机号
     * @param token    令牌
     * @param ocr_id   身份证
     * @param ocr_name 身份证
     */
    public void insertUser(String phone, String token, String ocr_id, String ocr_name) {
        ContentValues values = new ContentValues();
        values.put("phone", phone);
        values.put("token", token);
        values.put("ocr_id", ocr_id);
        values.put("ocr_name", ocr_name);
        getWritableDatabase().insert("user", "", values);
    }

    /**
     * 查询用户信息
     *
     * @return 用户信息
     */
    public com.jiaye.cashloan.persistence.User queryUser() {
        com.jiaye.cashloan.persistence.User user = new com.jiaye.cashloan.persistence.User();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM user", null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                user.setPhone(cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE)));
                user.setToken(cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_TOKEN)));
                user.setLoanId(cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_LOAN_ID)));
            }
            cursor.close();
        }
        return user;
    }

    /**
     * 更新用户信息
     *
     * @param phone  手机号
     * @param token  令牌
     * @param loanId 借款编号
     */
    public void updateUser(String phone, String token, String loanId) {
        ContentValues values = new ContentValues();
        if (!TextUtils.isEmpty(phone)) {
            values.put("phone", phone);
        }
        if (!TextUtils.isEmpty(token)) {
            values.put("token", token);
        }
        if (!TextUtils.isEmpty(loanId)) {
            values.put("loan_id", loanId);
        }
        getWritableDatabase().update("user", values, null, null);
    }

    /**
     * 删除用户信息
     */
    public void deleteUser() {
        getWritableDatabase().execSQL("delete from user");
    }

    /**
     * 删除所有销售人员信息
     */
    public void deleteSales() {
        getWritableDatabase().execSQL("delete from salesman");
    }

    /**
     * 插入销售人员信息
     */
    public void insertSales(List<com.jiaye.cashloan.http.data.search.Salesman.Company> list) {
        getWritableDatabase().beginTransaction();
        for (com.jiaye.cashloan.http.data.search.Salesman.Company company : list) {
            for (com.jiaye.cashloan.http.data.search.Salesman.Employee employee : company.getList()) {
                String sql = "insert into salesman" + " (company_id, company, name, work_id) values" +
                        " ('" + company.getId() +
                        "','" + company.getName() +
                        "','" + employee.getName() +
                        "','" + employee.getNumber() +
                        "');";
                getWritableDatabase().execSQL(sql);
            }
        }
        getWritableDatabase().setTransactionSuccessful();
        getWritableDatabase().endTransaction();
    }

    /**
     * 查询销售人员列表
     *
     * @param column 查询列(company, name, work_id)
     * @param value  查询值
     */
    public List<Salesman> querySales(String column, String value) {
        Cursor cursor = getReadableDatabase().rawQuery("select * from salesman where " + column + " like ?" +
                "order by name", new String[]{"%" + value + "%"});
        List<Salesman> salesmen = new ArrayList<>();
        while (cursor.moveToNext()) {
            Salesman sp = new Salesman();
            sp.setCompanyId(cursor.getString(cursor.getColumnIndexOrThrow("company_id")));
            sp.setCompany(cursor.getString(cursor.getColumnIndexOrThrow("company")));
            sp.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            sp.setWorkId(cursor.getString(cursor.getColumnIndexOrThrow("work_id")));
            salesmen.add(sp);
        }
        cursor.close();
        return salesmen;
    }

    /**
     * 查询公司列表
     *
     * @return 公司列表
     */
    public List<String> queryCompany() {
        Cursor cursor = getReadableDatabase().rawQuery("select company from salesman order by company", null);
        List<String> companies = new ArrayList<>();
        while (cursor.moveToNext()) {
            String company;
            company = cursor.getString(cursor.getColumnIndexOrThrow("company"));
            if (!companies.contains(company)) {
                companies.add(company);
            }
        }
        cursor.close();
        return companies;
    }
}
