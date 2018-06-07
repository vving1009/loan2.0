package com.jiaye.cashloan.view.step1;

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
import com.jiaye.cashloan.http.data.step1.Step1;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.FunctionActivity;
import com.jiaye.cashloan.view.step1.source.Step1Repository;
import com.jiaye.cashloan.widget.StepView;

/**
 * Step1Fragment
 *
 * @author 贾博瑄
 */

public class Step1Fragment extends BaseFragment implements Step1Contract.View {

    private Step1Contract.Presenter mPresenter;

    private RecyclerView mRecyclerView;

    private Adapter mAdapter;

    public static Step1Fragment newInstance() {
        Bundle args = new Bundle();
        Step1Fragment fragment = new Step1Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.step1_fragment, container, false);
        mRecyclerView = root.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new Adapter();
        mRecyclerView.setAdapter(mAdapter);
        mPresenter = new Step1Presenter(this, new Step1Repository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void setStep1(Step1 step1) {
        mAdapter.setStep1(step1);
        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public void showIDView() {
        FunctionActivity.function(getActivity(), "ID");
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private Step1 mStep1;

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
                    holder.setState(mStep1.getId());
                    break;
                case 1:
                    holder.setType(1);
                    holder.setName("人像对比");
                    holder.setState(mStep1.getBioassay());
                    break;
                case 2:
                    holder.setType(1);
                    holder.setName("个人资料 ");
                    holder.setState(mStep1.getPersonal());
                    break;
                case 3:
                    holder.setType(1);
                    holder.setName("手机运营商");
                    holder.setState(mStep1.getPhone());
                    break;
                case 4:
                    holder.setType(2);
                    holder.setName("车辆证件");
                    holder.setState(mStep1.getCar());
                    break;
            }
        }

        @Override
        public int getItemCount() {
            if (mStep1 != null) {
                return 5;
            } else {
                return 0;
            }
        }

        public void setStep1(Step1 step1) {
            this.mStep1 = step1;
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

        public void setOnClickListener() {

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
    }
}
