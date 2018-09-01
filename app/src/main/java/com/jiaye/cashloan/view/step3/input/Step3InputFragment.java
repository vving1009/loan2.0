package com.jiaye.cashloan.view.step3.input;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.persistence.Salesman;
import com.jiaye.cashloan.view.FunctionActivity;
import com.jiaye.cashloan.view.certification.CertificationFragment;
import com.jiaye.cashloan.view.step1.BaseStepFragment;
import com.jiaye.cashloan.view.step3.input.source.Step3InputRepository;
import com.jiaye.cashloan.view.step3.parent.Step3Fragment;
import com.jiaye.cashloan.widget.StepView;

/**
 * Step3InputFragment
 *
 * @author 贾博瑄
 */

public class Step3InputFragment extends BaseStepFragment implements Step3InputContract.View {

    public static final int REQUEST_CODE_COMPANY = 101;
    public static final int REQUEST_CODE_PERSON = 102;

    public static final String EXTRA_COMPANY_ID = "company_id";
    public static final String EXTRA_COMPANY_NAME = "company_name";
    public static final String EXTRA_PERSON_ID = "person_id";
    public static final String EXTRA_PERSON_NAME = "person_name";

    private Step3InputContract.Presenter mPresenter;

    private Adapter mAdapter;

    private RecyclerView mRecyclerView;

    private Button mNextBtn;

    private Step3Fragment.OnNextClickListener mOnNextClickListener;

    private Salesman mSalesman;

    private Step3InputState mStep3;

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
        mNextBtn.setOnClickListener(v -> mPresenter.onClickNext(mSalesman));
        mPresenter = new Step3InputPresenter(this, new Step3InputRepository());
        mPresenter.subscribe();
        mSalesman = new Salesman();
        mStep3 = new Step3InputState();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (((CertificationFragment) getParentFragment()).getCurrentFragmentIndex() == 3) {
            mPresenter.requestStep();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == REQUEST_CODE_COMPANY) {
                String companyName = data.getStringExtra(EXTRA_COMPANY_NAME);
                if (!TextUtils.isEmpty(companyName)) {
                    mStep3.getList().set(0, companyName);
                    mStep3.getList().set(1, "请选择");
                    mStep3.setFinishItem0(true);
                    mSalesman.setCompany(companyName);
                    mSalesman.setCompanyId("");
                    mSalesman.setName("");
                    mSalesman.setWorkId("");
                    mAdapter.notifyDataSetChanged();
                }
            }
            if (requestCode == REQUEST_CODE_PERSON) {
                String companyName = data.getStringExtra(EXTRA_COMPANY_NAME);
                String companyId = data.getStringExtra(EXTRA_COMPANY_ID);
                String personName = data.getStringExtra(EXTRA_PERSON_NAME);
                String personId = data.getStringExtra(EXTRA_PERSON_ID);
                if (!TextUtils.isEmpty(personName)) {
                    mStep3.getList().set(1, personName);
                    mStep3.setFinishItem1(true);
                    mSalesman.setCompany(companyName);
                    mSalesman.setCompanyId(companyId);
                    mSalesman.setName(personName);
                    mSalesman.setWorkId(personId);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    protected void requestStep() {
        mPresenter.requestStep();
    }

    @Override
    public void showCompanyView() {
        Intent intent = new Intent(getContext(), FunctionActivity.class);
        intent.putExtra("function", "Company");
        startActivityForResult(intent, REQUEST_CODE_COMPANY);
    }

    @Override
    public void showSalesmanView() {
        String company = mSalesman.getCompany();
        if (!TextUtils.isEmpty(company)) {
            Intent intent = new Intent(getContext(), FunctionActivity.class);
            intent.putExtra("function", "Salesman");
            intent.putExtra("company", mSalesman.getCompany());
            startActivityForResult(intent, REQUEST_CODE_PERSON);
        } else {
            showToast("清先选择分公司");
        }
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
            if (mStep3 != null) {
                return 2;
            } else {
                return 0;
            }
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
