package com.jiaye.loan.cashloan.view.view.home;

import com.jiaye.loan.cashloan.R;
import com.jiaye.loan.cashloan.http.data.home.Product;
import com.jiaye.loan.cashloan.http.data.home.ProductList;
import com.jiaye.loan.cashloan.http.data.home.ProductRequest;
import com.jiaye.loan.cashloan.http.utils.NetworkTransformer;
import com.jiaye.loan.cashloan.view.BasePresenterImpl;
import com.jiaye.loan.cashloan.view.ThrowableConsumer;
import com.jiaye.loan.cashloan.view.data.BaseDataSource;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * HomePresenter
 *
 * @author 贾博瑄
 */

public class HomePresenter extends BasePresenterImpl implements HomeContract.Presenter {

    private final HomeContract.View mView;

    public HomePresenter(HomeContract.View view, BaseDataSource dataSource) {
        super(dataSource);
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        super.subscribe();
        ProductRequest request = new ProductRequest();
        Disposable disposable = Observable.just(request)
                .compose(new NetworkTransformer<ProductRequest, ProductList>(mView, "productList"))
                .map(new Function<ProductList, List<Product>>() {
                    @Override
                    public List<Product> apply(ProductList productList) throws Exception {
                        return productList.getProducts();
                    }
                })
                .subscribe(new Consumer<List<Product>>() {
                    @Override
                    public void accept(List<Product> products) throws Exception {
                        for (Product product : products) {
                            if (product.getIsOpen().equals("0")) {
                                product.setLabelResId(R.drawable.home_ic_label_blue);
                                product.setColor(R.color.color_blue);
                            } else if (product.getIsOpen().equals("1")) {
                                product.setLabelResId(R.drawable.home_ic_label_gray);
                                product.setColor(R.color.color_gray_dark);
                            }
                        }
                        mView.setList(products);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
