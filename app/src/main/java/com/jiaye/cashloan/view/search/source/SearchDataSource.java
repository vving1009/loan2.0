package com.jiaye.cashloan.view.search.source;

import com.jiaye.cashloan.http.data.search.SaveSalesman;
import com.jiaye.cashloan.http.data.search.SaveSalesmanRequest;

import java.util.List;

import io.reactivex.Flowable;

/**
 * SearchDataSource
 *
 * @author 贾博瑄
 */

public interface SearchDataSource {

    /**
     * 查询业务员列表
     *
     * @param column 查询列(company, name, work_id)
     * @param value  查询值
     */
    Flowable<List<com.jiaye.cashloan.persistence.Salesman>> queryPeople(String column, String value);

    /**
     * 保存销售人员
     */
    Flowable<SaveSalesman> saveSalesman(SaveSalesmanRequest request);
}
