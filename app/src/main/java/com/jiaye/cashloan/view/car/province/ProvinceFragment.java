package com.jiaye.cashloan.view.car.province;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.car.CarProvince;
import com.jiaye.cashloan.view.BaseListFragment;
import com.jiaye.cashloan.view.car.CarActivity;
import com.jiaye.cashloan.view.car.city.CityFragment;
import com.jiaye.cashloan.view.car.province.source.ProvinceRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * ProvinceFragment
 *
 * @author 贾博瑄
 */

public class ProvinceFragment extends BaseListFragment implements ProvinceContract.View {

    private static final String TAG = "ProvinceFragment";

    private ProvinceContract.Presenter mPresenter;

    private List<CarProvince> mList = new ArrayList<>();

    public static ProvinceFragment newInstance() {
        Bundle args = new Bundle();
        ProvinceFragment fragment = new ProvinceFragment();
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
        mPresenter = new ProvincePresenter(this, new ProvinceRepository());
        mPresenter.subscribe();
        mPresenter.requestData();
        setOnIndexViewListener(string -> {
            Log.d(TAG, "setOnIndexViewListener: " + string);
            for (int i = 0; i < mList.size(); i++) {
                if (string.equals(mList.get(i).getPin())) {
                    ((LinearLayoutManager)mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(i,0);
                    return;
                }
            }
        });
    }

    @Override
    protected void recyclerViewOnBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        if (position == 0) {
            holder.setHeaderVisible(View.VISIBLE);
            holder.setHeaderText(mList.get(position).getPin());
        } else if (position > 0 && !mList.get(position).getPin().equals(mList.get(position - 1).getPin())) {
            holder.setHeaderVisible(View.VISIBLE);
            holder.setHeaderText(mList.get(position).getPin());
        } else {
            holder.setHeaderVisible(View.GONE);
        }
        holder.setContentText(mList.get(position).getProName());
    }

    @Override
    protected int getListSize() {
        return mList.size();
    }

    @Override
    protected void onListItemClick(int position) {
        CarActivity activity = (CarActivity) getActivity();
        if (activity != null) {
            ((CityFragment) activity.getNextFragment()).setProvinceId(String.valueOf(mList.get(position).getProID()));
            activity.goToNextFragment();
        }
    }

    @Override
    protected int getTitleId() {
        return R.string.car_province_title;
    }

    @Override
    public void setList(List<CarProvince> list) {
        mList = list;
        refreshRecyclerView();
    }
}
