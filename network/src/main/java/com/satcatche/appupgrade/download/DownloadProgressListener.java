package com.satcatche.appupgrade.download;

/**
 * DownloadProgressListener
 *
 * @author 贾博瑄
 */

public interface DownloadProgressListener {

    void update(long bytesRead, long contentLength, boolean done);

}
