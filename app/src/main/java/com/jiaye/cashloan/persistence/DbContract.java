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
     * token            令牌
     * phone            手机号
     * <p>
     * approve_number   借贷审批次数
     * progress_number  放款还贷次数
     * history_number   历史借贷次数
     * <p>
     * loan_id          借款编号
     * <p>
     * ocr_number       身份证号码
     * ocr_name         身份证姓名
     * ocr_birthday     身份证生日
     * ocr_gender       身份证性别
     * ocr_nation       身份证民族
     * ocr_address      身份证地址
     * ocr_date_begin   身份证有效期起始时间
     * ocr_date_end     身份证有效期结束时间
     * ocr_agency       身份证签发机关
     */
    public static abstract class User implements BaseColumns {
        public static final String TABLE_NAME = "user";
        /*登录或注册成功后存入数据库*/
        public static final String COLUMN_NAME_TOKEN = "token";
        public static final String COLUMN_NAME_PHONE = "phone";
        /*查询我的信息后存入数据库*/
        public static final String COLUMN_NAME_APPROVE_NUMBER = "approve_number";
        public static final String COLUMN_NAME_PROGRESS_NUMBER = "progress_number";
        public static final String COLUMN_NAME_HISTORY_NUMBER = "history_number";
        /*开始借款后存入数据库*/
        public static final String COLUMN_NAME_LOAN_ID = "loan_id";
        /*OCR*/
        public static final String COLUMN_NAME_OCR_ID = "ocr_id";
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
     * product_id       产品编号
     * product_name     产品名称
     * amount           借款金额
     * deadline         还款期限
     * payment_method   还款方式
     * is_default       是否为默认
     */
    public static abstract class Product implements BaseColumns {
        public static final String TABLE_NAME = "product";
        public static final String COLUMN_NAME_PRODUCT_ID = "product_id";
        public static final String COLUMN_NAME_PRODUCT_NAME = "product_name";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_DEADLINE = "deadline";
        public static final String COLUMN_NAME_PAYMENT_METHOD = "payment_method";
        public static final String COLUMN_NAME_IS_DEFAULT = "is_default";
    }
}
