package com.jiaye.cashloan.view.view.home;

import android.widget.Toast;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.home.Product;
import com.jiaye.cashloan.http.data.home.ProductList;
import com.jiaye.cashloan.http.data.home.ProductRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.home.source.HomeDataSource;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * HomePresenter
 *
 * @author 贾博瑄
 */

public class HomePresenter extends BasePresenterImpl implements HomeContract.Presenter {

    private final HomeContract.View mView;

    private final HomeDataSource mDataSource;

    public HomePresenter(HomeContract.View view, HomeDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        ProductRequest request = new ProductRequest();
        Disposable disposable = Flowable.just(request)
                .compose(new ResponseTransformer<ProductRequest, ProductList>("productList"))
                .compose(new ViewTransformer<ProductList>() {
                    @Override
                    public void accept() {
                        mView.showProgressDialog();
                    }
                })
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
                            if (product.getIsOpen().equals("1")) {
                                product.setLabelResId(R.drawable.home_ic_label_blue);
                                product.setColor(R.color.color_blue);
                            } else if (product.getIsOpen().equals("0")) {
                                product.setLabelResId(R.drawable.home_ic_label_gray);
                                product.setColor(R.color.color_gray_dark);
                            }
                        }
                        mView.setList(products);
                        mView.dismissProgressDialog();
                    }
                }, new ThrowableConsumer(mView), new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void selectProduct(Product product) {
//        if (product.getIsOpen().equals("1")) {
//            Disposable disposable = mDataSource.addProduct(product)
//                    .compose(new ViewTransformer<Product>() {
//                        @Override
//                        public void accept() {
//                            mView.showProgressDialog();
//                        }
//                    })
//                    .subscribe(new Consumer<Product>() {
//                        @Override
//                        public void accept(Product product) throws Exception {
//                            mView.dismissProgressDialog();
//                            mView.showLoanView();
//                        }
//                    }, new ThrowableConsumer(mView));
//            mCompositeDisposable.add(disposable);
//        }
       Disposable disposable = Flowable.just(product)
               .compose(new ViewTransformer<Product>(){
                   @Override
                   public void accept() {
                      mView.showProgressDialog();
                   }
               })
               .subscribe(new Consumer<Product>() {
                   @Override
                   public void accept(Product p) throws Exception {
                       mView.dismissProgressDialog();
                        if(p.getName().equals("分期消费")){
                            mView.showWishView();
                        }else if(p.getName().equals("借款产品")){

                        }else{

                        }
                   }
               },new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
