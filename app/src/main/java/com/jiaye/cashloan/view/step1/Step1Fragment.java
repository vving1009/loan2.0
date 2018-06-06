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

import java.util.List;

/**
 * Step1Fragment
 *
 * @author 贾博瑄
 */

public class Step1Fragment extends BaseFragment implements Step1Contract.View {

    private Step1Contract.Presenter mPresenter;

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
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new Adapter();
        recyclerView.setAdapter(mAdapter);
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
    public void setList(List<Step1> list) {
        mAdapter.setList(list);
    }

    @Override
    public void showIDView() {
        FunctionActivity.function(getActivity(), "ID");
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private List<Step1> mList;

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
            holder.setName(mList.get(position).getName());
            holder.setState(mList.get(position).getState());
        }

        @Override
        public int getItemCount() {
            if (mList == null || mList.size() == 0) {
                return 0;
            } else {
                return mList.size();
            }
        }

        public void setList(List<Step1> list) {
            this.mList = list;
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
