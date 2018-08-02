package com.jiaye.cashloan.utils;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.StsTokenClient;
import com.jiaye.cashloan.http.data.ststoken.StsTokenRequest;
import com.jiaye.cashloan.http.data.ststoken.StsTokenResponse;
import com.orhanobut.logger.Logger;

import java.io.IOException;

public enum OssManager {

    INSTANCE;

    private static final String TAG = "OssManager : ";

    private OSS oss;

    private String bucketName = BuildConfig.OSS_BUCKET_NAME;

    private String endPoint = BuildConfig.OSS_ENDPOINT;

    public static OssManager getInstance() {
        return INSTANCE;
    }

    OssManager() {
        init();
    }

    /**
     * 初始化
     **/
    private void init() {
        OSSCredentialProvider credentialProvider = new OSSFederationCredentialProvider() {
            @Override
            public OSSFederationToken getFederationToken() throws ClientException {
                Logger.d(TAG + "Get oss token...");
                try {
                    StsTokenResponse.JyrcResDataBean response = getTokenResponse().getJyrcResData();
                    return new OSSFederationToken(response.getAccessKeyId(), response.getAccessKeySecret(),
                            response.getSecurityToken(), response.getExpiration());
                } catch (IOException e) {
                    Logger.d(TAG + e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }
        };
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(10 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(10 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        oss = new OSSClient(LoanApplication.getInstance().getApplicationContext(), endPoint, credentialProvider, conf);
    }

    private StsTokenResponse getTokenResponse() throws IOException{
        StsTokenRequest request = new StsTokenRequest();
        return StsTokenClient.INSTANCE.getService().getToken(request).execute().body();
    }

    /**
     * 同步上传
     *
     * @param name     上传到服务器的名字
     * @param filePath 文件所在本地路径
     */
    public PutObjectResult upload(String name, String filePath) throws ClientException, ServiceException {
        Logger.d(TAG + "oss upload file: " + name + ", " + filePath);
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucketName, name, filePath);
        PutObjectResult putResult = oss.putObject(put);
        Logger.d(TAG + "PutObject UploadSuccess");
        Logger.d(TAG + "ETag" + putResult.getETag());
        Logger.d(TAG + "RequestId" + putResult.getRequestId());
        return putResult;
    }

    /**
     * 同步上传
     *
     * @param name       上传到服务器的名字
     * @param uploadData 二进制byte[]数组
     */
    public PutObjectResult upload(String name, byte[] uploadData) throws ClientException, ServiceException {
        Logger.d(TAG + "oss upload file: " + name);
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucketName, name, uploadData);
        PutObjectResult putResult = oss.putObject(put);
        Logger.d(TAG + "PutObject UploadSuccess");
        Logger.d(TAG + "ETag" + putResult.getETag());
        Logger.d(TAG + "RequestId" + putResult.getRequestId());
        return putResult;
    }
}
