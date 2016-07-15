package com.magic.fancymagic.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.magic.fancymagic.BaseApplication;
import com.magic.fancymagic.R;
import com.magic.fancymagic.utils.FileUtils;

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

    @ViewInject(id = R.id.pwd_btn, click = "unlockBtnClick")
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

    public void unlockBtnClick(View view) {
        String pwdStr = pwdEt.getText().toString();
        if(TextUtils.isEmpty(pwdStr)) {
            Toast.makeText(getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
            checkPwdResult(false);
        }else if(!FileUtils.checkPwd(getContext(), pwdStr)) {
            Toast.makeText(getContext(), "密码不正确", Toast.LENGTH_SHORT).show();
            checkPwdResult(false);
        }else {
            dismiss();
            checkPwdResult(true);
        }
    }

    private void checkPwdResult(boolean valid) {
        if(this.onPwdCheckedListener != null) {
            onPwdCheckedListener.onPwdChecked(valid);
        }
    }

    public interface OnPwdCheckedListener {
        void onPwdChecked(boolean valid);
    }

    private OnPwdCheckedListener onPwdCheckedListener;

    public void setOnPwdCheckedListener(OnPwdCheckedListener listener) {
        if(listener != null) {
            this.onPwdCheckedListener = listener;
        }
    }

    @Override
    public void show() {
        super.show();
        pwdEt.setText("");
    }
}
