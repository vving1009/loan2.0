package com.jiaye.cashloan.view.loancar.carprice.year.source;

import java.util.List;

import io.reactivex.Flowable;

/**
 * YearDataSource
 *
 * @author 贾博瑄
 */

public interface YearDataSource {

    Flowable<List<String>> getYearList(String modelId);
}
