package com.jiaye.cashloan.view.car.model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.car.CarModel;
import com.jiaye.cashloan.http.data.car.CarSeries;
import com.jiaye.cashloan.view.BaseListFragment;
import com.jiaye.cashloan.view.car.CarActivity;
import com.jiaye.cashloan.view.car.model.source.ModelRepository;
import com.jiaye.cashloan.view.step1.input.Step1InputFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * ModelFragment
 *
 * @author 贾博瑄
 */

public class ModelFragment extends BaseListFragment implements ModelContract.View {

    private ModelContract.Presenter mPresenter;

    private List<Object> mList = new ArrayList<>();

    public static ModelFragment newInstance() {
        Bundle args = new Bundle();
        ModelFragment fragment = new ModelFragment();
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
        mPresenter = new ModelPresenter(this, new ModelRepository());
        mPresenter.subscribe();
    }

    @Override
    protected void recyclerViewOnBindViewHolder(BaseListFragment.ListAdapter.ViewHolder holder, int position) {
        /*if (position == 0) {
            holder.setHeaderVisible(View.VISIBLE);
            holder.setHeaderText(mList.get(position).getSaleyear() + "年");
        } else if (position > 0 && !mList.get(position).getSaleyear().equals(mList.get(position - 1).getSaleyear())) {
            holder.setHeaderVisible(View.VISIBLE);
            holder.setHeaderText(mList.get(position).getSaleyear() + "年");
        } else {
            holder.setHeaderVisible(View.GONE);
        }
        holder.setContentText(mList.get(position).getSalesdesc());*/
        if (mList.get(position) instanceof String) {
            holder.setHeaderVisible(View.VISIBLE);
            holder.setBodyVisible(View.GONE);
            holder.setHeaderText((String)mList.get(position));
        } else if (mList.get(position) instanceof CarModel.DataBean.ChexingListBean) {
            holder.setHeaderVisible(View.GONE);
            holder.setBodyVisible(View.VISIBLE);
            holder.setContentText(((CarModel.DataBean.ChexingListBean) mList.get(position)).getCxname());
        }
    }

    @Override
    protected int getListSize() {
        return mList.size();
    }

    @Override
    protected void onListItemClick(int position) {
        CarActivity activity = (CarActivity) getActivity();
        if (activity != null && mList.get(position) instanceof CarModel.DataBean.ChexingListBean) {
            Intent intent = new Intent();
            intent.putExtra(Step1InputFragment.EXTRA_MODEL_ID, ((CarModel.DataBean.ChexingListBean) mList.get(position)).getId());
            intent.putExtra(Step1InputFragment.EXTRA_MODEL_NAME, ((CarModel.DataBean.ChexingListBean) mList.get(position)).getCxname());
            getActivity().setResult(Step1InputFragment.REQUEST_CODE_CAR_BRAND, intent);
            getActivity().finish();
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

    public void setFamilyId(String id) {
        mPresenter.requestData(id);
    }

    public void setTitle(String title) {
        super.setTitle(title);
    }
}
