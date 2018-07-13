package com.jiaye.cashloan.view.taobao;

import android.os.Bundle;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.MoxieFragment;
import com.jiaye.cashloan.view.taobao.source.TaoBaoRepository;

/**
 * TaoBaoFragment
 *
 * @author 贾博瑄
 */

public class TaoBaoFragment extends MoxieFragment implements TaoBaoContract.View {

    private static final String TAG = "TaoBaoFragment";

    private TaoBaoContract.Presenter mPresenter;

    public static TaoBaoFragment newInstance() {
        Bundle args = new Bundle();
        TaoBaoFragment fragment = new TaoBaoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BasePresenter getPresenter() {
        mPresenter = new TaoBaoPresenter(this, new TaoBaoRepository());
        return mPresenter;
    }

    @Override
    protected String getMoxieType() {
        return "taobao";
    }

    @Override
    protected String getMoxieParams() {
        return "";
    }
}
