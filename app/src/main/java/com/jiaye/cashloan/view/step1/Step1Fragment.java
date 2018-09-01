package com.jiaye.cashloan.view.step1;

import android.content.Intent;
import android.os.Bundle;
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
import com.jiaye.cashloan.view.FunctionActivity;
import com.jiaye.cashloan.view.car.CarActivity;
import com.jiaye.cashloan.view.certification.CertificationFragment;
import com.jiaye.cashloan.view.step1.source.Step1Repository;
import com.jiaye.cashloan.widget.StepView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Step1Fragment
 *
 * @author 贾博瑄
 */

public class Step1Fragment extends BaseStepFragment implements Step1Contract.View {

    private static final String TAG = "Step1Fragment";

    public static final int REQUEST_CODE_CAR_BRAND = 101;
    public static final int REQUEST_CODE_CAR_DATE = 102;
    public static final int REQUEST_CODE_CAR_MILES = 103;
    public static final int REQUEST_CODE_CAR_CITY = 104;

    public static final String EXTRA_MODEL_ID = "model_id";
    public static final String EXTRA_MODEL_NAME = "model_name";
    public static final String EXTRA_YEAR = "year";
    public static final String EXTRA_MONTH = "month";
    public static final String EXTRA_MILES = "miles";
    public static final String EXTRA_PROVINCE_ID = "city_province_id";
    public static final String EXTRA_CITY_NAME = "city_name";
    public static final String EXTRA_CITY_ID = "city_id";

    private Step1Contract.Presenter mPresenter;

    private TextView mTextTitle;

    private TextView mTextSubTitle;

    private RecyclerView mRecyclerView;

    private LinearLayout mLayoutResult;

    private TextView mTextResult;

    private Adapter mAdapter;

    private Button mBtnNext;

    private List<String> mList;

    public static Step1Fragment newInstance() {
        Bundle args = new Bundle();
        Step1Fragment fragment = new Step1Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.step1_fragment, container, false);
        mTextTitle = root.findViewById(R.id.title);
        mTextSubTitle = root.findViewById(R.id.sub_title);
        mRecyclerView = root.findViewById(R.id.recycler_view);
        mLayoutResult = root.findViewById(R.id.result_layout);
        mTextResult = root.findViewById(R.id.result_text);
        mList = new ArrayList<>();
        mList.add("请选择");
        mList.add("请选择");
        mList.add("请输入");
        mList.add("请选择");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new Adapter();
        mRecyclerView.setAdapter(mAdapter);
        mBtnNext = root.findViewById(R.id.btn_next);
        showInputView();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAR_BRAND) {
            if (data != null) {
                mList.set(0, data.getStringExtra(EXTRA_MODEL_NAME));
                mAdapter.notifyDataSetChanged();
                mPresenter.getStep1().setFinishItem0(true);
                mPresenter.getStep1().setCarId(data.getStringExtra(EXTRA_MODEL_ID));
            }
        }
        if (requestCode == REQUEST_CODE_CAR_DATE) {
            if (data != null) {
                String year = data.getStringExtra(EXTRA_YEAR);
                String month = data.getStringExtra(EXTRA_MONTH);
                mList.set(1, year + "-" + month);
                mAdapter.notifyDataSetChanged();
                mPresenter.getStep1().setFinishItem1(true);
                mPresenter.getStep1().setCarYear(year);
                mPresenter.getStep1().setCarMonth(month);
            }
        }
        if (requestCode == REQUEST_CODE_CAR_MILES) {
            if (data != null) {
                String miles = data.getStringExtra(EXTRA_MILES);
                mList.set(2, miles + "公里");
                mAdapter.notifyDataSetChanged();
                mPresenter.getStep1().setFinishItem2(true);
                mPresenter.getStep1().setCarMiles(divideTenThousand(miles));
            }
        }
        if (requestCode == REQUEST_CODE_CAR_CITY) {
            if (data != null) {
                mList.set(3, data.getStringExtra(EXTRA_CITY_NAME));
                mPresenter.getStep1().setFinishItem3(true);
                mAdapter.notifyDataSetChanged();
                mPresenter.getStep1().setCarCity(data.getStringExtra(EXTRA_CITY_ID));
                mPresenter.getStep1().setCarProvince(data.getStringExtra(EXTRA_PROVINCE_ID));
            }
        }
    }

    private String divideTenThousand(String value) {
        BigDecimal miles = new BigDecimal(value);
        BigDecimal tenThousand = new BigDecimal("10000");
        return miles.divide(tenThousand, 2, RoundingMode.HALF_UP).toString();
    }

    public void showInputView() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mLayoutResult.setVisibility(View.GONE);
        mTextTitle.setText("车辆评估");
        mTextSubTitle.setText("完善信息后即可获得车辆估值");
        mBtnNext.setText("立即评估");
        mBtnNext.setOnClickListener(v -> mPresenter.onClickNext());
    }

    @Override
    public void showResultView(String min, String max) {
        mRecyclerView.setVisibility(View.GONE);
        mLayoutResult.setVisibility(View.VISIBLE);
        mTextTitle.setText("评估结果");
        mTextSubTitle.setVisibility(View.INVISIBLE);
        mTextResult.setText(getResources().getString(R.string.step1_valuation, min, max));
        mBtnNext.setText("立即预约");
        mBtnNext.setOnClickListener(v -> mPresenter.saveCarPrice());
    }

    @Override
    public void showCarBrandView() {
        Intent intent = new Intent(getContext(), CarActivity.class);
        intent.putExtra(CarActivity.EXTRA_TYPE, CarActivity.TYPE_BRAND);
        startActivityForResult(intent, REQUEST_CODE_CAR_BRAND);
    }

    @Override
    public void showCarDateView() {
        Intent intent = new Intent(getContext(), CarActivity.class);
        intent.putExtra(CarActivity.EXTRA_TYPE, CarActivity.TYPE_DATE);
        intent.putExtra(EXTRA_MODEL_ID, mPresenter.getStep1().getCarId());
        startActivityForResult(intent, REQUEST_CODE_CAR_DATE);
    }

    @Override
    public void showCarMilesView() {
        Intent intent = new Intent(getContext(), FunctionActivity.class);
        intent.putExtra("function", "CarMiles");
        startActivityForResult(intent, REQUEST_CODE_CAR_MILES);
    }

    @Override
    public void showCarLocationView() {
        Intent intent = new Intent(getContext(), CarActivity.class);
        intent.putExtra(CarActivity.EXTRA_TYPE, CarActivity.TYPE_CITY);
        startActivityForResult(intent, REQUEST_CODE_CAR_CITY);
    }

    @Override
    public void sendBroadcast() {
        CertificationFragment.refresh(getActivity());
    }

    @Override
    protected void requestStep() {

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
            } else if (position == 3) {
                holder.setType(2);
            } else {
                holder.setType(1);
            }
            switch (position) {
                case 0:
                    holder.setTextName("车辆型号");
                    holder.setTextState(mList.get(position));
                    holder.setState(mPresenter.getStep1().isFinishItem0());
                    break;
                case 1:
                    holder.setTextName("上牌时间");
                    holder.setTextState(mList.get(position));
                    holder.setState(mPresenter.getStep1().isFinishItem1());
                    holder.setReady(mPresenter.getStep1().isFinishItem0());
                    break;
                case 2:
                    holder.setTextName("行驶里程");
                    holder.setTextState(mList.get(position));
                    holder.setState(mPresenter.getStep1().isFinishItem2());
                    holder.setReady(mPresenter.getStep1().isFinishItem1());
                    break;
                case 3:
                    holder.setTextName("所在城市");
                    holder.setTextState(mList.get(position));
                    holder.setState(mPresenter.getStep1().isFinishItem3());
                    holder.setReady(mPresenter.getStep1().isFinishItem2());
                    break;
            }
            holder.invalidate();
        }

        @Override
        public int getItemCount() {
            if (mPresenter.getStep1() != null) {
                return 4;
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
