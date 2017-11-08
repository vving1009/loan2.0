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
                    DbContract.User.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_PHONE + TEXT_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_APPROVE_NUMBER + INTEGER_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_LOAN_NUMBER + INTEGER_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_HISTORY_NUMBER + INTEGER_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_OCR_NUMBER + TEXT_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_OCR_NAME + TEXT_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_OCR_BIRTHDAY + TEXT_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_OCR_GENDER + TEXT_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_OCR_NATION + TEXT_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_OCR_ADDRESS + TEXT_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_OCR_DATE_BEGIN + TEXT_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_OCR_DATE_END + TEXT_TYPE + COMMA_SEP +
                    DbContract.User.COLUMN_NAME_OCR_AGENCY + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_PRODUCT =
            "CREATE TABLE " + DbContract.Product.TABLE_NAME + " (" +
                    DbContract.Product._ID + INTEGER_TYPE + " PRIMARY KEY," +
                    DbContract.Product.COLUMN_NAME_PRODUCT_ID + TEXT_TYPE + COMMA_SEP +
                    DbContract.Product.COLUMN_NAME_AMOUNT + INTEGER_TYPE + COMMA_SEP +
                    DbContract.Product.COLUMN_NAME_DEADLINE + INTEGER_TYPE + COMMA_SEP +
                    DbContract.Product.COLUMN_NAME_PAYMENT_METHOD + TEXT_TYPE +
                    " )";

    public DbHelper(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, BuildConfig.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER);
        db.execSQL(SQL_CREATE_PRODUCT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
