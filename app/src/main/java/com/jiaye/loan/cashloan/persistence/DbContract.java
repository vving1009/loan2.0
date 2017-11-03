package com.jiaye.loan.cashloan.persistence;

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
     * 用户信息
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
}
