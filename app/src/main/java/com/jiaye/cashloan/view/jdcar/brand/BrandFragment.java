package com.jiaye.cashloan.view.jdcar.brand;

import android.os.Bundle;
import android.view.View;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.jdcar.JdCarBrand;
import com.jiaye.cashloan.view.BaseListFragment;
import com.jiaye.cashloan.view.jdcar.JdActivity;
import com.jiaye.cashloan.view.jdcar.brand.source.BrandRepository;
import com.jiaye.cashloan.view.jdcar.family.FamilyFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * BrandFragment
 *
 * @author 贾博�?
 */

public class BrandFragment extends BaseListFragment implements BrandContract.View {

    private BrandContract.Presenter mPresenter;

    private List<JdCarBrand> mList = new ArrayList<>();

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
    }

    @Override
    protected void recyclerViewOnBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        if (position > 0 && !mList.get(position).getLetter().equals(mList.get(position - 1).getLetter())) {
            holder.setHeaderVisible(View.VISIBLE);
            holder.setHeaderText(mList.get(position).getLetter());
        } else {
            holder.setHeaderVisible(View.GONE);
        }
        holder.setContentText(mList.get(position).getBrandname());
    }

    @Override
    protected int getListSize() {
        return mList.size();
    }

    @Override
    protected void onListItemClick(int position) {
        JdActivity activity = (JdActivity) getActivity();
        if (activity != null) {
            activity.goToNextFragment();
            ((FamilyFragment) activity.getNextFragment()).setBrandId(mList.get(position).getId());
        }
    }

    @Override
    protected int getTitleId() {
        return R.string.jd_brand_title;
    }

    @Override
    public void setList(List<JdCarBrand> list) {
        mList = list;
        Collections.sort(list, (o1, o2) -> {
            if (o1 == null || o2 == null) {
                return -1;
            }
            String str1 = o1.getBrandname();
            String str2 = o2.getBrandname();
            if (str1.compareToIgnoreCase(str2) < 0) {
                return -1;
            } else if (str1.compareToIgnoreCase(str2) == 0) {
                return 0;
            }
            return 1;
        });
        refreshRecyclerView();
    }
}
