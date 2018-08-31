package com.jiaye.cashloan.view.step1.input;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.FunctionActivity;
import com.jiaye.cashloan.view.certification.CertificationFragment;
import com.jiaye.cashloan.view.car.CarActivity;
import com.jiaye.cashloan.view.step1.BaseStepFragment;
import com.jiaye.cashloan.view.step1.input.source.Step1InputRepository;
import com.jiaye.cashloan.view.step1.parent.Step1Fragment;
import com.jiaye.cashloan.widget.StepView;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Step1InputFragment
 *
 * @author 贾博瑄
 */

public class Step1InputFragment extends BaseStepFragment implements Step1InputContract.View {

    private static final String TAG = "Step1InputFragment";

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

    private Step1InputContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;

    private Adapter mAdapter;

    private Step1Fragment.OnNextClickListener mOnNextClickListener;

    private Step1InputState mStep1;

    public static Step1InputFragment newInstance() {
        Bundle args = new Bundle();
        Step1InputFragment fragment = new Step1InputFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.step1_input_fragment, container, false);
        mRecyclerView = root.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new Adapter();
        mRecyclerView.setAdapter(mAdapter);
        root.findViewById(R.id.btn_next).setOnClickListener(v -> {
            mPresenter.onClickNext(mStep1);
        });
        mStep1 = new Step1InputState();
        mPresenter = new Step1InputPresenter(this, new Step1InputRepository());
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
                mStep1.getList().set(0, data.getStringExtra(EXTRA_MODEL_NAME));
                mStep1.setFinishItem0(true);
                mAdapter.notifyDataSetChanged();
                mStep1.setCarId(data.getStringExtra(EXTRA_MODEL_ID));
            }
        }
        if (requestCode == REQUEST_CODE_CAR_DATE) {
            if (data != null) {
                String year = data.getStringExtra(EXTRA_YEAR);
                String month = data.getStringExtra(EXTRA_MONTH);
                mStep1.getList().set(1, year + "-" + month);
                mStep1.setFinishItem1(true);
                mAdapter.notifyDataSetChanged();
                mStep1.setCarYear(year);
                mStep1.setCarMonth(month);
            }
        }
        if (requestCode == REQUEST_CODE_CAR_MILES) {
            if (data != null) {
                String miles = data.getStringExtra(EXTRA_MILES);
                mStep1.getList().set(2, miles + "公里");
                mStep1.setFinishItem2(true);
                mAdapter.notifyDataSetChanged();
                mStep1.setCarMiles(divideTenThousand(miles));
            }
        }
        if (requestCode == REQUEST_CODE_CAR_CITY) {
            if (data != null) {
                mStep1.getList().set(3, data.getStringExtra(EXTRA_CITY_NAME));
                mStep1.setFinishItem3(true);
                mAdapter.notifyDataSetChanged();
                mStep1.setCarCity(data.getStringExtra(EXTRA_CITY_ID));
                mStep1.setCarProvince(data.getStringExtra(EXTRA_PROVINCE_ID));
            }
        }
    }

    private String divideTenThousand(String value) {
        BigDecimal miles = new BigDecimal(value);
        BigDecimal tenThousand = new BigDecimal("10000");
        return miles.divide(tenThousand, 2, RoundingMode.HALF_UP).toString();
    }

    public void setOnNextClickListener(Step1Fragment.OnNextClickListener listener) {
        mOnNextClickListener = listener;
    }

    /*@Override
    public void setStep2(Step1InputState step1) {
        mAdapter.setStep2(step1);
        mRecyclerView.scrollToPosition(0);
    }*/

    @Override
    public void showResultView(String min, String max) {
        mOnNextClickListener.onClick(min, max);
    }

    @Override
    public void showCarBrandView() {
        Intent intent = new Intent(getContext(), CarActivity.class);
        intent.putExtra(CarActivity.EXTRA_TYPE, CarActivity.TYPE_BRAND);
        startActivityForResult(intent, REQUEST_CODE_CAR_BRAND);
    }

    @Override
    public void showCarDateView() {
        if (!TextUtils.isEmpty(mStep1.getCarId())) {
            Intent intent = new Intent(getContext(), CarActivity.class);
            intent.putExtra(CarActivity.EXTRA_TYPE, CarActivity.TYPE_DATE);
            intent.putExtra(EXTRA_MODEL_ID, mStep1.getCarId());
            startActivityForResult(intent, REQUEST_CODE_CAR_DATE);
        } else {
            showToast("请先选择车型");
        }
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
    protected void requestStep() {

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
                    holder.setTextState(mStep1.getList().get(position));
                    holder.setState(mStep1.isFinishItem0());
                    break;
                case 1:
                    holder.setTextName("上牌时间");
                    holder.setTextState(mStep1.getList().get(position));
                    holder.setState(mStep1.isFinishItem1());
                    holder.setReady(mStep1.isFinishItem0());
                    break;
                case 2:
                    holder.setTextName("行驶里程");
                    holder.setTextState(mStep1.getList().get(position));
                    holder.setState(mStep1.isFinishItem2());
                    holder.setReady(mStep1.isFinishItem1());
                    break;
                case 3:
                    holder.setTextName("所在城市");
                    holder.setTextState(mStep1.getList().get(position));
                    holder.setState(mStep1.isFinishItem3());
                    holder.setReady(mStep1.isFinishItem2());
                    break;
            }
            holder.invalidate();
        }

        @Override
        public int getItemCount() {
            if (mStep1 != null) {
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
    /*private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private String[] carItems;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            carItems = getResources().getStringArray(R.array.step1_car_items);
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

            holder.setTextName(carItems[position]);
            holder.setTextState(mList.get(position));

            if (holder.getStateText().equals("请选择") || holder.getStateText().equals("请输入")) {
                holder.setState(0);
                if (mList.get(position - 1).equals("请选择")) {

                }
                holder.setReady(false);
            } else {
                holder.setState(1);
                holder.setReady(true);
            }

            switch (position) {
                case 0:
                    holder.setType(0);
                    holder.setState(mStep1.getId());
                    break;
                case 1:
                    holder.setType(1);
                    holder.setState(mStep1.getBioassay());
                    holder.setReady(mStep1.getId() == 1);
                    break;
                case 2:
                    holder.setType(1);
                    holder.setState(mStep1.getPersonal());
                    holder.setReady(mStep1.getBioassay() == 1);
                    break;
                case 3:
                    holder.setType(2);
                    holder.setState(mStep1.getPhone());
                    holder.setReady(mStep1.getPersonal() == 1);
                    break;
            }
            holder.invalidate();
        }

        @Override
        public int getItemCount() {
            return 4;
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

        public void setTextName(String name) {
            mTextName.setText(name);
        }

        public void setTextState(String name) {
            mTextName.setText(name);
        }

        public String getStateText() {
            return mTextState.getText().toString();
        }

        public void setState(int state) {
            switch (state) {
                case 0:
                    mTextState.setTextColor(Color.parseColor("#989898"));
                    mTextState.setText("请选择");
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

        public void setReady(boolean ready) {
            mStepView.setReady(ready);
        }

        public void invalidate() {
            mStepView.invalidate();
        }
    }*/
}
