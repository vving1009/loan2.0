package com.jiaye.cashloan.view.taobao;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.taobao.source.TaoBaoQRRepository;

import java.net.URISyntaxException;

/**
 * TaoBaoQRFragment
 *
 * @author 贾博瑄
 */

public class TaoBaoQRFragment extends BaseFragment implements TaoBaoQRContract.View {

    private TaoBaoQRContract.Presenter mPresenter;

    public static TaoBaoQRFragment newInstance() {
        Bundle args = new Bundle();
        TaoBaoQRFragment fragment = new TaoBaoQRFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ImageView mImgQRCode;

    private Button mRpc;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.taobao_qrcode_fragment, container, false);
        mImgQRCode = root.findViewById(R.id.img_qrcode);
        mRpc = root.findViewById(R.id.btn_commit);
        mRpc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = Intent.parseUri(mPresenter.getRpc(), Intent.URI_INTENT_SCHEME);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    startActivity(intent);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (ActivityNotFoundException e) {
                    showToastById(R.string.loan_auth_taobao_qr_error);
                    e.printStackTrace();
                }
            }
        });
        mPresenter = new TaoBaoQRPresenter(this, new TaoBaoQRRepository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void setImg(Bitmap bitmap) {
        mImgQRCode.setImageBitmap(bitmap);
    }

    @Override
    public void showRpc() {
        mRpc.setVisibility(View.VISIBLE);
    }

    @Override
    public void result() {
        getActivity().finish();
    }

    public void request() {
        mPresenter.request();
    }
}
