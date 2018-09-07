package com.jiaye.cashloan.view.loancar.carprice.model;

import com.jiaye.cashloan.http.data.car.CarModel;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.loancar.carprice.model.source.ModelDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * ModelPresenter
 *
 * @author 贾博瑄
 */

public class ModelPresenter extends BasePresenterImpl implements ModelContract.Presenter {

    private final ModelContract.View mView;

    private final ModelDataSource mDataSource;

    public ModelPresenter(ModelContract.View view, ModelDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void requestData(String familyId) {
        Disposable disposable = mDataSource.getModelList(familyId)
                .compose(new ViewTransformer<CarModel>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .map(carModel -> {
                    List<Object> list = new ArrayList<>();
                    List<CarModel.DataBean> datas = carModel.getData();
                    for (CarModel.DataBean data : datas) {
                        list.add(data.getPyear() + "款");
                        list.addAll(data.getChexingList());
                    }
                    return list;
                    /*Collections.sort(list, (o1, o2) -> {
                        if (o1 == null || o2 == null) {
                            return -1;
                        }
                        String str1 = o1.getSaleyear();
                        String str2 = o2.getSaleyear();
                        if (str1.compareToIgnoreCase(str2) < 0) {
                            return -1;
                        } else if (str1.compareToIgnoreCase(str2) == 0) {
                            return 0;
                        }
                        return 1;
                    });*/
                })
                .subscribe(list -> {
                    mView.dismissProgressDialog();
                    mView.setList(list);
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
