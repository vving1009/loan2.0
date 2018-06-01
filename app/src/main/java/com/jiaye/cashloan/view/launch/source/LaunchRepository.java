package com.jiaye.cashloan.view.launch.source;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.config.FileConfig;
import com.jiaye.cashloan.http.data.dictionary.DictionaryList;
import com.jiaye.cashloan.http.data.dictionary.DictionaryListRequest;
import com.jiaye.cashloan.http.data.launch.CheckUpdate;
import com.jiaye.cashloan.http.data.launch.CheckUpdateRequest;
import com.jiaye.cashloan.http.download.DownloadClient;
import com.jiaye.cashloan.http.download.DownloadProgressListener;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.utils.MD5Util;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

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
    public Flowable<Object> requestDictionaryList() {
        DictionaryListRequest request = new DictionaryListRequest();
        final DictionaryListRequest.Dictionary[] dictionaries = new DictionaryListRequest.Dictionary[4];
        dictionaries[0] = new DictionaryListRequest.Dictionary();
        dictionaries[0].setType(1);
        dictionaries[0].setMd5(MD5Util.get32MD5Lower(new File(FileConfig.AREA_PATH)));
        dictionaries[1] = new DictionaryListRequest.Dictionary();
        dictionaries[1].setType(2);
        dictionaries[1].setMd5(MD5Util.get32MD5Lower(new File(FileConfig.EDUCATION_PATH)));
        dictionaries[2] = new DictionaryListRequest.Dictionary();
        dictionaries[2].setType(3);
        dictionaries[2].setMd5(MD5Util.get32MD5Lower(new File(FileConfig.MARRIAGE_PATH)));
        dictionaries[3] = new DictionaryListRequest.Dictionary();
        dictionaries[3].setType(4);
        dictionaries[3].setMd5(MD5Util.get32MD5Lower(new File(FileConfig.RELATION_PATH)));
        request.setDictionaries(dictionaries);
        return Flowable.just(request)
                .compose(new SatcatcheResponseTransformer<DictionaryListRequest, DictionaryList>
                        ("dictionaryList"))
                .flatMap(new Function<DictionaryList, Publisher<DictionaryList.Dictionary>>() {
                    @Override
                    public Publisher<DictionaryList.Dictionary> apply(DictionaryList dictionaryList) throws Exception {
                        return Flowable.fromArray(dictionaryList.getDictionaries());
                    }
                })
                .flatMap(new Function<DictionaryList.Dictionary, Publisher<Object>>() {
                    @Override
                    public Publisher<Object> apply(DictionaryList.Dictionary dictionary) throws Exception {
                        if (dictionary.isNeedUpdate()) {
                            String url = dictionary.getUrl();
                            String base = url.subSequence(0, url.lastIndexOf("/") + 1).toString();
                            final String name = url.subSequence(url.lastIndexOf("/") + 1, url.length()).toString();
                            return new DownloadClient(base).getDownloadService().download(name)
                                    .map(new Function<ResponseBody, InputStream>() {
                                        @Override
                                        public InputStream apply(ResponseBody responseBody) throws Exception {
                                            return responseBody.byteStream();
                                        }
                                    })
                                    .map(new Function<InputStream, Object>() {
                                        @Override
                                        public Object apply(InputStream inputStream) throws Exception {
                                            save(inputStream, name);
                                            return Flowable.just(new Object());
                                        }
                                    });
                        } else {
                            return Flowable.just(new Object());
                        }
                    }
                })
                .last(new Object())
                .toFlowable();
    }

    @Override
    public Flowable<CheckUpdate> checkUpdate() {
        return Flowable.just(new CheckUpdateRequest())
                .compose(new SatcatcheResponseTransformer<CheckUpdateRequest, CheckUpdate>
                        ("checkUpdate"))
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
        String url = mCheckUpdate.getDownloadUrl();
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
                        return save(inputStream, "loan.apk");
                    }
                });
    }

    @Override
    public boolean isNeedGuide() {
        return LoanApplication.getInstance().getPreferencesHelper().isNeedGuide();
    }

    @Override
    public Flowable<File> download(String type, final String name) {
        /*return LoanClient.INSTANCE.getService()
                .dictCommon(new DictionaryListRequest(type))
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
                });*/
        return new Flowable<File>() {
            @Override
            protected void subscribeActual(Subscriber<? super File> s) {

            }
        };
    }

    private File save(InputStream input, String name) {
        File dir = LoanApplication.getInstance().getApplicationContext().getExternalFilesDir("Download");
        if (dir != null && !dir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dir.mkdir();
        }
        File file = new File(dir, name);
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
