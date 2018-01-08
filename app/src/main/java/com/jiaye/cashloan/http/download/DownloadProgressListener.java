package com.jiaye.cashloan.http.download;

/**
 * DownloadProgressListener
 *
 * @author 贾博瑄
 */

public interface DownloadProgressListener {

    void update(long bytesRead, long contentLength, boolean done);

}
