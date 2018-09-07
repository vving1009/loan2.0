package com.jiaye.cashloan.view.loancar.carpaper.source;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.vehcile.CarPapersState;
import com.jiaye.cashloan.http.data.vehcile.CarPapersStateRequest;
import com.jiaye.cashloan.http.data.vehcile.UploadCarPapersRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.persistence.User;
import com.jiaye.cashloan.http.OssManager;
import com.satcatche.library.utils.DateUtil;
import com.jiaye.cashloan.view.loancar.carpaper.VehicleContract;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * VehicleRepository
 *
 * @author 贾博瑄
 */

public class VehicleRepository implements VehicleDataSource {

    private String picName;

    @Override
    public Flowable<EmptyResponse> uploadFile(String folder, List<String> paths) {
        User user = LoanApplication.getInstance().getDbHelper().queryUser();
        return Flowable.fromIterable(paths)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(path -> {
                    picName = DateUtil.formatDateTimeMillis(System.currentTimeMillis()) + ".jpg";
                    String ossPath = user.getPhone() + "/" +
                            user.getLoanId() + "/" + VehicleContract.ROOT_FOLDER_VEHICLE + "/" +
                            folder + "/" + picName;
                    OssManager.getInstance().upload(ossPath, path);
                    return ossPath;
                })
                .map(ossPath -> {
                    UploadCarPapersRequest request = new UploadCarPapersRequest();
                    request.setLoanId(user.getLoanId());
                    request.setPicName(picName);
                    switch (folder) {
                        case VehicleContract.FOLDER_DRIVE_LICENCE:
                            request.setPicType("9");
                            break;
                        case VehicleContract.FOLDER_VEHICLE_OWNERSHIP:
                            request.setPicType("11");
                            break;
                    }
                    request.setPicUrl(BuildConfig.OSS_BASE_URL + ossPath);
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<UploadCarPapersRequest, EmptyResponse>("uploadCarPapers"));
    }

    @Override
    public Flowable<CarPapersState> submit() {
        CarPapersStateRequest request = new CarPapersStateRequest();
        request.setLoanId(LoanApplication.getInstance().getDbHelper().queryUser().getLoanId());
        return Flowable.just(request)
                .compose(new SatcatcheResponseTransformer<CarPapersStateRequest, CarPapersState>("carPapersState"));
    }
}
