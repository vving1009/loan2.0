package com.jiaye.cashloan.view.data.launch;

import android.content.Context;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.LoanClient;
import com.jiaye.cashloan.http.data.dictionary.DictionaryRequest;

import java.io.File;
import java.io.FileOutputStream;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * LaunchRepository
 *
 * @author 贾博瑄
 */

public class LaunchRepository implements LaunchDataSource {

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
}
