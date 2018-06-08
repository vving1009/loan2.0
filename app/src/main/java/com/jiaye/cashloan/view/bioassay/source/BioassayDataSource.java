package com.jiaye.cashloan.view.bioassay.source;

import com.jiaye.cashloan.http.data.bioassay.Bioassay;

import io.reactivex.Flowable;

/**
 * BioassayDataSource
 *
 * @author 贾博瑄
 */

public interface BioassayDataSource {

    /**
     * 上传图片
     */
    Flowable<Bioassay> upload(byte[] data);
}
