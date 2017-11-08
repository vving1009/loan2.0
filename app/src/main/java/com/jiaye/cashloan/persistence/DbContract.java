package com.jiaye.cashloan.persistence;

import android.provider.BaseColumns;

/**
 * DbContract
 *
 * @author 贾博瑄
 */

public class DbContract {

    private DbContract() {
    }

    /**
     * 用户
     * <p>
     * token 令牌
     * name 姓名
     * phone 手机号
     * approve_number 借贷审批次数
     * loan_number 放款还贷次数
     * history_number 历史借贷次数
     * <p>
     * ocr_number 身份证号码
     * ocr_name 身份证姓名
     * ocr_birthday 身份证生日
     * ocr_gender 身份证性别
     * ocr_nation 身份证民族
     * ocr_address 身份证地址
     * ocr_date_begin 身份证有效期起始时间
     * ocr_date_end 身份证有效期结束时间
     * ocr_agency 身份证签发机关
     */
    public static abstract class User implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_TOKEN = "token";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PHONE = "phone";
        public static final String COLUMN_NAME_APPROVE_NUMBER = "approve_number";
        public static final String COLUMN_NAME_LOAN_NUMBER = "loan_number";
        public static final String COLUMN_NAME_HISTORY_NUMBER = "history_number";
        /*OCR*/
        public static final String COLUMN_NAME_OCR_NUMBER = "ocr_number";
        public static final String COLUMN_NAME_OCR_NAME = "ocr_name";
        public static final String COLUMN_NAME_OCR_BIRTHDAY = "ocr_birthday";
        public static final String COLUMN_NAME_OCR_GENDER = "ocr_gender";
        public static final String COLUMN_NAME_OCR_NATION = "ocr_nation";
        public static final String COLUMN_NAME_OCR_ADDRESS = "ocr_address";
        public static final String COLUMN_NAME_OCR_DATE_BEGIN = "ocr_date_begin";
        public static final String COLUMN_NAME_OCR_DATE_END = "ocr_date_end";
        public static final String COLUMN_NAME_OCR_AGENCY = "ocr_agency";
    }

    /**
     * 产品
     * <p>
     * product_id 产品编号
     * amount 借款金额
     * deadline 还款期限
     * payment_method 还款方式
     */
    public static abstract class Product implements BaseColumns {
        public static final String TABLE_NAME = "product";
        public static final String COLUMN_NAME_PRODUCT_ID = "product_id";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_DEADLINE = "deadline";
        public static final String COLUMN_NAME_PAYMENT_METHOD = "payment_method";
    }
}
