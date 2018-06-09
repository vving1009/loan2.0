package com.jiaye.cashloan.view.vehicle;

import android.util.Log;

import com.jiaye.cashloan.http.data.vehcile.CarPapersState;
import com.jiaye.cashloan.http.data.vehcile.UploadCarPapers;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.vehicle.source.VehicleDataSource;
import com.jph.takephoto.model.TImage;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

/**
 * VehiclePresenter
 *
 * @author 贾博瑄
 */

public class VehiclePresenter extends BasePresenterImpl implements VehicleContract.Presenter {

    private final VehicleContract.View mView;

    private final VehicleDataSource mDataSource;

    private String mFolder;

    private int licenceCount;

    private int ownershipCount;

    public VehiclePresenter(VehicleContract.View view, VehicleDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
        licenceCount = 0;
        ownershipCount = 0;
    }

    @Override
    public void setFolder(String folder) {
        mFolder = folder;
    }

    @Override
    public void upload(ArrayList<TImage> list) {
        ArrayList<String> paths = new ArrayList<>();
        for (TImage image : list) {
            String path = image.getCompressPath();
            paths.add(path);
        }
        Disposable disposable = mDataSource.uploadFile(mFolder, paths)
                .compose(new ViewTransformer<UploadCarPapers>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(putObjectResult -> {
                    mView.dismissProgressDialog();
                    switch (mFolder) {
                        case VehicleContract.FOLDER_DRIVE_LICENCE:
                            licenceCount++;
                            break;
                        case VehicleContract.FOLDER_VEHICLE_OWNERSHIP:
                            ownershipCount++;
                            break;
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void submit() {
        Log.d("liwei", "submit: licenceCount = " + licenceCount + ", ownershipCount= " + ownershipCount);
        if (licenceCount >= 3 && ownershipCount >= 2) {
            Disposable disposable = mDataSource.submit()
                    .compose(new ViewTransformer<CarPapersState>() {
                        @Override
                        public void accept() {
                            super.accept();
                            mView.showProgressDialog();
                        }
                    })
                    .subscribe(a -> {
                        mView.dismissProgressDialog();
                        mView.finish();
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        } else if (licenceCount < 3 && ownershipCount < 2) {
            mView.showToast("请上传行驶证照片不少于3张，车辆产权证不少于2张。");
        } else if (licenceCount < 3) {
            mView.showToast("请上传行驶证照片不少于3张。");
        } else if (ownershipCount < 2) {
            mView.showToast("请上传车辆产权证不少于2张。");
        }
    }
}
