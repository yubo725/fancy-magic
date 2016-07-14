package com.magic.fancymagic.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.magic.fancymagic.BaseApplication;
import com.magic.fancymagic.R;
import com.magic.fancymagic.activity.SecretActivity;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * Created by yubo on 2016/7/14.
 * 设置对话框
 */
public class SettingsDialog extends Dialog {

    @ViewInject(id = R.id.secret_menu, click = "menuClicked")
    TextView secretBtn;

    @ViewInject(id = R.id.pwd_menu, click = "menuClicked")
    TextView pwdBtn;

    private SetPwdDialog setPwdDialog;

    public SettingsDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.settings_dialog_layout, null);
        setContentView(view);
        FinalActivity.initInjectedView(this, view);

        secretBtn.setTypeface(BaseApplication.getInstance().getTypeface());
        pwdBtn.setTypeface(BaseApplication.getInstance().getTypeface());

        setPwdDialog = new SetPwdDialog(getContext(), R.style.loading_dialog);
    }

    public void menuClicked(View view) {
        switch(view.getId()) {
            case R.id.secret_menu:
                getContext().startActivity(new Intent(getContext(), SecretActivity.class));
                break;
            case R.id.pwd_menu:
                setPwdDialog.show();
                break;
        }
        dismiss();
    }

}
