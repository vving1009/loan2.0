package com.jiaye.cashloan.view.view.loan;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.FunctionActivity;
import com.jiaye.cashloan.view.data.loan.LoanAuthModel;
import com.jiaye.cashloan.view.data.loan.source.LoanAuthRepository;
import com.jiaye.cashloan.view.taobao.TaoBaoFragment;
import com.jiaye.cashloan.view.view.loan.auth.face.LoanAuthFaceActivity;
import com.jiaye.cashloan.view.view.loan.auth.file.LoanAuthFileActivity;
import com.jiaye.cashloan.view.view.loan.auth.info.LoanAuthInfoActivity;
import com.jiaye.cashloan.view.view.loan.auth.visa.LoanAuthVisaActivity;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

/**
 * LoanAuthFragment
 *
 * @author 贾博瑄
 */

public class LoanAuthFragment extends BaseFragment implements LoanAuthContract.View, EasyPermissions.PermissionCallbacks {

    private static final int REQUEST_OCR_PERMISSION = 101;

    private static final int REQUEST_FACE_PERMISSION = 102;

    private static final int REQUEST = 200;

    private LoanAuthContract.Presenter mPresenter;

    private Adapter mAdapter;

    private Button btnNext;

    private String[] cameraPermissions = {Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static LoanAuthFragment newInstance() {
        Bundle args = new Bundle();
        LoanAuthFragment fragment = new LoanAuthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            result();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.loan_auth_fragment, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recycler);
        mAdapter = new Adapter();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(mAdapter);
        root.findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        btnNext = root.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.confirm();
            }
        });
        
        mPresenter = new LoanAuthPresenter(this, new LoanAuthRepository());
        mPresenter.subscribe();
        
        hasPermission();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.requestLoanAuth();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void setList(List<LoanAuthModel> list) {
        mAdapter.setList(list);
    }

    @Override
    public void setNextEnable() {
        btnNext.setEnabled(true);
    }

    @Override
    public void showLoanAuthOCRView() {
        if (EasyPermissions.hasPermissions(getContext(), cameraPermissions)) {
            showLoanAuthOCRGranted();
        } else {
            EasyPermissions.requestPermissions(this, REQUEST_OCR_PERMISSION, cameraPermissions);
        }
    }

    @Override
    public void showLoanAuthVisaView() {
        Intent intent = new Intent(getActivity(), LoanAuthVisaActivity.class);
        intent.putExtra("type", "visa");
        startActivity(intent);
    }

    @Override
    public void showLoanAuthVisaHistoryView() {
        Intent intent = new Intent(getActivity(), LoanAuthVisaActivity.class);
        intent.putExtra("type", "visa_history");
        startActivity(intent);
    }

    @Override
    public void showLoanAuthFaceView() {
        if (EasyPermissions.hasPermissions(getContext(), cameraPermissions)) {
            showLoanAuthFaceGranted();
        } else {
            EasyPermissions.requestPermissions(this, REQUEST_FACE_PERMISSION, cameraPermissions);
        }
    }

    @Override
    public void showLoanAuthInfoView() {
        Intent intent = new Intent(getActivity(), LoanAuthInfoActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoanAuthPhoneView() {
        FunctionActivity.function(getActivity(), "Mobile");
    }

    @Override
    public void showLoanAuthTaoBaoView() {
        FunctionActivity.function(getActivity(), "Taobao");
    }

    @Override
    public void showLoanFileView() {
        Intent intent = new Intent(getActivity(), LoanAuthFileActivity.class);
        startActivityForResult(intent, REQUEST);
    }

    @Override
    public void exitView() {
        getActivity().finish();
    }

    private void result() {
        getActivity().finish();
    }

    private void hasPermission() {
        // READ_CONTACTS和READ_EXTERNAL_STORAGE和LOCATION权限已在HomeFragment申请
        if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.READ_CONTACTS)) {
            mPresenter.uploadContact();
        }
        if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            mPresenter.uploadPhoto();
        }
    }

    private void showLoanAuthOCRGranted() {
    }

    private void showLoanAuthFaceGranted() {
        Intent intent = new Intent(getActivity(), LoanAuthFaceActivity.class);
        startActivity(intent);
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private List<LoanAuthModel> mList;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.loan_auth_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.setListener(new OnClickViewHolderListener() {
                @Override
                public void onClickViewHolder(ViewHolder viewHolder) {
                    mPresenter.selectLoanAuthModel(mList.get(viewHolder.getLayoutPosition()));
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setIcon(getResources().getDrawable(mList.get(position).getIcon()));
            holder.setIconBackground(getResources().getDrawable(mList.get(position).getIcBackground()));
            holder.setName(getResources().getText(mList.get(position).getName()).toString());
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

        public void setList(List<LoanAuthModel> list) {
            mList = list;
            notifyDataSetChanged();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private OnClickViewHolderListener mListener;

        private LinearLayout mLayout;

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
            mImgName = itemView.findViewById(R.id.img_name);
            mText = itemView.findViewById(R.id.text);
            mImgState = itemView.findViewById(R.id.img_state);
        }

        public void setListener(OnClickViewHolderListener listener) {
            mListener = listener;
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

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == REQUEST_FACE_PERMISSION &&
                EasyPermissions.hasPermissions(getContext(), cameraPermissions)) {
            showLoanAuthFaceGranted();
        }
        if (requestCode == REQUEST_OCR_PERMISSION &&
                EasyPermissions.hasPermissions(getContext(), cameraPermissions)) {
            showLoanAuthOCRGranted();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        StringBuilder sb = new StringBuilder();
        String s;
        for (String perm : perms) {
            switch (perm) {
                case Manifest.permission.CAMERA:
                    s = "打开摄像头，";
                    break;
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                    s = "写存储设备，";
                    break;
                default:
                    s = "";
                    break;
            }
            sb.append(s);
        }
        sb.deleteCharAt(sb.lastIndexOf("，"));
        new AppSettingsDialog
                .Builder(this)
                .setTitle("权限申请")
                .setRationale("此功能需要" + sb.toString() + "权限，否则无法正常使用，是否打开设置？")
                .setPositiveButton("好")
                .setNegativeButton("不行")
                .build()
                .show();
    }
}
