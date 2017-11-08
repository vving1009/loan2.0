package com.jiaye.cashloan.view.view.loan;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.loan.LoanAuthModel;
import com.jiaye.cashloan.view.view.loan.auth.LoanAuthOCRActivity;
import com.jiaye.cashloan.view.view.loan.auth.LoanAuthFaceActivity;

import java.util.List;

import static android.support.v4.content.ContextCompat.checkSelfPermission;
import static com.jiaye.cashloan.view.view.loan.auth.LoanAuthOCRActivity.REQUEST_OCR;
import static com.jiaye.cashloan.view.view.loan.auth.LoanAuthFaceActivity.REQUEST_FACE;

/**
 * LoanAuthFragment
 *
 * @author 贾博瑄
 */

public class LoanAuthFragment extends BaseFragment implements LoanAuthContract.View {

    private static final int REQUEST_CARD_PERMISSION = 101;

    private static final int REQUEST_FACE_PERMISSION = 102;

    private LoanAuthContract.Presenter mPresenter;

    private Adapter mAdapter;

    public static LoanAuthFragment newInstance() {
        Bundle args = new Bundle();
        LoanAuthFragment fragment = new LoanAuthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_OCR && resultCode == REQUEST_OCR) {
            boolean isSuccess = data.getBooleanExtra("is_success", false);
            if (isSuccess) {
                showToastById(R.string.loan_auth_ocr_success);
            } else {
                showToastById(R.string.loan_auth_ocr_fail);
            }
        } else if (requestCode == REQUEST_FACE && resultCode == REQUEST_FACE) {
            boolean isSuccess = data.getBooleanExtra("is_success", false);
            if (isSuccess) {
                showToastById(R.string.loan_auth_face_success);
            } else {
                showToastById(R.string.loan_auth_face_fail);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CARD_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(getActivity(), LoanAuthOCRActivity.class);
                startActivity(intent);
            } else {
                showToastById(R.string.error_loan_auth_camera);
            }
        } else if (requestCode == REQUEST_FACE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(getActivity(), LoanAuthFaceActivity.class);
                startActivity(intent);
            } else {
                showToastById(R.string.error_loan_auth_camera);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.loan_auth_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler);
        mAdapter = new Adapter(getActivity(), new OnClickCardListener() {
            @Override
            public void onClickCard(LoanAuthModel model) {
                mPresenter.selectLoanAuthModel(model);
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(mAdapter);
        root.findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        root.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.subscribe();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.requestLoanAuth();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(LoanAuthContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setList(List<LoanAuthModel> list) {
        mAdapter.setList(list);
    }

    @Override
    public void startLoanAuthCardView() {
        if (checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CARD_PERMISSION);
        } else {
            Intent intent = new Intent(getActivity(), LoanAuthOCRActivity.class);
            startActivityForResult(intent, REQUEST_FACE);
        }
    }

    @Override
    public void startLoanAuthFaceView() {
        if (checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_FACE_PERMISSION);
        } else {
            Intent intent = new Intent(getActivity(), LoanAuthFaceActivity.class);
            startActivityForResult(intent, REQUEST_FACE);
        }
    }

    @Override
    public void startLoanAuthPersonView() {

    }

    @Override
    public void startLoanAuthPhoneView() {

    }

    @Override
    public void startLoanAuthTaoBaoView() {

    }

    @Override
    public void startLoanAuthSesameView() {

    }

    private static class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private Context mContext;

        private OnClickCardListener mListener;

        private List<LoanAuthModel> mList;

        public Adapter(Context context, OnClickCardListener listener) {
            mContext = context;
            mListener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.loan_auth_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.setListener(new ViewHolder.OnClickViewHolderListener() {
                @Override
                public void onClickViewHolder(ViewHolder viewHolder) {
                    mListener.onClickCard(mList.get(viewHolder.getLayoutPosition()));
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setIcon(mContext.getResources().getDrawable(mList.get(position).getIcon()));
            holder.setIconBackground(mContext.getResources().getDrawable(mList.get(position).getIcBackground()));
            holder.setName(mContext.getResources().getText(mList.get(position).getName()).toString());
            holder.setColor(mContext.getResources().getColor(mList.get(position).getColor()));
            holder.setState(mContext.getResources().getDrawable(mList.get(position).getIcState()));
            holder.setBackground(mContext.getResources().getDrawable(mList.get(position).getBackground()));
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

    private interface OnClickCardListener {

        void onClickCard(LoanAuthModel model);
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private interface OnClickViewHolderListener {

            void onClickViewHolder(ViewHolder viewHolder);
        }

        private OnClickViewHolderListener mListener;

        private LinearLayout mLayout;

        private ImageView mImgName;

        private TextView mText;

        private ImageView mImgState;

        public ViewHolder(View itemView) {
            super(itemView);
            mLayout = (LinearLayout) itemView.findViewById(R.id.layout_card);
            mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onClickViewHolder(ViewHolder.this);
                    }
                }
            });
            mImgName = (ImageView) itemView.findViewById(R.id.img_name);
            mText = (TextView) itemView.findViewById(R.id.text);
            mImgState = (ImageView) itemView.findViewById(R.id.img_state);
        }

        public void setListener(ViewHolder.OnClickViewHolderListener listener) {
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
}
