package com.jiaye.cashloan.view.data.launch.source;

import android.content.Context;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.LoanClient;
import com.jiaye.cashloan.http.UploadClient;
import com.jiaye.cashloan.http.data.dictionary.DictionaryRequest;
import com.jiaye.cashloan.http.data.launch.CheckUpdate;
import com.jiaye.cashloan.http.data.launch.CheckUpdateRequest;
import com.jiaye.cashloan.http.download.DownloadClient;
import com.jiaye.cashloan.http.download.DownloadProgressListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * LaunchRepository
 *
 * @author 贾博瑄
 */

public class LaunchRepository implements LaunchDataSource {

    private CheckUpdate mCheckUpdate;

    @Override
    public Flowable<CheckUpdate> checkUpdate() {
        return UploadClient.INSTANCE.getService().checkUpdate(new CheckUpdateRequest())
                .map(new Function<CheckUpdate, CheckUpdate>() {
                    @Override
                    public CheckUpdate apply(CheckUpdate checkUpdate) throws Exception {
                        mCheckUpdate = checkUpdate;
                        return checkUpdate;
                    }
                });
    }

    @Override
    public Flowable<File> download(DownloadProgressListener listener) {
        String url = mCheckUpdate.getData().getDownloadUrl();
        String base = url.subSequence(0, url.lastIndexOf("/") + 1).toString();
        String apk = url.subSequence(url.lastIndexOf("/") + 1, url.length()).toString();
        return new DownloadClient(base, listener).getDownloadService().download(apk)
                .map(new Function<ResponseBody, InputStream>() {
                    @Override
                    public InputStream apply(ResponseBody responseBody) throws Exception {
                        return responseBody.byteStream();
                    }
                })
                .map(new Function<InputStream, File>() {
                    @Override
                    public File apply(InputStream inputStream) throws Exception {
                        return save(inputStream);
                    }
                });
    }

    @Override
    public CheckUpdate getCheckUpdate() {
        return mCheckUpdate;
    }

    @Override
    public boolean isNeedGuide() {
        return LoanApplication.getInstance().getPreferencesHelper().isNeedGuide();
    }

    @Override
    public Flowable<File> download(String type, final String name) {
        return LoanClient.INSTANCE.getService()
                .dictCommon(new DictionaryRequest(type))
                .map(new Function<ResponseBody, File>() {
                    @Override
                    public File apply(ResponseBody response) throws Exception {
                        Context context = LoanApplication.getInstance().getApplicationContext();
                        File file = new File(context.getFilesDir(), name);
                        FileOutputStream out = new FileOutputStream(file);
                        out.write(response.string().getBytes("UTF-8"));
                        out.close();
                        return file;
                    }
                });
    }

    private File save(InputStream input) {
        File dir = LoanApplication.getInstance().getApplicationContext().getExternalFilesDir("Download");
        if (dir != null && !dir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dir.mkdir();
        }
        File file = new File(dir, "loan.apk");
        OutputStream output = null;
        try {
            output = new FileOutputStream(file);
            byte[] buffer = new byte[4 * 1024]; // or other buffer size
            int read;
            while ((read = input.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
