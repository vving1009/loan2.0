package com.jiaye.cashloan.view.step1.old;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.step2.Step2Input;
import com.jiaye.cashloan.service.UploadFaceService;
import com.jiaye.cashloan.view.FunctionActivity;
import com.jiaye.cashloan.view.bioassay.BioassayActivity;
import com.jiaye.cashloan.view.certification.CertificationFragment;
import com.jiaye.cashloan.view.step1.BaseStepFragment;
import com.jiaye.cashloan.view.step1.old.source.Step1Repository;
import com.jiaye.cashloan.widget.StepView;
import com.moxie.client.model.MxParam;
import com.satcatche.library.widget.SatcatcheDialog;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Step2InputFragment
 *
 * @author 贾博瑄
 */

public class Step1Fragment extends BaseStepFragment implements Step1Contract.View, EasyPermissions.PermissionCallbacks {

    private final int CAMERA_PERMS_REQUEST_CODE = 101;

    private Step1Contract.Presenter mPresenter;

    private RecyclerView mRecyclerView;

    private Adapter mAdapter;

    @SuppressLint("InlinedApi")
    private final String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static Step1Fragment newInstance() {
        Bundle args = new Bundle();
        Step1Fragment fragment = new Step1Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.step1_fragment, container, false);
        mRecyclerView = root.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new Adapter();
        mRecyclerView.setAdapter(mAdapter);
        root.findViewById(R.id.btn_next).setOnClickListener(v -> mPresenter.onClickNext());
        mPresenter = new Step1Presenter(this, new Step1Repository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.requestStep();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    protected void requestStep() {
        mPresenter.requestStep();
    }

    @Override
    public void setStep1(Step2Input step2Input) {
        mAdapter.setStep1(step2Input);
        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public void showIDView() {
        FunctionActivity.function(getActivity(), "ID");
    }

    @Override
    public void showBioassayView() {
        EasyPermissions.requestPermissions(this, CAMERA_PERMS_REQUEST_CODE, permissions);
    }

    @Override
    public void showInfoView() {
        FunctionActivity.function(getActivity(), "Info");
    }

    @Override
    public void showPhoneView() {
        FunctionActivity.function(getActivity(), MxParam.PARAM_TASK_CARRIER);
    }

    @Override
    public void showVehicleView() {
        FunctionActivity.function(getActivity(), "Vehicle");
        UploadFaceService.startUploadFaceService(getContext());
    }

    @Override
    public void sendBroadcast() {
        CertificationFragment.refresh(getActivity());
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private Step2Input mStep2Input;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.step_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (position == 0) {
                holder.setType(0);
            } else if (position == getItemCount() - 1) {
                holder.setType(2);
            } else {
                holder.setType(1);
            }
            switch (position) {
                case 0:
                    holder.setType(0);
                    holder.setName("身份认证");
                    //holder.setState(mStep2Input.getId());
                    break;
                case 1:
                    holder.setType(1);
                    holder.setName("人像对比");
                    holder.setState(mStep2Input.getBioassay());
                    //holder.setReady(mStep2Input.getId() == 1);
                    break;
                case 2:
                    holder.setType(1);
                    holder.setName("个人资料 ");
                    holder.setState(mStep2Input.getPersonal());
                    holder.setReady(mStep2Input.getBioassay() == 1);
                    break;
                case 3:
                    holder.setType(1);
                    holder.setName("手机运营商");
                    holder.setState(mStep2Input.getPhone());
                    holder.setReady(mStep2Input.getPersonal() == 1);
                    break;
                case 4:
                    holder.setType(2);
                    holder.setName("车辆证件");
                    holder.setState(mStep2Input.getCar());
                    holder.setReady(mStep2Input.getPhone() == 1);
                    break;
            }
            holder.invalidate();
        }

        @Override
        public int getItemCount() {
            if (mStep2Input != null) {
                return 5;
            } else {
                return 0;
            }
        }

        public void setStep1(Step2Input step2Input) {
            this.mStep2Input = step2Input;
            notifyDataSetChanged();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private StepView mStepView;

        private TextView mTextName;

        private TextView mTextState;

        private ImageView mImageInto;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> mPresenter.onClickItem(getLayoutPosition()));
            mStepView = itemView.findViewById(R.id.step_view);
            mTextName = itemView.findViewById(R.id.text_name);
            mTextState = itemView.findViewById(R.id.text_state);
            mImageInto = itemView.findViewById(R.id.img_into);
        }

        public void setType(int type) {
            mStepView.setType(type);
        }

        public void setName(String name) {
            mTextName.setText(name);
        }

        public void setState(int state) {
            switch (state) {
                case 0:
                    mTextState.setTextColor(Color.parseColor("#989898"));
                    mTextState.setText("待认证");
                    mImageInto.setVisibility(View.VISIBLE);
                    mStepView.setSelect(false);
                    break;
                case 1:
                    mTextState.setTextColor(Color.parseColor("#425FBB"));
                    mTextState.setText("已认证");
                    mImageInto.setVisibility(View.INVISIBLE);
                    mStepView.setSelect(true);
                    break;
            }
        }

        public void setReady(boolean ready) {
            mStepView.setReady(ready);
        }

        public void invalidate() {
            mStepView.invalidate();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.hasPermissions(getContext(), permissions)) {
            Intent intent = new Intent(getActivity(), BioassayActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        StringBuilder sb = new StringBuilder();
        String s;
        for (String perm : perms) {
            switch (perm) {
                case Manifest.permission.CAMERA:
                    s = "相机，";
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
        new SatcatcheDialog.Builder(getContext())
                .setTitle("权限申请")
                .setMessage("人像对比需要" + sb.toString() + "权限，否则不能进行，是否打开设置？")
                .setPositiveButton("好", (dialog, which) ->
                        startActivityForResult(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                        .setData(Uri.fromParts("package", getContext().getPackageName(), null)),
                                CAMERA_PERMS_REQUEST_CODE))
                .setNegativeButton("不行", null)
                .build()
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyPermissions.requestPermissions(this, CAMERA_PERMS_REQUEST_CODE, permissions);
    }
}
