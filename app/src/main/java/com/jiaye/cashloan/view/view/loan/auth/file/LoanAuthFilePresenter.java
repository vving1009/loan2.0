package com.jiaye.cashloan.view.view.loan.auth.file;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.FileState;
import com.jiaye.cashloan.http.data.loan.UploadFile;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.auth.source.file.LoanAuthFileDataSource;
import com.jiaye.cashloan.view.data.loan.auth.source.file.LoanAuthFileModel;
import com.jph.takephoto.model.TImage;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * LoanAuthFilePresenter
 *
 * @author 贾博瑄
 */
public class LoanAuthFilePresenter extends BasePresenterImpl implements LoanAuthFileContract.Presenter {

    private LoanAuthFileContract.View mView;

    private LoanAuthFileDataSource mDataSource;

    private int mType;

    public LoanAuthFilePresenter(LoanAuthFileContract.View view, LoanAuthFileDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        requestFileState();
    }

    @Override
    public void setType(int type) {
        mType = type;
    }

    @Override
    public void camera() {
        mView.camera("/camera/" + System.currentTimeMillis() + ".jpg");
    }

    @Override
    public void photo() {
        mView.photo();
    }

    @Override
    public void upload(ArrayList<TImage> list) {
        ArrayList<String> paths = new ArrayList<>();
        for (TImage image : list) {
            String path = image.getCompressPath();
            paths.add(path);
        }
        Disposable disposable = mDataSource.uploadFile(mType, paths)
                .compose(new ViewTransformer<UploadFile>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<UploadFile>() {
                    @Override
                    public void accept(UploadFile uploadFile) throws Exception {
                        mView.dismissProgressDialog();
                        requestFileState();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void confirm() {
        Disposable disposable = mDataSource.requestLoanConfirm()
                .compose(new ViewTransformer<String>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String loanId) throws Exception {
                        mView.dismissProgressDialog();
                        mView.showLoanProgressView(loanId);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    private void requestFileState() {
        Disposable disposable = mDataSource.requestFileState()
                .compose(new ViewTransformer<FileState>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<FileState>() {
                    @Override
                    public void accept(FileState fileState) throws Exception {
                        mView.dismissProgressDialog();
                        List<LoanAuthFileModel> list = new ArrayList<>();
                        for (FileState.Data data : fileState.getList()) {
                            LoanAuthFileModel model = new LoanAuthFileModel();
                            model.setIcon(R.drawable.loan_auth_file_ic_photo);
                            model.setName(data.getName());
                            model.setType(data.getType());
                            setLoanAuthFileModel(data.getCount(), model);
                            list.add(model);
                        }
                        mView.setList(list);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    private void setLoanAuthFileModel(int count, LoanAuthFileModel model) {
        model.setCount(count);
        if (count == 0) {
            model.setIcState(R.drawable.ic_angle_blue);
            model.setIcBackground(R.drawable.loan_auth_ic_bg_gray);
            model.setColor(R.color.color_gray);
            model.setBackground(R.drawable.loan_auth_bg_gray);
        } else {
            model.setIcState(R.drawable.loan_auth_ic_pass);
            model.setIcBackground(R.drawable.loan_auth_ic_bg_blue);
            model.setColor(R.color.color_blue);
            model.setBackground(R.drawable.loan_auth_bg_blue);
        }
    }
}
