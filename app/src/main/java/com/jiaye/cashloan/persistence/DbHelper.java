package com.jiaye.cashloan.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jiaye.cashloan.BuildConfig;

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
                    " )";

    public DbHelper(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, BuildConfig.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i = oldVersion; i < newVersion; i++) {
            switch (i) {
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
                default:
                    break;
            }
        }
    }
}
