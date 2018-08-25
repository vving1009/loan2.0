package com.jiaye.cashloan.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.jdcar.JdActivity;
import com.satcatche.library.widget.SatcatcheDialog;

public abstract class BaseListFragment extends BaseFunctionFragment {

    protected RecyclerView mList;

    protected ListAdapter mListAdapter;

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View rootView = inflater.inflate(R.layout.base_list_fragment, frameLayout);
        onFragmentCreate(rootView);
        initList();
        return rootView;
    }

    @Override
    protected void onClickBack() {
        onBackPressed();
    }

    @Override
    public boolean onBackPressed() {
        if (((JdActivity) getActivity()).goToBackFragment()) {
            return true;
        }
        return false;
    }

    private void initList() {
        mListAdapter = new ListAdapter();
        mList.setLayoutManager(new LinearLayoutManager(getContext()));
        mList.setAdapter(mListAdapter);
    }

    protected class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.jd_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            recyclerViewOnBindViewHolder(holder, position);
        }

        @Override
        public int getItemCount() {
            return getListSize();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            LinearLayout header;
            TextView headerText;
            TextView content;

            ViewHolder(View itemView) {
                super(itemView);
                header = itemView.findViewById(R.id.header);
                headerText = itemView.findViewById(R.id.header_text);
                content = itemView.findViewById(R.id.content);
                content.setOnClickListener(v -> {
                    onListItemClick(getLayoutPosition());
                });
            }

            public void setHeaderVisible(int visibilit) {
                header.setVisibility(visibilit);
            }

            public void setHeaderText(String text) {
                headerText.setText(text);
            }

            public void setContentText(String text) {
                content.setText(text);
            }
        }
    }

    protected abstract void recyclerViewOnBindViewHolder(ListAdapter.ViewHolder holder, int position);

    protected abstract int getListSize();

    protected abstract void onListItemClick(int position);

    protected abstract void onFragmentCreate(View rootView);

    protected void refreshRecyclerView() {
        mListAdapter.notifyDataSetChanged();
    }
}
