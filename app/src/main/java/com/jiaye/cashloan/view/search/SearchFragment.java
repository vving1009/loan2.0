package com.jiaye.cashloan.view.search;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.persistence.Salesman;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.FunctionActivity;
import com.jiaye.cashloan.view.search.source.SearchRepository;
import com.satcatche.library.widget.SatcatcheDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SearchFragment
 *
 * @author 贾博瑄
 */

public class SearchFragment extends BaseFragment implements SearchContract.View {

    public static final String FINISH_COMPANY_FRAGMENT_ACTION = "com.jiaye.cashloan.FINISH_COMPANY";

    private SearchContract.Presenter mPresenter;

    private SearchView mSearchView;

    private RecyclerView mSearchList;

    private SearchListAdapter mSearchListAdapter;

    private SatcatcheDialog mDialog;

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.search_fragment, container, false);

        mDialog = new SatcatcheDialog.Builder(getContext())
                .setTitle("提示")
                .setMessage("是否选择当前客户经理")
                .setPositiveButton("是", ((dialog, which) -> {
                    mPresenter.saveSalesman();
                    LocalBroadcastManager.getInstance(getContext()).sendBroadcast(new Intent(FINISH_COMPANY_FRAGMENT_ACTION));
                }))
                .setNegativeButton("否", null)
                .build();
        mSearchView = root.findViewById(R.id.search_btn);
        mSearchList = root.findViewById(R.id.company_list);
        root.findViewById(R.id.img_back).setOnClickListener(v ->
                Objects.requireNonNull(getActivity()).finish());
        root.findViewById(R.id.clear_btn).setOnClickListener(v -> {
            mSearchView.setQuery("", false);
            mSearchView.setIconified(false);
        });
        initList();
        initSearchView();
        mPresenter = new SearchPresenter(this, new SearchRepository());
        mPresenter.subscribe();
        return root;
    }

    private void initSearchView() {
        TextView textView = mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(14);
        textView.setHintTextColor(Color.GRAY);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        SpannableString spanText = new SpannableString("请输入姓名、工号");
        spanText.setSpan(new AbsoluteSizeSpan(12, true), 0, spanText.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mSearchView.setQueryHint(spanText);// 设置字体大小
        try {
            Class<?> argClass = mSearchView.getClass();
            Field searchPlate = argClass.getDeclaredField("mSearchPlate");
            searchPlate.setAccessible(true);
            View view = (View) searchPlate.get(mSearchView);
            view.setBackgroundColor(Color.TRANSPARENT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mPresenter.querySalesman(newText);
                return true;
            }
        });
    }

    private void initList() {
        mSearchListAdapter = new SearchFragment.SearchListAdapter();
        mSearchList.setLayoutManager(new LinearLayoutManager(getContext()));
        mSearchList.setAdapter(mSearchListAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(getContext().getResources().getDrawable(R.drawable.search_list_divider));
        mSearchList.addItemDecoration(divider);
    }

    private class SearchListAdapter extends RecyclerView.Adapter<SearchFragment.SearchListAdapter.ViewHolder> {

        private List<Salesman> salesmen = new ArrayList<>();

        private int selectPos = -1;
        
        private Resources resources = getContext().getResources();

        SearchListAdapter() {
        }

        @NonNull
        @Override
        public SearchFragment.SearchListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.search_item, parent, false);
            return new SearchFragment.SearchListAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchFragment.SearchListAdapter.ViewHolder holder, int position) {
            holder.id.setText(salesmen.get(position).getWorkId());
            holder.name.setText(salesmen.get(position).getName());
            holder.company.setText(salesmen.get(position).getCompany());
            if (position == selectPos) {
                holder.image.setImageDrawable(resources.getDrawable(R.drawable.search_ic_person_select));
                holder.setTextColor(R.color.color_blue);
            } else {
                holder.image.setImageDrawable(resources.getDrawable(R.drawable.search_ic_person_un_select));
                holder.setTextColor(R.color.color_gray);
            }
            holder.rootView.setOnClickListener(v -> {
                selectPos = holder.getAdapterPosition();
                notifyDataSetChanged();
                mPresenter.selectSalesman(salesmen.get(selectPos));
                mDialog.show();
            });
        }

        @Override
        public int getItemCount() {
            return salesmen.size();
        }

        private void notifyListChange(List<Salesman> list) {
            salesmen = list;
            notifyDataSetChanged();
        }

        private void setBlankContent() {
            selectPos = -1;
            salesmen.clear();
            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView image;
            TextView id;
            TextView name;
            TextView company;
            View rootView;

            ViewHolder(View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
                id = itemView.findViewById(R.id.id);
                name = itemView.findViewById(R.id.name);
                company = itemView.findViewById(R.id.company);
                rootView = itemView.findViewById(R.id.root_view);
            }

            void setTextColor(@ColorRes int resId) {
                id.setTextColor(resources.getColor(resId));
                name.setTextColor(resources.getColor(resId));
                company.setTextColor(resources.getColor(resId));
            }
        }
    }

    @Override
    public void setListDataChanged(List<Salesman> list) {
        setListBlankContent();
        mSearchListAdapter.notifyListChange(list);
    }

    @Override
    public void setListBlankContent() {
        mSearchListAdapter.setBlankContent();
        mPresenter.selectSalesman(null);
    }

    @Override
    public void showCertificationView() {
        FunctionActivity.function(getActivity(), "Certification");
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }
}
