package com.jiaye.cashloan.view.vehicle;

import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;
import com.jph.takephoto.model.TImage;

import java.util.ArrayList;

/**
 * VehicleContract
 *
 * @author 贾博瑄
 */

public interface VehicleContract {

    String ROOT_FOLDER_VEHICLE = "vehicle";

    String FOLDER_DRIVE_LICENCE = "driveLicence";

    String FOLDER_VEHICLE_OWNERSHIP = "vehicleOwnership";

    interface View extends BaseViewContract {

        void showLicenceCount(int count);

        void showOwnershipCount(int count);

        void finish();
    }

    interface Presenter extends BasePresenter {
        void setFolder(String folder);

        void upload(ArrayList<TImage> list);

        void submit();
    }
}
