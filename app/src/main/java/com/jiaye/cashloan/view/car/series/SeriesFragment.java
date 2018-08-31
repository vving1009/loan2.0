package com.jiaye.cashloan.view.car.series;

import android.os.Bundle;
import android.view.View;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.car.CarSeries;
import com.jiaye.cashloan.view.BaseListFragment;
import com.jiaye.cashloan.view.car.CarActivity;
import com.jiaye.cashloan.view.car.series.source.SeriesRepository;
import com.jiaye.cashloan.view.car.model.ModelFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * SeriesFragment
 *
 * @author 贾博瑄
 */

public class SeriesFragment extends BaseListFragment implements SeriesContract.View {

    private static final String TAG = "SeriesFragment";

    private SeriesContract.Presenter mPresenter;

    private List<Object> mList = new ArrayList<>();

    public static SeriesFragment newInstance() {
        Bundle args = new Bundle();
        SeriesFragment fragment = new SeriesFragment();
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
        mPresenter = new SeriesPresenter(this, new SeriesRepository());
        mPresenter.subscribe();
    }

    @Override
    protected void recyclerViewOnBindViewHolder(BaseListFragment.ListAdapter.ViewHolder holder, int position) {
        if (mList.get(position) instanceof String) {
            holder.setHeaderVisible(View.VISIBLE);
            holder.setBodyVisible(View.GONE);
            holder.setHeaderText((String)mList.get(position));
        } else if (mList.get(position) instanceof CarSeries.PinpaiListBean.XilieBean) {
            holder.setHeaderVisible(View.GONE);
            holder.setBodyVisible(View.VISIBLE);
            holder.setContentText(((CarSeries.PinpaiListBean.XilieBean) mList.get(position)).getXlname());
        }
    }

    @Override
    protected int getListSize() {
        return mList.size();
    }

    @Override
    protected void onListItemClick(int position) {
        CarActivity activity = (CarActivity) getActivity();
        if (activity != null && mList.get(position) instanceof CarSeries.PinpaiListBean.XilieBean) {
            ((ModelFragment) activity.getNextFragment()).setFamilyId(((CarSeries.PinpaiListBean.XilieBean) mList.get(position)).getXlid());
            ((ModelFragment) activity.getNextFragment()).setTitle(((CarSeries.PinpaiListBean.XilieBean) mList.get(position)).getXlname());
            activity.goToNextFragment();
        }
    }

    @Override
    protected int getTitleId() {
        return R.string.car_brand_title;
    }

    @Override
    public void setList(List<Object> list) {
        mList = list;
        refreshRecyclerView();
    }

    public void setBrandId(String id) {
        mPresenter.requestData(id);
    }

    public void setTitle(String title) {
        super.setTitle(title);
    }
}
