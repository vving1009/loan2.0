package com.jiaye.cashloan.utils;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;

public class OssManager {

    private static OssManager INSTANCE;

    private OSS oss;

    private String bucketName = BuildConfig.OSS_BUCKET_NAME;

    private String accessKeyId = BuildConfig.OSS_ACCESS_KEY_ID;

    private String accessKeySecret = BuildConfig.OSS_ACCESS_KEY_SECRET;

    private String endPoint = BuildConfig.OSS_ENDPOINT;

    public synchronized static OssManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OssManager();
        }
        return INSTANCE;
    }

    private OssManager() {
        init();
    }

    /**
     * 初始化
     **/
    private void init() {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(10 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(10 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        oss = new OSSClient(LoanApplication.getInstance().getApplicationContext(), endPoint, credentialProvider, conf);
    }

    /**
     * 同步上传
     *
     * @param name     上传到服务器的名字
     * @param filePath 文件所在本地路径
     */
    public PutObjectResult upload(String name, String filePath) throws ClientException, ServiceException {
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucketName, name, filePath);
        PutObjectResult putResult = oss.putObject(put);
        Log.d("PutObject", "UploadSuccess");
        Log.d("ETag", putResult.getETag());
        Log.d("RequestId", putResult.getRequestId());
        return putResult;
    }
}
