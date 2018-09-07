package com.jiaye.cashloan.view.loancar.carprice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseListFragment;
import com.jiaye.cashloan.view.loancar.step1.Step1Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * ModelFragment
 *
 * @author 贾博瑄
 */

public class MonthFragment extends BaseListFragment {

    private String year;

    public static MonthFragment newInstance() {
        Bundle args = new Bundle();
        MonthFragment fragment = new MonthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected void onFragmentCreate(View rootView) {
    }

    @Override
    protected void recyclerViewOnBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        holder.setContentText(String.valueOf(position + 1) + "月");
    }

    @Override
    protected int getListSize() {
        if (getCurrentYear().equals(year)) {
            return getCurrentMonth();
        } else {
            return 12;
        }
    }

    @Override
    protected void onListItemClick(int position) {
        CarActivity activity = (CarActivity) getActivity();
        if (activity != null) {
            Intent intent = new Intent();
            intent.putExtra(Step1Fragment.EXTRA_YEAR, year);
            intent.putExtra(Step1Fragment.EXTRA_MONTH, position + 1 < 10 ? "0" + (position + 1) : String.valueOf(position + 1));
            getActivity().setResult(Step1Fragment.REQUEST_CODE_CAR_DATE, intent);
            getActivity().finish();
        }
    }

    @Override
    protected int getTitleId() {
        return R.string.car_month_title;
    }

    public void setYear(String year) {
        this.year = year;
        mListAdapter.notifyDataSetChanged();
    }

    private String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.CHINA);
        Date date = new Date();
        return sdf.format(date);
    }

    private int getCurrentMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM", Locale.CHINA);
        Date date = new Date();
        return Integer.parseInt(sdf.format(date));
    }
}
