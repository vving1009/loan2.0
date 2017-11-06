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
     */
    public static abstract class User implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_TOKEN = "token";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PHONE = "phone";
        public static final String COLUMN_NAME_APPROVE_NUMBER = "approve_number";
        public static final String COLUMN_NAME_LOAN_NUMBER = "loan_number";
        public static final String COLUMN_NAME_HISTORY_NUMBER = "history_number";
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
