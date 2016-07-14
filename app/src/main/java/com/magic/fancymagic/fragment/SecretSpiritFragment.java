package com.magic.fancymagic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.magic.fancymagic.R;
import com.magic.fancymagic.view.SpiritHintDialog;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * Created by yubo on 2016/7/14.
 */
public class SecretSpiritFragment extends Fragment {

    private View rootView;

    @ViewInject(id = R.id.reset_phone_btn, click = "resetBtnClick")
    Button resetBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_spirit_layout, null);
            FinalActivity.initInjectedView(this, rootView);
        }
        return rootView;
    }

    //重置手机号
    public void resetBtnClick(View view) {
        SpiritHintDialog hintDialog = new SpiritHintDialog(getContext(), R.style.loading_dialog);
        hintDialog.setCancelable(true);
        hintDialog.setCanceledOnTouchOutside(true);
        hintDialog.show();
    }
}
