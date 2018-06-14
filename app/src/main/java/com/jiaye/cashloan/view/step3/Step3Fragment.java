package com.jiaye.cashloan.view.step3;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.step3.Step3;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.FunctionActivity;
import com.jiaye.cashloan.view.certification.CertificationFragment;
import com.jiaye.cashloan.view.step1.BaseStepFragment;
import com.jiaye.cashloan.view.step3.source.Step3Repository;
import com.jiaye.cashloan.widget.StepView;

/**
 * Step3Fragment
 *
 * @author 贾博瑄
 */

public class Step3Fragment extends BaseStepFragment implements Step3Contract.View {

    private Step3Contract.Presenter mPresenter;

    private Adapter mAdapter;

    public static Step3Fragment newInstance() {
        Bundle args = new Bundle();
        Step3Fragment fragment = new Step3Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View root = inflater.inflate(R.layout.step3_fragment, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new Adapter();
        recyclerView.setAdapter(mAdapter);
        root.findViewById(R.id.btn_next).setOnClickListener(v -> mPresenter.onClickNext());
        mPresenter = new Step3Presenter(this, new Step3Repository());
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
    public void setStep3(Step3 step3) {
        mAdapter.setStep3(step3);
    }

    @Override
    public void showTaoBaoView() {
        FunctionActivity.function(getActivity(), "Taobao");
    }

    @Override
    public void showFileView() {
        FunctionActivity.function(getActivity(), "File");
    }

    @Override
    public void showSignView() {
        FunctionActivity.function(getActivity(), "Sign");
    }

    @Override
    public void sendBroadcast() {
        CertificationFragment.refresh(getActivity());
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private Step3 mStep3;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.step_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (position == 0) {
                holder.setType(0);
                holder.setName("淘宝/支付宝");
                holder.setState(mStep3.getTaobao());
                if (mStep3.getTaobao() == 0) {
                    holder.setStateText("待提交");
                } else if (mStep3.getTaobao() == 1) {
                    holder.setStateText("已认证");
                }
            } else if (position == 1) {
                holder.setType(1);
                holder.setName("进件资料");
                holder.setState(mStep3.getFile());
                if (mStep3.getFile() == 0) {
                    holder.setStateText("待提交");
                } else if (mStep3.getFile() == 1) {
                    holder.setStateText("已认证");
                }
            } else if (position == 2) {
                holder.setType(2);
                holder.setName("电子签章授权");
                holder.setState(mStep3.getSign());
                if (mStep3.getSign() == 0) {
                    holder.setStateText("待授权");
                } else if (mStep3.getSign() == 1) {
                    holder.setStateText("已授权");
                }
            }
        }

        @Override
        public int getItemCount() {
            if (mStep3 != null) {
                return 3;
            } else {
                return 0;
            }
        }

        public void setStep3(Step3 step3) {
            mStep3 = step3;
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
                    mImageInto.setVisibility(View.VISIBLE);
                    mStepView.setSelect(false);
                    break;
                case 1:
                    mTextState.setTextColor(Color.parseColor("#425FBB"));
                    mImageInto.setVisibility(View.INVISIBLE);
                    mStepView.setSelect(true);
                    break;
            }
        }

        public void setStateText(String text) {
            mTextState.setText(text);
        }
    }
}
