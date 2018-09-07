package com.jiaye.cashloan.view.loancar.carprice.year;

import android.os.Bundle;
import android.view.View;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseListFragment;
import com.jiaye.cashloan.view.loancar.carprice.CarActivity;
import com.jiaye.cashloan.view.loancar.carprice.MonthFragment;
import com.jiaye.cashloan.view.loancar.carprice.year.source.YearRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * ModelFragment
 *
 * @author 贾博瑄
 */

public class YearFragment extends BaseListFragment implements YearContract.View {

    private int currentYear;

    private YearContract.Presenter mPresenter;

    private List<String> years = new ArrayList<>();

    public static YearFragment newInstance(String modelId) {
        Bundle args = new Bundle();
        args.putString("model", modelId);
        YearFragment fragment = new YearFragment();
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.CHINA);
        Date date = new Date();
        currentYear = Integer.parseInt(sdf.format(date));
        mPresenter = new YearPresenter(this, new YearRepository());
        mPresenter.subscribe();
        mPresenter.requestData(getArguments().getString("model"));
    }

    @Override
    protected void recyclerViewOnBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        holder.setContentText(years.get(position));
    }

    @Override
    protected int getListSize() {
        return years.size();
    }

    @Override
    protected void onListItemClick(int position) {
        CarActivity activity = (CarActivity) getActivity();
        if (activity != null) {
            ((MonthFragment) activity.getNextFragment()).setYear(years.get(position));
            activity.goToNextFragment();
        }
    }

    @Override
    protected int getTitleId() {
        return R.string.car_year_title;
    }

    @Override
    public void setList(List<String> list) {
        years = list;
        refreshRecyclerView();
    }
}
