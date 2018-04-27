package com.jiaye.cashloan.view.view.loan.auth.file;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.data.loan.auth.source.file.LoanAuthFileModel;
import com.jiaye.cashloan.view.data.loan.auth.source.file.LoanAuthFileRepository;
import com.jiaye.cashloan.view.view.loan.LoanProgressActivity;
import com.jph.takephoto.app.TakePhotoAppCompatActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TResult;

import java.io.File;
import java.util.List;

/**
 * LoanAuthFileActivity
 *
 * @author 贾博瑄
 */
public class LoanAuthFileActivity extends TakePhotoAppCompatActivity implements LoanAuthFileContract.View {

    private LoanAuthFileContract.Presenter mPresenter;

    protected ProgressDialog mDialog;

    private Adapter mAdapter;

    private BottomSheetDialog mBottomDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_auth_file_activity);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        mAdapter = new Adapter();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(mAdapter);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mBottomDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.photo_layout, null);
        layout.findViewById(R.id.text_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomDialog.dismiss();
                mPresenter.camera();
            }
        });
        layout.findViewById(R.id.text_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomDialog.dismiss();
                mPresenter.photo();
            }
        });
        layout.findViewById(R.id.text_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomDialog.dismiss();
            }
        });
        mBottomDialog.setContentView(layout);
        mPresenter = new LoanAuthFilePresenter(this, new LoanAuthFileRepository());
        mPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        mPresenter.upload(result.getImages());
    }

    @Override
    public void setList(List<LoanAuthFileModel> list) {
        mAdapter.setList(list);
    }

    @Override
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

    @Override
    public void photo() {
        CompressConfig compressConfig = CompressConfig.ofDefaultConfig();
        compressConfig.enableReserveRaw(false);
        compressConfig.setMaxSize(500 * 1024);
        getTakePhoto().onEnableCompress(compressConfig, false);
        getTakePhoto().onPickMultiple(9);
    }

    @Override
    public void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToastById(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(this);
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
        }
        mDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private List<LoanAuthFileModel> mList;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(LoanAuthFileActivity.this).inflate(R.layout.loan_auth_file_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.setListener(new OnClickViewHolderListener() {
                @Override
                public void onClickViewHolder(ViewHolder viewHolder) {
                    int type = mList.get(viewHolder.getLayoutPosition()).getType();
                    mPresenter.setType(type);
                    mBottomDialog.show();
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setIcon(getResources().getDrawable(mList.get(position).getIcon()));
            holder.setIconBackground(getResources().getDrawable(mList.get(position).getIcBackground()));
            holder.setName(mList.get(position).getName());
            holder.setCount(mList.get(position).getCount());
            holder.setColor(getResources().getColor(mList.get(position).getColor()));
            holder.setState(getResources().getDrawable(mList.get(position).getIcState()));
            holder.setBackground(getResources().getDrawable(mList.get(position).getBackground()));
        }

        @Override
        public int getItemCount() {
            if (mList != null && mList.size() > 0) {
                return mList.size();
            } else {
                return 0;
            }
        }

        public void setList(List<LoanAuthFileModel> list) {
            mList = list;
            notifyDataSetChanged();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private OnClickViewHolderListener mListener;

        private LinearLayout mLayout;

        private TextView mTextCount;

        private ImageView mImgName;

        private TextView mText;

        private ImageView mImgState;

        public ViewHolder(View itemView) {
            super(itemView);
            mLayout = itemView.findViewById(R.id.layout_card);
            mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onClickViewHolder(ViewHolder.this);
                    }
                }
            });
            mTextCount = itemView.findViewById(R.id.text_count);
            mImgName = itemView.findViewById(R.id.img_name);
            mText = itemView.findViewById(R.id.text);
            mImgState = itemView.findViewById(R.id.img_state);
        }

        public void setListener(OnClickViewHolderListener listener) {
            mListener = listener;
        }

        public void setCount(int count) {
            mTextCount.setText(String.format(getString(R.string.loan_auth_file_count), count));
        }

        public void setIcon(Drawable drawable) {
            mImgName.setImageDrawable(drawable);
        }

        public void setIconBackground(Drawable drawable) {
            mImgName.setBackgroundDrawable(drawable);
        }

        public void setName(String text) {
            mText.setText(text);
        }

        public void setColor(int color) {
            mText.setTextColor(color);
            mTextCount.setTextColor(color);
        }

        public void setState(Drawable drawable) {
            mImgState.setImageDrawable(drawable);
        }

        public void setBackground(Drawable drawable) {
            mLayout.setBackgroundDrawable(drawable);
        }
    }

    private interface OnClickViewHolderListener {

        void onClickViewHolder(ViewHolder viewHolder);
    }
}
