package com.jiaye.cashloan.view.search;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.persistence.Salesman;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.FunctionActivity;
import com.jiaye.cashloan.view.search.source.SearchRepository;

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

    private SearchContract.Presenter mPresenter;
    private SearchView mSearchView;
    private RecyclerView mCompanyList;
    private RecyclerView mPersonList;
    private CompanyListAdapter mCompanyListAdapter;
    private PersonListAdapter mPersonListAdapter;

    public static SearchFragment newInstance(String city) {
        Bundle args = new Bundle();
        args.putString("city", city);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.search_fragment, container, false);
        mSearchView = root.findViewById(R.id.search_view);
        mCompanyList = root.findViewById(R.id.company_list);
        mPersonList = root.findViewById(R.id.person_list);
        root.findViewById(R.id.img_back).setOnClickListener(v ->
                Objects.requireNonNull(getActivity()).finish());
        root.findViewById(R.id.ok_btn).setOnClickListener(v -> {
            mPresenter.saveSalesman();
        });
        initList();
        initSearchView();
        mCompanyList.requestFocus();
        new Handler().postDelayed(() ->
                mSearchView.setQuery(getArguments().getString("city", ""), true), 200);
        mPresenter = new SearchPresenter(this, new SearchRepository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void setCompanyListItemSelected(String company) {
        mCompanyListAdapter.setItemSelected(company);
    }

    @Override
    public void setCompanyListNoneSelected() {
        mCompanyListAdapter.setNoneSelected();
    }

    @Override
    public void setCompanyListDataChanged(List<String> list) {
        mCompanyListAdapter.notifyListChange(list);
    }

    @Override
    public void setPersonListBlankContent() {
        mPersonListAdapter.setBlankContent();
        mPresenter.selectSalesman(null);
    }

    @Override
    public void showCertificationView() {
        FunctionActivity.function(getActivity(), "Certification");
        getActivity().finish();
    }

    @Override
    public void setPersonListDataChanged(List<Salesman> list) {
        setPersonListBlankContent();
        mPersonListAdapter.notifyListChange(list);
    }

    private void initSearchView() {
        TextView textView = mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(14);
        textView.setHintTextColor(Color.GRAY);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        SpannableString spanText = new SpannableString("请输入营业厅名称、姓名、工号");
        spanText.setSpan(new AbsoluteSizeSpan(14, true), 0, spanText.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mSearchView.setQueryHint(spanText);// 设置字体大小
        //动态显示删除✖键
        mSearchView.onActionViewExpanded();
        mSearchView.setIconified(true);

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
                mPresenter.queryPeopleBySearchView(newText);
                return true;
            }
        });
    }

    private void initList() {
        mCompanyListAdapter = new CompanyListAdapter();
        mPersonListAdapter = new PersonListAdapter();
        mCompanyList.setLayoutManager(new LinearLayoutManager(getContext()));
        mPersonList.setLayoutManager(new LinearLayoutManager(getContext()));
        mCompanyList.setAdapter(mCompanyListAdapter);
        mPersonList.setAdapter(mPersonListAdapter);
    }

    private class CompanyListAdapter extends RecyclerView.Adapter<CompanyListAdapter.ViewHolder> {

        private List<String> companys = new ArrayList<>();

        private int selectPos = -1;

        CompanyListAdapter() {
        }

        @NonNull
        @Override
        public CompanyListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.salescompany_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CompanyListAdapter.ViewHolder holder, int position) {
            holder.text.setText(companys.get(position));
            if (position == selectPos) {
                holder.rootView.setBackgroundColor(getContext().getResources().getColor(android.R.color.white));
            } else {
                holder.rootView.setBackgroundColor(getContext().getResources().getColor(R.color.salesman_list_item_bg));
            }
            holder.rootView.setOnClickListener(v -> {
                selectPos = holder.getAdapterPosition();
                notifyDataSetChanged();
                mPresenter.queryPeopleByCompanyList(DbContract.Salesman.COLUMN_COMPANY, companys.get(position));
            });
        }

        @Override
        public int getItemCount() {
            return companys.size();
        }

        private void setItemSelected(String company) {
            selectPos = companys.indexOf(company);
            notifyDataSetChanged();
        }

        private void notifyListChange(List<String> list) {
            companys = list;
            notifyDataSetChanged();
        }

        private void setNoneSelected() {
            selectPos = -1;
            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView text;
            View rootView;

            ViewHolder(View itemView) {
                super(itemView);
                text = itemView.findViewById(R.id.text);
                rootView = itemView.findViewById(R.id.root_view);
            }
        }
    }

    private class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.ViewHolder> {

        private List<Salesman> salesmen = new ArrayList<>();

        private int selectPos = -1;

        PersonListAdapter() {
        }

        @NonNull
        @Override
        public PersonListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.salesman_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PersonListAdapter.ViewHolder holder, int position) {
            holder.name.setText(salesmen.get(position).getName());
            holder.phone.setText(salesmen.get(position).getWorkId());
            if (position == selectPos) {
                holder.name.setTextColor(getContext().getResources().getColor(R.color.salesman_list_text_selected));
                holder.phone.setTextColor(getContext().getResources().getColor(R.color.salesman_list_text_selected));
            } else {
                holder.name.setTextColor(getContext().getResources().getColor(R.color.salesman_list_text));
                holder.phone.setTextColor(getContext().getResources().getColor(R.color.salesman_list_text));
            }
            holder.rootView.setOnClickListener(v -> {
                selectPos = holder.getAdapterPosition();
                notifyDataSetChanged();
                mPresenter.selectSalesman(salesmen.get(selectPos));
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

            TextView id;
            TextView name;
            TextView phone;
            View rootView;

            ViewHolder(View itemView) {
                super(itemView);
                id = itemView.findViewById(R.id.id);
                name = itemView.findViewById(R.id.name);
                phone = itemView.findViewById(R.id.phone);
                rootView = itemView.findViewById(R.id.root_view);
            }
        }
    }
}
