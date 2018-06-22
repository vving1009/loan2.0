package com.jiaye.cashloan.view.company;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.persistence.Salesman;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.FunctionActivity;
import com.jiaye.cashloan.view.company.source.CompanyRepository;
import com.jiaye.cashloan.view.search.SearchFragment;
import com.jiaye.cashloan.widget.SatcatcheDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CompanyFragment
 *
 * @author 贾博瑄
 */

public class CompanyFragment extends BaseFragment implements CompanyContract.View {

    private CompanyContract.Presenter mPresenter;
    private RecyclerView mCompanyList;
    private RecyclerView mPersonList;
    private CompanyListAdapter mCompanyListAdapter;
    private PersonListAdapter mPersonListAdapter;
    private SatcatcheDialog mDialog;

    public static CompanyFragment newInstance(String city) {
        Bundle args = new Bundle();
        args.putString("city", city);
        CompanyFragment fragment = new CompanyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.company_fragment, container, false);
        mDialog = new SatcatcheDialog.Builder(getContext())
                .setTitle("提示")
                .setMessage("是否选择当前客户经理")
                .setPositiveButton("是", ((dialog, which) -> mPresenter.saveSalesman()))
                .setNegativeButton("否", null)
                .build();
        mCompanyList = root.findViewById(R.id.company_list);
        mPersonList = root.findViewById(R.id.person_list);
        root.findViewById(R.id.search_btn).setOnClickListener(v -> FunctionActivity.function(getActivity(), "Search"));
        root.findViewById(R.id.img_back).setOnClickListener(v -> Objects.requireNonNull(getActivity()).finish());
        initList();
        mCompanyList.requestFocus();
        mPresenter = new CompanyPresenter(this, new CompanyRepository());
        mPresenter.subscribe();
        registerLocalBroadcast();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
        unRegisterLocalBroadcast();
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
    public void setInitCity() {
        mPresenter.queryCompany(getArguments().getString("city", ""));
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
                holder.rootView.setBackgroundColor(getContext().getResources().getColor(R.color.color_gray_light));
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
            mCompanyList.scrollToPosition(selectPos);
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
                holder.name.setTextColor(getContext().getResources().getColor(R.color.color_blue));
                holder.phone.setTextColor(getContext().getResources().getColor(R.color.color_blue));
            } else {
                holder.name.setTextColor(getContext().getResources().getColor(R.color.color_gray));
                holder.phone.setTextColor(getContext().getResources().getColor(R.color.color_gray));
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

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getActivity().finish();
        }
    };

    private void registerLocalBroadcast(){
        IntentFilter intentFilter = new IntentFilter(SearchFragment.FINISH_COMPANY_FRAGMENT_ACTION);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mReceiver,intentFilter);
    }

    private void unRegisterLocalBroadcast(){
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mReceiver);
    }
}
