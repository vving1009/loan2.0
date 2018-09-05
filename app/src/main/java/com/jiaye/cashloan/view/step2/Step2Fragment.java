package com.jiaye.cashloan.view.step2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.step2.Step2;
import com.jiaye.cashloan.service.UploadFaceService;
import com.jiaye.cashloan.view.FunctionActivity;
import com.jiaye.cashloan.view.bioassay.BioassayActivity;
import com.jiaye.cashloan.view.certification.CertificationFragment;
import com.jiaye.cashloan.view.step1.BaseStepFragment;
import com.jiaye.cashloan.view.step2.source.Step2Repository;
import com.jiaye.cashloan.widget.StepView;
import com.moxie.client.model.MxParam;
import com.satcatche.library.widget.SatcatcheDialog;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Step2Fragment
 *
 * @author 贾博瑄
 */

public class Step2Fragment extends BaseStepFragment implements Step2Contract.View, EasyPermissions.PermissionCallbacks {

    private final int CAMERA_PERMS_REQUEST_CODE = 101;

    private Step2Contract.Presenter mPresenter;

    private TextView mTextTitle;

    private TextView mTextSubTitle;

    private RecyclerView mRecyclerView;

    private LinearLayout mLayoutResult;

    private TextView mTextResult;

    private Adapter mAdapter;

    private Button mBtnNext;

    @SuppressLint("InlinedApi")
    private final String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static Step2Fragment newInstance() {
        Bundle args = new Bundle();
        Step2Fragment fragment = new Step2Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.step2_fragment, container, false);
        mTextTitle = root.findViewById(R.id.title);
        mTextSubTitle = root.findViewById(R.id.sub_title);
        mRecyclerView = root.findViewById(R.id.recycler_view);
        mLayoutResult = root.findViewById(R.id.result_layout);
        mTextResult = root.findViewById(R.id.result_text);
        mBtnNext = root.findViewById(R.id.btn_next);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new Adapter();
        mRecyclerView.setAdapter(mAdapter);
        showInputView();

        mPresenter = new Step2Presenter(this, new Step2Repository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (((CertificationFragment) getParentFragment()).getCurrentFragmentIndex() == 2) {
            mPresenter.requestStep();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    public void showInputView() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mLayoutResult.setVisibility(View.GONE);
        mTextTitle.setText("提交信息");
        mTextSubTitle.setText("提交信息后给出您的借款额度");
        mBtnNext.setText("提交申请");
        mBtnNext.setOnClickListener(v -> mPresenter.onClickNext());
    }

    @Override
    public void showResultView(String price) {
        mRecyclerView.setVisibility(View.GONE);
        mLayoutResult.setVisibility(View.VISIBLE);
        mTextTitle.setText("借款额度");
        mTextSubTitle.setText("请您到店进行后续手续办理");
        mTextResult.setText(getResources().getString(R.string.step2_loan_limit, price));
        mBtnNext.setText("获取最终额度");
        mBtnNext.setOnClickListener(v -> mPresenter.requestUpdateStep());
    }

    @Override
    protected void requestStep() {
        mPresenter.requestStep();
    }

    @Override
    public void sendBroadcast() {
        CertificationFragment.refresh(getActivity());
    }

    @Override
    public void setStep2(Step2 step2) {
        mAdapter.setStep2(step2);
        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public void showInsuranceView() {
        FunctionActivity.function(getActivity(), MxParam.PARAM_TASK_INSURANCE);
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
    public void showTaobaoView() {
        FunctionActivity.function(getActivity(), MxParam.PARAM_TASK_TAOBAO);
    }

    @Override
    public void showVehicleView() {
        FunctionActivity.function(getActivity(), "Vehicle");
        UploadFaceService.startUploadFaceService(getContext());
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private Step2 mStep2;

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
                    holder.setTextName("保单");
                    holder.setState(mStep2.getCarinsuranceAuth());
                    break;
                case 1:
                    holder.setTextName("身份认证");
                    holder.setState(mStep2.getId());
                    holder.setReady(mStep2.getCarinsuranceAuth() == 1);
                    break;
                case 2:
                    holder.setTextName("人像对比");
                    holder.setState(mStep2.getBioassayAuth());
                    holder.setReady(mStep2.getId() == 1);
                    break;
                case 3:
                    holder.setTextName("个人资料 ");
                    holder.setState(mStep2.getUserInfo());
                    holder.setReady(mStep2.getBioassayAuth() == 1);
                    break;
                case 4:
                    holder.setTextName("手机运营商");
                    holder.setState(mStep2.getOperatorAuth());
                    holder.setReady(mStep2.getUserInfo() == 1);
                    break;
                case 5:
                    holder.setTextName("淘宝/支付宝");
                    holder.setState(mStep2.getTaobaoAuth());
                    holder.setReady(mStep2.getOperatorAuth() == 1);
                    break;
                case 6:
                    holder.setTextName("车辆证件");
                    holder.setState(mStep2.getCarPapers());
                    holder.setReady(mStep2.getTaobaoAuth() == 1);
                    break;
            }
            holder.invalidate();
        }

        @Override
        public int getItemCount() {
            if (mStep2 != null) {
                return 7;
            } else {
                return 0;
            }
        }

        public void setStep2(Step2 step2) {
            this.mStep2 = step2;
            notifyDataSetChanged();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private StepView mStepView;

        private TextView mTextName;

        private TextView mTextState;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> mPresenter.onClickItem(getLayoutPosition()));
            mStepView = itemView.findViewById(R.id.step_view);
            mTextName = itemView.findViewById(R.id.text_name);
            mTextState = itemView.findViewById(R.id.text_state);
        }

        public void setType(int type) {
            mStepView.setType(type);
        }

        public void setTextName(String name) {
            mTextName.setText(name);
        }

        public void setState(int state) {
            int position = getLayoutPosition();
            switch (state) {
                case 0:
                    if (position == 0 || position == 3 || position == 6) {
                        mTextState.setText("待提交");
                    } else {
                        mTextState.setText("待认证");
                    }
                    mStepView.setSelect(false);
                    break;
                case 1:
                    if (position == 0 || position == 3 || position == 6) {
                        mTextState.setText("已提交");
                    } else {
                        mTextState.setText("已认证");
                    }
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
