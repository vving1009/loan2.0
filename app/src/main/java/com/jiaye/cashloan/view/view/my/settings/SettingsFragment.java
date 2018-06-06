package com.jiaye.cashloan.view.view.my.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.my.settings.source.SettingsRepository;
import com.jiaye.cashloan.view.view.my.MyActivity;

/**
 * SettingsFragment
 *
 * @author 贾博瑄
 */

public class SettingsFragment extends BaseFragment implements SettingsContract.View {

    private SettingsContract.Presenter mPresenter;

    private SwitchCompat mSwitch;

    public static SettingsFragment newInstance() {
        Bundle args = new Bundle();
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.settings_fragment, container, false);
        mSwitch = root.findViewById(R.id.switch_gesture);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mSwitch.isPressed() && isChecked) {
                    showGestureView();
                } else if (mSwitch.isPressed() && !isChecked) {
                    mPresenter.removeGesturePassword();
                }
            }
        });
        root.findViewById(R.id.layout_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPasswordView();
            }
        });
        root.findViewById(R.id.layout_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.exit();
            }
        });
        mPresenter = new SettingsPresenter(this, new SettingsRepository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getGestureStatus();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void setSwitch(boolean check) {
        mSwitch.setChecked(check);
    }

    @Override
    public void showGestureView() {
        Intent intent = new Intent(getActivity(), MyActivity.class);
        intent.putExtra("view", "gesture");
        getActivity().startActivity(intent);
    }

    @Override
    public void result() {
        getActivity().finish();
    }

    private void showPasswordView() {
    }
}
