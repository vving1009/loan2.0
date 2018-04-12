package com.jiaye.cashloan.http;

import com.jiaye.cashloan.http.base.SatcatcheRequest;
import com.jiaye.cashloan.http.base.SatcatcheResponse;
import com.jiaye.cashloan.http.data.loan.FileState;
import com.jiaye.cashloan.http.data.loan.FileStateRequest;
import com.jiaye.cashloan.http.data.loan.UploadFile;
import com.jiaye.cashloan.http.data.loan.UploadFileRequest;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * SatcatcheService
 *
 * @author 贾博瑄
 */
public interface SatcatcheService {

    /*认证材料查询*/
    @POST("searchMaterialPic")
    Flowable<SatcatcheResponse<FileState>> fileState(@Body SatcatcheRequest<FileStateRequest> request);

    /*上传认证材料*/
    @POST("uploadMaterialPic")
    Flowable<SatcatcheResponse<UploadFile>> uploadFile(@Body SatcatcheRequest<UploadFileRequest> request);
}
