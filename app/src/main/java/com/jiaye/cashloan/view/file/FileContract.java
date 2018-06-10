package com.jiaye.cashloan.view.file;

import com.jiaye.cashloan.http.data.loan.FileState;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;
import com.jph.takephoto.model.TImage;

import java.util.ArrayList;
import java.util.List;

/**
 * FileContract
 *
 * @author 贾博瑄
 */
public interface FileContract {

    String ROOT_FOLDER_FILE = "file";

    String FOLDER_WAGE = "wage";

    String FOLDER_CREDIT = "bank";

    String FOLDER_ADDRESS = "address";

    interface View extends BaseViewContract {

        void setList(List<FileState.Data> list);

        void finish();
    }

    interface Presenter extends BasePresenter {

        void setFolder(String folder);

        void upload(ArrayList<TImage> list);

        void submit();
    }
}
