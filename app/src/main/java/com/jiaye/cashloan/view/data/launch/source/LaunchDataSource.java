package com.jiaye.cashloan.view.data.launch.source;

import java.io.File;

import io.reactivex.Flowable;

/**
 * LaunchDataSource
 *
 * @author 贾博瑄
 */

public interface LaunchDataSource {

    /**
     * 是否需要引导
     *
     * @return true 需要 false 不需要
     */
    boolean isNeedGuide();

    /**
     * 下载字典
     *
     * @param type 类型
     * @param name 姓名
     * @return file
     */
    Flowable<File> download(String type, final String name);
}
