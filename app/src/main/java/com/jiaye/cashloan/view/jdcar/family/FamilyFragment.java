package com.jiaye.cashloan.view.jdcar.family;

import android.os.Bundle;
import android.view.View;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.jdcar.JdCarFamily;
import com.jiaye.cashloan.view.BaseListFragment;
import com.jiaye.cashloan.view.jdcar.JdActivity;
import com.jiaye.cashloan.view.jdcar.family.source.FamilyRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * FamilyFragment
 *
 * @author 贾博�?
 */

public class FamilyFragment extends BaseListFragment implements FamilyContract.View {

    private FamilyContract.Presenter mPresenter;

    private List<JdCarFamily> mList = new ArrayList<>();

    public static FamilyFragment newInstance() {
        Bundle args = new Bundle();
        FamilyFragment fragment = new FamilyFragment();
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
        mPresenter = new FamilyPresenter(this, new FamilyRepository());
        mPresenter.subscribe();
    }

    @Override
    protected void recyclerViewOnBindViewHolder(BaseListFragment.ListAdapter.ViewHolder holder, int position) {
        holder.setHeaderText(mList.get(position).getFamilyname());
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
            activity.getNextFragment().setFamilyId(mList.get(position).getId());
        }
    }

    @Override
    protected int getTitleId() {
        return R.string.jd_brand_title;
    }

    @Override
    public void setList(List<JdCarFamily> list) {
        mList = list;
        Collections.sort(list, (o1, o2) -> {
            if (o1 == null || o2 == null) {
                return -1;
            }
            String str1 = o1.getFamilyname();
            String str2 = o2.getFamilyname();
            if (str1.compareToIgnoreCase(str2) < 0) {
                return -1;
            } else if (str1.compareToIgnoreCase(str2) == 0) {
                return 0;
            }
            return 1;
        });
        refreshRecyclerView();
    }

    public void setBrandId(String id) {

    }
}
