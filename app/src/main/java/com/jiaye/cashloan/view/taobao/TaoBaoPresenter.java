package com.jiaye.cashloan.view.taobao;

import android.graphics.Bitmap;

import com.jiaye.cashloan.http.data.loan.SaveTaoBao;
import com.jiaye.cashloan.http.gongxinbao.GongXinBao;
import com.jiaye.cashloan.utils.Base64Util;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.taobao.source.TaoBaoDataSource;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * TaoBaoPresenter
 *
 * @author 贾博瑄
 */

public class TaoBaoPresenter extends BasePresenterImpl implements TaoBaoContract.Presenter {

    private final TaoBaoContract.View mView;

    private final TaoBaoDataSource mDataSource;

    public TaoBaoPresenter(TaoBaoContract.View view, TaoBaoDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }
}
