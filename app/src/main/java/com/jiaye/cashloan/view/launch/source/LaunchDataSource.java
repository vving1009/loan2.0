package com.jiaye.cashloan.view.launch.source;

import com.jiaye.cashloan.http.data.launch.CheckUpdate;
import com.jiaye.cashloan.http.download.DownloadProgressListener;

import java.io.File;

import io.reactivex.Flowable;

/**
 * LaunchDataSource
 *
 * @author 贾博瑄
 */

public interface LaunchDataSource {

    /**
     * 请求数据字典列表
     */
    Flowable<Object> requestDictionaryList();

    /**
     * 检测升级
     */
    Flowable<CheckUpdate> checkUpdate();

    /**
     * 下载应用
     */
    Flowable<File> download(DownloadProgressListener listener);

    /**
     * 是否需要引导
     *
     * @return true 需要 false 不需要
     */
    boolean isNeedGuide();
}
