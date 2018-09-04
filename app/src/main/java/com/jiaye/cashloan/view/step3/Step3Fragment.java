package com.jiaye.cashloan.view.step3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.persistence.Salesman;
import com.jiaye.cashloan.view.FunctionActivity;
import com.jiaye.cashloan.view.account.AccountWebActivity;
import com.jiaye.cashloan.view.certification.CertificationFragment;
import com.jiaye.cashloan.view.step1.BaseStepFragment;
import com.jiaye.cashloan.view.step3.source.Step3Repository;
import com.jiaye.cashloan.widget.StepView;

import java.util.ArrayList;
import java.util.List;

/**
 * Step3Fragment
 *
 * @author 贾博瑄
 */

public class Step3Fragment extends BaseStepFragment implements Step3Contract.View {

    public static final int REQUEST_CODE_SALESMAN = 101;

    public static final String EXTRA_SALESMAN = "salesman";

    private Step3Contract.Presenter mPresenter;

    private TextView mTextTitle;

    private TextView mTextSubTitle;

    private RecyclerView mRecyclerView;

    private LinearLayout mLayoutResult;

    private ImageView mImgResult;

    private TextView mTextResult;

    private TextView mTextCall;

    private Button mBtnNext;

    private CheckBox mCheckBox;

    private Adapter mAdapter;

    private Salesman mSalesman;

    private List<String> mList;

    public static Step3Fragment newInstance() {
        Bundle args = new Bundle();
        Step3Fragment fragment = new Step3Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.step3_fragment, container, false);
        mTextTitle = root.findViewById(R.id.title);
        mTextSubTitle = root.findViewById(R.id.sub_title);
        mLayoutResult = root.findViewById(R.id.result_layout);
        mImgResult = root.findViewById(R.id.result_img);
        mTextResult = root.findViewById(R.id.result_text);
        mTextCall = root.findViewById(R.id.call_text);
        mBtnNext = root.findViewById(R.id.btn_next);
        mCheckBox = root.findViewById(R.id.sign_check_box);
        mRecyclerView = root.findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new Adapter();
        mRecyclerView.setAdapter(mAdapter);
        mList = new ArrayList<>();
        mList.add("请选择");
        mList.add("请选择");
        mAdapter = new Adapter();
        mRecyclerView.setAdapter(mAdapter);

        SpannableString str1 = new SpannableString(getResources().getString(R.string.step3_call));
        str1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_blue)),
                str1.length() - 12, str1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        str1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:400–8780–777"));
                startActivity(intent);
            }
        }, str1.length() - 12, str1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextCall.setText(str1);
        mTextCall.setMovementMethod(LinkMovementMethod.getInstance());

        SpannableString str2 = new SpannableString(getResources().getString(R.string.step3_sign));
        str2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_blue)),
                str2.length() - 7, str2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        str2.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                showSignView();
            }
        }, str2.length() - 7, str2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mCheckBox.setText(str2);
        mCheckBox.setMovementMethod(LinkMovementMethod.getInstance());

        mSalesman = new Salesman();
        mPresenter = new Step3Presenter(this, new Step3Repository());
        mPresenter.subscribe();
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
            if (requestCode == REQUEST_CODE_SALESMAN) {
                mSalesman = data.getParcelableExtra(EXTRA_SALESMAN);
                String company = mSalesman.getCompany();
                String name = mSalesman.getName();
                /*String companyName = data.getStringExtra(EXTRA_SALESMAN);
                String companyId = data.getStringExtra(EXTRA_COMPANY_ID);
                String personName = data.getStringExtra(EXTRA_PERSON_NAME);
                String personId = data.getStringExtra(EXTRA_PERSON_ID);*/
                if (!TextUtils.isEmpty(company) && !TextUtils.isEmpty(name)) {
                    mList.set(0, company);
                    mList.set(1, name);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void showInputView() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mLayoutResult.setVisibility(View.GONE);
        mTextCall.setVisibility(View.VISIBLE);
        mCheckBox.setVisibility(View.GONE);
        mBtnNext.setVisibility(View.VISIBLE);
        mTextTitle.setText("到店借款");
        mTextSubTitle.setText("请您到店进行后续手续办理");
        mBtnNext.setText("提交");
        mBtnNext.setOnClickListener(v -> mPresenter.onClickNext(mSalesman));
    }

    @Override
    public void showWaitView() {
        mRecyclerView.setVisibility(View.GONE);
        mLayoutResult.setVisibility(View.VISIBLE);
        mTextCall.setVisibility(View.GONE);
        mCheckBox.setVisibility(View.GONE);
        mTextSubTitle.setVisibility(View.INVISIBLE);
        mBtnNext.setVisibility(View.GONE);
        mTextTitle.setText("最终额度");
        mImgResult.setImageDrawable(getResources().getDrawable(R.drawable.certification_ic_wait));
        mTextResult.setText(getResources().getString(R.string.step3_approving));
    }

    @Override
    public void showRejectView() {
        mRecyclerView.setVisibility(View.GONE);
        mLayoutResult.setVisibility(View.VISIBLE);
        mTextCall.setVisibility(View.GONE);
        mCheckBox.setVisibility(View.GONE);
        mTextSubTitle.setVisibility(View.INVISIBLE);
        mBtnNext.setVisibility(View.GONE);
        mTextTitle.setText("最终额度");
        mImgResult.setImageDrawable(getResources().getDrawable(R.drawable.certification_ic_reject));
        mTextResult.setText(getResources().getString(R.string.step3_reject));
    }

    @Override
    public void showSuccessView(String value) {
        mRecyclerView.setVisibility(View.GONE);
        mLayoutResult.setVisibility(View.VISIBLE);
        mTextCall.setVisibility(View.GONE);
        mCheckBox.setVisibility(View.VISIBLE);
        mTextSubTitle.setVisibility(View.INVISIBLE);
        mBtnNext.setVisibility(View.VISIBLE);
        mTextTitle.setText("最终额度");
        mImgResult.setImageDrawable(getResources().getDrawable(R.drawable.certification_ic_shop));
        mTextResult.setText(getResources().getString(R.string.step3_amount, value));
        mBtnNext.setText("确认借款");
        mBtnNext.setOnClickListener(v -> {
            if (mCheckBox.isChecked()) {
                mPresenter.requestNextStep();
            } else {
                showToast("请先勾选下方勾选框");
            }
        });
    }

    public void showSignView() {
        FunctionActivity.function(getActivity(), "Sign");
    }

    @Override
    protected void requestStep() {
        mPresenter.requestStep();
    }

    public void showCompanyView() {
        Intent intent = new Intent(getContext(), FunctionActivity.class);
        intent.putExtra("function", "Company");
        startActivityForResult(intent, REQUEST_CODE_SALESMAN);
    }

    @Override
    public void showMoreInfoView() {
        FunctionActivity.function(getActivity(), "Info2");
    }

    @Override
    public void showOpenAccountView() {
        Intent intent = new Intent(getActivity(), AccountWebActivity.class);
        intent.putExtra("type", "accountOpen");
        startActivity(intent);
    }

    @Override
    public void sendBroadcast() {
        CertificationFragment.refresh(getActivity());
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
                    holder.setTextState(mList.get(position));
                    holder.setState(!mList.get(position).equals("请选择"));
                    break;
                case 1:
                    holder.setType(2);
                    holder.setTextName("客户经理");
                    holder.setTextState(mList.get(position));
                    holder.setState(!mList.get(position).equals("请选择"));
                    holder.setReady(!mList.get(position - 1).equals("请选择"));
                    break;
            }
            holder.invalidate();
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private StepView mStepView;

        private TextView mTextName;

        private TextView mTextState;

        //private ImageView mImageInto;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                mList.set(0, "请选择");
                mList.set(1, "请选择");
                mAdapter.notifyDataSetChanged();
                mSalesman = null;
                showCompanyView();
            });
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
