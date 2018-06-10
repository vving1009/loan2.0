package com.jiaye.cashloan.view.file;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.FileState;
import com.jiaye.cashloan.view.PhotoFunctionFragment;
import com.jiaye.cashloan.view.file.source.FileRepository;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TResult;

import java.io.File;
import java.util.List;

/**
 * FileFragment
 *
 * @author 贾博瑄
 */
public class FileFragment extends PhotoFunctionFragment implements FileContract.View {

    private FileContract.Presenter mPresenter;

    private Adapter mAdapter;

    private BottomSheetDialog mBottomDialog;

    public static FileFragment newInstance() {
        Bundle args = new Bundle();
        FileFragment fragment = new FileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getTitleId() {
        return R.string.loan_auth_file;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View root = inflater.inflate(R.layout.file_fragment, frameLayout, true);
        RecyclerView recyclerView = root.findViewById(R.id.recycler);
        mAdapter = new Adapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        root.findViewById(R.id.btn_submit).setOnClickListener(v -> mPresenter.submit());
        mBottomDialog = new BottomSheetDialog(getActivity());
        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.photo_layout, null);
        layout.findViewById(R.id.text_camera).setOnClickListener(v -> {
            mBottomDialog.dismiss();
            camera("/camera/" + System.currentTimeMillis() + ".jpg");
        });
        layout.findViewById(R.id.text_photo).setOnClickListener(v -> {
            mBottomDialog.dismiss();
            photo();
        });
        layout.findViewById(R.id.text_cancel).setOnClickListener(v -> mBottomDialog.dismiss());
        mBottomDialog.setContentView(layout);
        mPresenter = new FilePresenter(this, new FileRepository());
        mPresenter.subscribe();
        return null;
    }

    @Override
    public void takeSuccess(TResult result) {
        mPresenter.upload(result.getImages());
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void setList(List<FileState.Data> list) {
        mAdapter.setList(list);
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    public void camera(String path) {
        File file = new File(Environment.getExternalStorageDirectory(), path);
        if (!file.getParentFile().exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);
        CompressConfig compressConfig = CompressConfig.ofDefaultConfig();
        compressConfig.enableReserveRaw(false);
        compressConfig.setMaxSize(500 * 1024);
        getTakePhoto().onEnableCompress(compressConfig, false);
        getTakePhoto().onPickFromCapture(imageUri);
    }

    public void photo() {
        CompressConfig compressConfig = CompressConfig.ofDefaultConfig();
        compressConfig.enableReserveRaw(false);
        compressConfig.setMaxSize(500 * 1024);
        getTakePhoto().onEnableCompress(compressConfig, false);
        getTakePhoto().onPickMultiple(9);
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private List<FileState.Data> mList;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.file_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            switch (position) {
                case 0:
                    for (int i = 0; i < mList.size(); i++) {
                        // 工资
                        if (mList.get(i).getType() == 2) {
                            if (mList.get(i).getCount() > 0) {
                                holder.setBackground(R.drawable.file_bg_1_upload);
                                holder.setPhotoVisibility(false);
                                holder.setText("工资流水 (" + mList.get(i).getCount() + "张)");
                                holder.setTextColor(getResources().getColor(R.color.color_white));
                            } else {
                                holder.setBackground(R.drawable.file_bg_1_un_upload);
                                holder.setPhotoVisibility(true);
                                holder.setText("工资流水");
                                holder.setTextColor(getResources().getColor(R.color.color_gray));
                            }
                        }
                    }
                    break;
                case 1:
                    for (int i = 0; i < mList.size(); i++) {
                        // 征信
                        if (mList.get(i).getType() == 5) {
                            if (mList.get(i).getCount() > 0) {
                                holder.setBackground(R.drawable.file_bg_2_upload);
                                holder.setPhotoVisibility(false);
                                holder.setText("征信报告 (" + mList.get(i).getCount() + "张)");
                                holder.setTextColor(getResources().getColor(R.color.color_white));
                            } else {
                                holder.setBackground(R.drawable.file_bg_2_un_upload);
                                holder.setPhotoVisibility(true);
                                holder.setText("征信报告");
                                holder.setTextColor(getResources().getColor(R.color.color_gray));
                            }
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < mList.size(); i++) {
                        // 住址
                        if (mList.get(i).getType() == 4) {
                            if (mList.get(i).getCount() > 0) {
                                holder.setBackground(R.drawable.file_bg_3_un_upload);
                                holder.setPhotoVisibility(false);
                                holder.setText("住址证明 (" + mList.get(i).getCount() + "张)");
                                holder.setTextColor(getResources().getColor(R.color.color_white));
                            } else {
                                holder.setBackground(R.drawable.file_bg_3_un_upload);
                                holder.setPhotoVisibility(true);
                                holder.setText("住址证明");
                                holder.setTextColor(getResources().getColor(R.color.color_gray));
                            }
                        }
                    }
                    break;
            }
        }

        @Override
        public int getItemCount() {
            if (mList != null && mList.size() > 0) {
                return 3;
            } else {
                return 0;
            }
        }

        public void setList(List<FileState.Data> list) {
            mList = list;
            notifyDataSetChanged();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout mLayout;

        private ImageView mImgPhoto;

        private TextView mText;

        public ViewHolder(View itemView) {
            super(itemView);
            mLayout = itemView.findViewById(R.id.layout);
            mLayout.setOnClickListener(v -> {
                switch (getLayoutPosition()) {
                    case 0:
                        mPresenter.setFolder(FileContract.FOLDER_WAGE);
                        break;
                    case 1:
                        mPresenter.setFolder(FileContract.FOLDER_CREDIT);
                        break;
                    case 2:
                        mPresenter.setFolder(FileContract.FOLDER_ADDRESS);
                        break;
                }
                mBottomDialog.show();
            });
            mImgPhoto = itemView.findViewById(R.id.img_photo);
            mText = itemView.findViewById(R.id.text);
        }

        public void setBackground(int resId) {
            mLayout.setBackgroundResource(resId);
        }

        public void setPhotoVisibility(boolean visibility) {
            if (visibility) {
                mImgPhoto.setVisibility(View.VISIBLE);
            } else {
                mImgPhoto.setVisibility(View.INVISIBLE);
            }
        }

        public void setText(String text) {
            mText.setText(text);
        }

        public void setTextColor(int color) {
            mText.setTextColor(color);
        }
    }
}
