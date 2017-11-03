package com.jiaye.loan.cashloan.view.data;

/**
 * BaseDataSource
 *
 * @author 贾博瑄
 */

public interface BaseDataSource {

    /**
     * 开启数据库
     */
    void open();

    /**
     * 关闭数据库
     */
    void close();
}
