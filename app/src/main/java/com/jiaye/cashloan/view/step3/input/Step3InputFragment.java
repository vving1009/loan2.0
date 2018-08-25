package com.jiaye.cashloan.view.step3.input;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.step3.Step3;
import com.jiaye.cashloan.view.FunctionActivity;
import com.jiaye.cashloan.view.certification.CertificationFragment;
import com.jiaye.cashloan.view.step1.BaseStepFragment;
import com.jiaye.cashloan.view.step3.input.source.Step3InputRepository;
import com.jiaye.cashloan.view.step3.parent.Step3Fragment;
import com.jiaye.cashloan.widget.StepView;
import com.moxie.client.model.MxParam;

/**
 * Step3InputFragment
 *
 * @author 贾博瑄
 */

public class Step3InputFragment extends BaseStepFragment implements Step3InputContract.View {

    private Step3InputContract.Presenter mPresenter;

    private Adapter mAdapter;

    private RecyclerView mRecyclerView;

    private Button mNextBtn;

    private Step3Fragment.OnNextClickListener mOnNextClickListener;

    public static Step3InputFragment newInstance() {
        Bundle args = new Bundle();
        Step3InputFragment fragment = new Step3InputFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.step3_input_fragment, container, false);
        mNextBtn = root.findViewById(R.id.btn_next);
        mRecyclerView = root.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new Adapter();
        mRecyclerView.setAdapter(mAdapter);
        mNextBtn = root.findViewById(R.id.btn_next);
        mNextBtn.setOnClickListener(v -> mPresenter.onClickNext());
        mPresenter = new Step3InputPresenter(this, new Step3InputRepository());
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
    public void setStep3(Step3InputState step3) {
        mAdapter.setStep3(step3);
    }

    @Override
    public void showCompanyView() {
        FunctionActivity.function(getActivity(), "File");
    }

    @Override
    public void showSalesmanView() {
        FunctionActivity.function(getActivity(), "Sign");
    }

    @Override
    public void sendBroadcast() {
        CertificationFragment.refresh(getActivity());
    }

    @Override
    public void showResultView() {
        mOnNextClickListener.onClick();
    }

    public void setOnNextClickListener(Step3Fragment.OnNextClickListener onNextClickListener) {
        mOnNextClickListener = onNextClickListener;
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private Step3InputState mStep3;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.step_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            switch (position) {
                case 0:
                    holder.setType(0);
                    holder.setTextName("分公司");
                    holder.setTextState(mStep3.getList().get(position));
                    holder.setState(mStep3.isFinishItem0());
                    break;
                case 1:
                    holder.setType(2);
                    holder.setTextName("客户经理");
                    holder.setTextState(mStep3.getList().get(position));
                    holder.setState(mStep3.isFinishItem1());
                    holder.setReady(mStep3.isFinishItem0());
                    break;
            }
            holder.invalidate();
        }

        @Override
        public int getItemCount() {
            /*if (mStep3 != null) {
                return 2;
            } else {
                return 0;
            }*/
            return 2;
        }

        public void setStep3(Step3InputState step3) {
            this.mStep3 = step3;
            notifyDataSetChanged();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private StepView mStepView;

        private TextView mTextName;

        private TextView mTextState;

        //private ImageView mImageInto;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> mPresenter.onClickItem(getLayoutPosition()));
            mStepView = itemView.findViewById(R.id.step_view);
            mTextName = itemView.findViewById(R.id.text_name);
            mTextState = itemView.findViewById(R.id.text_state);
            //mImageInto = itemView.findViewById(R.id.img_into);
        }

        public void setType(int type) {
            mStepView.setType(type);
        }

        public void setTextName(String name) {
            mTextName.setText(name);
        }

        public void setTextState(String state) {
            mTextState.setText(state);
        }

        public void setState(boolean state) {
            if (state) {
                //mTextState.setTextColor(Color.parseColor("#425FBB"));
                //mImageInto.setVisibility(View.INVISIBLE);
                mStepView.setSelect(true);
            } else {
                //mTextState.setTextColor(Color.parseColor("#989898"));
                //mImageInto.setVisibility(View.VISIBLE);
                mStepView.setSelect(false);
            }
        }

        public void setReady(boolean ready) {
            mStepView.setReady(ready);
        }

        public void invalidate() {
            mStepView.invalidate();
        }
    }
}
