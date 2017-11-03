package com.jiaye.loan.cashloan.view.data.home.source;

import com.jiaye.loan.cashloan.view.data.BaseDataSource;
import com.jiaye.loan.cashloan.view.data.home.Card;

import java.util.List;

import io.reactivex.Observable;

/**
 * HomeDataSource
 *
 * @author 贾博瑄
 */

public interface HomeDataSource extends BaseDataSource {

    /**
     * 获取卡片列表
     * @return 卡片列表
     */
    Observable<List<Card>> getCardList();
}
