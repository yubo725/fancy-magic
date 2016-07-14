package com.magic.fancymagic.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.magic.fancymagic.BaseApplication;
import com.magic.fancymagic.R;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * Created by yubo on 2016/7/14.
 */
public class PwdDialog extends Dialog {

    @ViewInject(id = R.id.pwd_dialog_title)
    TextView dialogTitle;

    @ViewInject(id = R.id.pwd_et)
    EditText pwdEt;

    @ViewInject(id = R.id.pwd_btn)
    Button pwdBtn;

    public PwdDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.pwd_dialog_layout, null);
        FinalActivity.initInjectedView(this, view);
        setContentView(view);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        Typeface typeface = BaseApplication.getInstance().getTypeface();
        dialogTitle.setTypeface(typeface);
        pwdEt.setTypeface(typeface);
        pwdBtn.setTypeface(typeface);
    }

}
