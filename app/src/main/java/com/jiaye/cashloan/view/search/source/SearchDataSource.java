package com.jiaye.cashloan.view.search.source;

import java.util.List;

import io.reactivex.Flowable;

/**
 * SearchDataSource
 *
 * @author 贾博�?
 */

public interface SearchDataSource {

    /**
     * 查询业务员
     * @param column 查询列(company, name, work_id)
     * @param value 查询值
     * @return
     */
    Flowable<List<Salesman>> queryPeople(String column, String value);

    /**
     * 加载公司list
     * @return
     */
    Flowable<List<String>> queryCompany();
}
