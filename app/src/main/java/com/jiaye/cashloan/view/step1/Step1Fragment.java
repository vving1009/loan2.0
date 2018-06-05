package com.jiaye.cashloan.view.step1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.step1.source.Step1Repository;
import com.jiaye.cashloan.widget.StepView;

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

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

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
        }

        @Override
        public int getItemCount() {
            return 5;
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private StepView mStepView;

        public ViewHolder(View itemView) {
            super(itemView);
            mStepView = itemView.findViewById(R.id.step_view);
        }

        public void setSelect(boolean select) {
            mStepView.setSelect(select);
        }

        public void setType(int type) {
            mStepView.setType(type);
        }
    }
}
