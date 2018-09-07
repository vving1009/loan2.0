package com.jiaye.cashloan.view.loancar.carprice.brand;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.car.CarBrand;
import com.jiaye.cashloan.view.BaseListFragment;
import com.jiaye.cashloan.view.loancar.carprice.CarActivity;
import com.jiaye.cashloan.view.loancar.carprice.brand.source.BrandRepository;
import com.jiaye.cashloan.view.loancar.carprice.series.SeriesFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * BrandFragment
 *
 * @author 贾博瑄
 */

public class BrandFragment extends BaseListFragment implements BrandContract.View {

    private static final String TAG = "BrandFragment";

    private BrandContract.Presenter mPresenter;

    private List<CarBrand.Body> mList = new ArrayList<>();

    public static BrandFragment newInstance() {
        Bundle args = new Bundle();
        BrandFragment fragment = new BrandFragment();
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
        mPresenter = new BrandPresenter(this, new BrandRepository());
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
        if (position == 0 && mList.get(position).getPin().equals("A")) {
            holder.setHeaderVisible(View.VISIBLE);
            holder.setHeaderText("A");
        } else if (position > 0 && !mList.get(position).getPin().equals(mList.get(position - 1).getPin())) {
            holder.setHeaderVisible(View.VISIBLE);
            holder.setHeaderText(mList.get(position).getPin());
        } else {
            holder.setHeaderVisible(View.GONE);
        }
        holder.setContentText(mList.get(position).getBigPpname());
    }

    @Override
    protected int getListSize() {
        return mList.size();
    }

    @Override
    protected void onListItemClick(int position) {
        CarActivity activity = (CarActivity) getActivity();
        if (activity != null) {
            ((SeriesFragment) activity.getNextFragment()).setBrandId(mList.get(position).getId());
            ((SeriesFragment) activity.getNextFragment()).setTitle(mList.get(position).getBigPpname());
            activity.goToNextFragment();
        }
    }

    @Override
    protected int getTitleId() {
        return R.string.car_brand_title;
    }

    @Override
    public void setList(List<CarBrand.Body> list) {
        mList = list;
        refreshRecyclerView();
    }
}
