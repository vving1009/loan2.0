package com.jiaye.cashloan.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.car.CarActivity;
import com.jiaye.cashloan.widget.LetterIndexView;

public abstract class BaseListFragment extends BaseFunctionFragment {

    protected RecyclerView mRecyclerView;

    protected LetterIndexView mIndexView;

    protected ListAdapter mListAdapter;

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View rootView = inflater.inflate(R.layout.base_list_fragment, frameLayout);
        mRecyclerView = rootView.findViewById(R.id.list);
        mIndexView = rootView.findViewById(R.id.index_view);
        initList();
        onFragmentCreate(rootView);
        return rootView;
    }

    @Override
    protected void onClickBack() {
        if (!((CarActivity) getActivity()).goToBackFragment()) {
            getActivity().finish();
        }
    }

    @Override
    public boolean onBackPressed() {
        if (((CarActivity) getActivity()).goToBackFragment()) {
            return true;
        }
        return false;
    }

    private void initList() {
        mListAdapter = new ListAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mListAdapter);
    }

    protected class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.car_list_item, parent, false);
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
            LinearLayout body;
            TextView headerText;
            TextView content;
            ImageView imageView;

            ViewHolder(View itemView) {
                super(itemView);
                header = itemView.findViewById(R.id.header);
                body = itemView.findViewById(R.id.body);
                headerText = itemView.findViewById(R.id.header_text);
                content = itemView.findViewById(R.id.content);
                imageView = itemView.findViewById(R.id.image);
                content.setOnClickListener(v -> {
                    onListItemClick(getLayoutPosition());
                });
            }

            public void setHeaderVisible(int visibilit) {
                header.setVisibility(visibilit);
            }

            public void setBodyVisible(int visibilit) {
                body.setVisibility(visibilit);
            }

            public void setHeaderText(String text) {
                headerText.setText(text);
            }

            public void setContentText(String text) {
                content.setText(text);
            }

            public void setImage(String url) {
                imageView.setVisibility(View.VISIBLE);
                Glide.with(getContext()).load(url).into(imageView);
            }
        }
    }

    protected void setOnIndexViewListener(LetterIndexView.OnLetterSelectListener listener) {
        mIndexView.setVisibility(View.VISIBLE);
        mIndexView.setOnLetterSelectListener(listener);
    }

    protected abstract void recyclerViewOnBindViewHolder(ListAdapter.ViewHolder holder, int position);

    protected abstract int getListSize();

    protected abstract void onListItemClick(int position);

    protected abstract void onFragmentCreate(View rootView);

    protected void refreshRecyclerView() {
        mListAdapter.notifyDataSetChanged();
    }
}
