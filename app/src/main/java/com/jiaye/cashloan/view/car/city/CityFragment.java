package com.jiaye.cashloan.view.car.city;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.car.CarCity;
import com.jiaye.cashloan.view.BaseListFragment;
import com.jiaye.cashloan.view.car.CarActivity;
import com.jiaye.cashloan.view.car.city.source.CityRepository;
import com.jiaye.cashloan.view.step1.Step1Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * CityFragment
 *
 * @author 贾博瑄
 */

public class CityFragment extends BaseListFragment implements CityContract.View {

    private static final String TAG = "CityFragment";

    private CityContract.Presenter mPresenter;

    private List<CarCity> mList = new ArrayList<>();

    private String provinceId;

    public static CityFragment newInstance() {
        Bundle args = new Bundle();
        CityFragment fragment = new CityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    protected void onFragmentCreate(View rootView) {
        mPresenter = new CityPresenter(this, new CityRepository());
        mPresenter.subscribe();
        /*setOnIndexViewListener(string -> {
            Log.d(TAG, "setOnIndexViewListener: " + string);
            for (int i = 0; i < mList.size(); i++) {
                if (string.equals(mList.get(i).getLetter())) {
                    ((LinearLayoutManager)mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(i,0);
                    return;
                }
            }
        });*/
    }

    @Override
    protected void recyclerViewOnBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        holder.setContentText(mList.get(position).getCityName());
    }

    @Override
    protected int getListSize() {
        return mList.size();
    }

    @Override
    protected void onListItemClick(int position) {
        CarActivity activity = (CarActivity) getActivity();
        if (activity != null) {
            Intent intent = new Intent();
            intent.putExtra(Step1Fragment.EXTRA_PROVINCE_ID, provinceId);
            intent.putExtra(Step1Fragment.EXTRA_CITY_ID, String.valueOf(mList.get(position).getCityID()));
            intent.putExtra(Step1Fragment.EXTRA_CITY_NAME, mList.get(position).getCityName());
            activity.setResult(Step1Fragment.REQUEST_CODE_CAR_BRAND, intent);
            activity.finish();
        }
    }

    @Override
    protected int getTitleId() {
        return R.string.car_city_title;
    }

    @Override
    public void setList(List<CarCity> list) {
        mList = list;
        refreshRecyclerView();
    }

    public void setProvinceId(String id) {
        provinceId = id;
        mPresenter.requestData(id);
    }
}
