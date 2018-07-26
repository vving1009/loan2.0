package com.jiaye.cashloan.view.launch.source;

import com.satcatche.appupgrade.checkupgrade.bean.UpgradeResponse;
import com.satcatche.appupgrade.download.DownloadProgressListener;

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
    Flowable<UpgradeResponse.Body> checkUpdate();

    /**
     * 下载应用
     */
    Flowable<File> download(DownloadProgressListener listener);
}
