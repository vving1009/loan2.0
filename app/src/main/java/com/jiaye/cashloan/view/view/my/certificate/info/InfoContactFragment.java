package com.jiaye.cashloan.view.view.my.certificate.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.my.certificate.info.contact.source.InfoContactRepository;

/**
 * InfoContactFragment
 *
 * @author 贾博瑄
 */

public class InfoContactFragment extends BaseFragment implements InfoContactContract.View {

    private InfoContactContract.Presenter mPresenter;

    private TextView mTextFamilyName;

    private TextView mTextFamily;

    private TextView mTextFamilyPhone;

    private TextView mTextFriendName;

    private TextView mTextFriend;

    private TextView mTextFriendPhone;

    public static InfoContactFragment newInstance() {
        Bundle args = new Bundle();
        InfoContactFragment fragment = new InfoContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_contact_fragment,container,false);
        mTextFamilyName = view.findViewById(R.id.text_family_name);
        mTextFamily = view.findViewById(R.id.text_family);
        mTextFamilyPhone = view.findViewById(R.id.text_family_phone);
        mTextFriendName = view.findViewById(R.id.text_friend_name);
        mTextFriend = view.findViewById(R.id.text_friend);
        mTextFriendPhone = view.findViewById(R.id.text_friend_phone);
        mPresenter = new InfoContactPresenter(this, new InfoContactRepository());
        mPresenter.subscribe();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void setFamilyName(String text) {
        mTextFamilyName.setText(text);
    }

    @Override
    public void setFamily(String text) {
        mTextFamily.setText(text);
    }

    @Override
    public void setFamilyPhone(String text) {
        mTextFamilyPhone.setText(text);
    }

    @Override
    public void setFriendName(String text) {
        mTextFriendName.setText(text);
    }

    @Override
    public void setFriend(String text) {
        mTextFriend.setText(text);
    }

    @Override
    public void setFriendPhone(String text) {
        mTextFriendPhone.setText(text);
    }
}
