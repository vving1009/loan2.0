package com.jiaye.cashloan.view.view.loan.auth.file;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.data.loan.auth.source.file.LoanAuthFileModel;
import com.jph.takephoto.model.TImage;

import java.util.ArrayList;
import java.util.List;

/**
 * LoanAuthFileContract
 *
 * @author 贾博瑄
 */
public interface LoanAuthFileContract {

    interface View extends BaseViewContract {

        /**
         * 设置列表
         */
        void setList(List<LoanAuthFileModel> list);

        /**
         * 拍照
         */
        void camera(String path);

        /**
         * 相册
         */
        void photo();
    }

    interface Presenter extends BasePresenter {

        /**
         * 设置类型
         */
        void setType(int type);

        /**
         * 拍照
         */
        void camera();

        /**
         * 相册
         */
        void photo();

        /**
         * 上传照片列表
         *
         * @param list 列表
         */
        void upload(ArrayList<TImage> list);
    }
}
