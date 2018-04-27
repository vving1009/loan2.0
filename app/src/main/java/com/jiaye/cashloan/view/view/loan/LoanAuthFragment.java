package com.jiaye.cashloan.view.view.loan;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
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
import com.jiaye.cashloan.view.data.loan.LoanAuthModel;
import com.jiaye.cashloan.view.data.loan.source.LoanAuthRepository;
import com.jiaye.cashloan.view.view.loan.auth.face.LoanAuthFaceActivity;
import com.jiaye.cashloan.view.view.loan.auth.file.LoanAuthFileActivity;
import com.jiaye.cashloan.view.view.loan.auth.info.LoanAuthInfoActivity;
import com.jiaye.cashloan.view.view.loan.auth.ocr.LoanAuthOCRActivity;
import com.jiaye.cashloan.view.view.loan.auth.phone.LoanAuthPhoneActivity;
import com.jiaye.cashloan.view.view.loan.auth.sesame.LoanAuthSesameActivity;
import com.jiaye.cashloan.view.view.loan.auth.taobao.LoanAuthTaoBaoActivity;
import com.jiaye.cashloan.view.view.loan.auth.visa.LoanAuthVisaActivity;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * LoanAuthFragment
 *
 * @author 贾博瑄
 */

public class LoanAuthFragment extends BaseFragment implements LoanAuthContract.View {

    private static final int REQUEST_OCR_PERMISSION = 101;

    private static final int REQUEST_FACE_PERMISSION = 102;

    private static final int REQUEST_INFO_LOCATION_PERMISSION = 103;

    private static final int REQUEST_INFO_PERMISSION = 104;

    private static final int REQUEST_LOCATION_PERMISSION = 105;

    private static final int REQUEST = 200;

    private LoanAuthContract.Presenter mPresenter;

    private Adapter mAdapter;

    private Button btnNext;

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_OCR_PERMISSION:
                if (isGrant(grantResults)) {
                    showLoanAuthOCRGranted();
                } else {
                    showToastById(R.string.error_loan_auth_camera_and_write);
                }
                break;
            case REQUEST_FACE_PERMISSION:
                if (isGrant(grantResults)) {
                    showLoanAuthFaceGranted();
                } else {
                    showToastById(R.string.error_loan_auth_camera_and_write);
                }
                break;
            case REQUEST_INFO_LOCATION_PERMISSION:
                for (int i = 0; i < grantResults.length; i++) {
                    if (i == 0) {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            mPresenter.uploadContact();
                        }
                    } else if (i == 1) {
                        if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                            uploadLocation();
                        }
                    }
                }
                break;
            case REQUEST_INFO_PERMISSION:
                if (isGrant(grantResults)) { // 如果用户授权,获取通讯录并上传
                    mPresenter.uploadContact();
                }
                break;
            case REQUEST_LOCATION_PERMISSION:
                if (isGrant(grantResults)) { // 如果用户授权,获取地理位置并上传
                    uploadLocation();
                }
                break;
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
        if (hasCameraPermission(REQUEST_OCR_PERMISSION)) {
            showLoanAuthOCRGranted();
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
        if (hasCameraPermission(REQUEST_FACE_PERMISSION)) {
            showLoanAuthFaceGranted();
        }
    }

    @Override
    public void showLoanAuthInfoView() {
        Intent intent = new Intent(getActivity(), LoanAuthInfoActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoanAuthPhoneView() {
        Intent intent = new Intent(getActivity(), LoanAuthPhoneActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoanAuthTaoBaoView() {
        Intent intent = new Intent(getActivity(), LoanAuthTaoBaoActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoanFileView() {
        Intent intent = new Intent(getActivity(), LoanAuthFileActivity.class);
        startActivityForResult(intent, REQUEST);
    }

    @Override
    public void showLoanProgressView(String loanId) {
        Intent intent = new Intent(getActivity(), LoanProgressActivity.class);
        intent.putExtra("loanId", loanId);
        startActivity(intent);
        getActivity().finish();
    }

    private void result() {
        getActivity().finish();
    }

    private boolean isGrant(@NonNull int[] grantResults) {
        boolean grant = true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                grant = false;
            }
        }
        return grant;
    }

    private boolean hasCameraPermission(int requestCode) {
        boolean hasPermission = false;
        boolean requestCamera = checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
        boolean requestWrite = checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
        if (requestCamera && requestWrite) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
        } else if (requestCamera) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, requestCode);
        } else if (requestWrite) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
        } else {
            hasPermission = true;
        }
        return hasPermission;
    }

    private void hasPermission() {
        boolean requestContact = checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED;
        boolean requestFine = checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        if (!requestContact) {
            mPresenter.uploadContact();
        }
        if (!requestFine) {
            uploadLocation();
        }
        if (requestContact && requestFine) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_INFO_LOCATION_PERMISSION);
        } else if (requestContact) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_INFO_PERMISSION);
        } else if (requestFine) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }

    }

    private void showLoanAuthOCRGranted() {
        Intent intent = new Intent(getActivity(), LoanAuthOCRActivity.class);
        startActivity(intent);
    }

    private void showLoanAuthFaceGranted() {
        Intent intent = new Intent(getActivity(), LoanAuthFaceActivity.class);
        startActivity(intent);
    }

    private boolean checkGPSIsOpen() {
        boolean isOpen;
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        isOpen = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
        return isOpen;
    }

    private void uploadLocation() {
        if (checkGPSIsOpen()) {
            mPresenter.uploadLocation();
        }
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
}
