package com.jiaye.cashloan.view.view.home.wish;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.data.home.source.wish.source.WishDataSource;
import com.jiaye.cashloan.view.view.home.HomeContract;

import org.w3c.dom.Text;

/**
 * Created by guozihua on 2018/1/2.
 */

public class WishPresenter extends BasePresenterImpl implements WishContract.Presenter {


    private final WishContract.View mView;

    private final WishDataSource mDataSource;

    public WishPresenter(WishContract.View view, WishDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void commit() {
        if(TextUtils.isEmpty(mView.getBrand())){
            mView.showToastById(R.string.error_wish_brand);
        }else if(TextUtils.isEmpty(mView.getProductType())){
            mView.showToastById(R.string.error_wish_product_type);
        }else if(TextUtils.isEmpty(mView.getReferencePrice())){
            mView.showToastById(R.string.error_wish_reference_price);
        }else if(TextUtils.isEmpty(mView.getLoanNumber())){
            mView.showToastById(R.string.error_wish_loan_number);
        }else{

        }
    }
}
