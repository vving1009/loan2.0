package com.jiaye.cashloan.view.company.source;

import com.jiaye.cashloan.http.data.search.SaveSalesman;
import com.jiaye.cashloan.http.data.search.Salesman;
import com.jiaye.cashloan.http.data.search.SaveSalesmanRequest;

import java.util.List;

import io.reactivex.Flowable;

/**
 * CompanyDataSource
 *
 * @author 贾博瑄
 */

public interface CompanyDataSource {

    /**
     * 查询销售人员
     */
    Flowable<Salesman> salesman();

    /**
     * 查询业务员列表
     *
     * @param column 查询列(company, name, work_id)
     * @param value  查询值
     */
    Flowable<List<com.jiaye.cashloan.persistence.Salesman>> queryPeople(String column, String value);

    /**
     * 查询公司列表
     */
    Flowable<List<String>> queryCompany();

    /**
     * 保存销售人员
     */
    Flowable<SaveSalesman> saveSalesman(SaveSalesmanRequest request);
}
