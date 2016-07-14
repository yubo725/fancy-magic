package com.magic.fancymagic.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
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
 * 设置密码
 */
public class SetPwdDialog extends Dialog {

    @ViewInject(id = R.id.dialog_title)
    TextView titleTv;

    @ViewInject(id = R.id.pwd_et)
    EditText pwdEt;

    @ViewInject(id = R.id.pwd_et2)
    EditText pwdEt2;

    @ViewInject(id = R.id.ok_btn)
    Button okBtn;

    @ViewInject(id = R.id.cancel_btn)
    Button cancelBtn;

    public SetPwdDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.set_pwd_dialog_layout, null);
        FinalActivity.initInjectedView(this, view);
        setContentView(view);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        Typeface typeface = BaseApplication.getInstance().getTypeface();
        titleTv.setTypeface(typeface);
        pwdEt.setTypeface(typeface);
        pwdEt2.setTypeface(typeface);
        okBtn.setTypeface(typeface);
        cancelBtn.setTypeface(typeface);
        okBtn.setOnClickListener(onClickListener);
        cancelBtn.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.ok_btn) {
                String pwdStr1 = pwdEt.getText().toString();
                String pwdStr2 = pwdEt2.getText().toString();
                if (TextUtils.isEmpty(pwdStr1) || TextUtils.isEmpty(pwdStr2)) {
                    showToast("密码不能为空");
                } else if (!pwdStr1.equals(pwdStr2)) {
                    showToast("两次输入的密码不一致");
                } else {
                    //保存密码，关闭对话框
                    FileUtils.savePwd(getContext(), pwdStr1);
                    showToast("密码已保存");
                    dismiss();
                }
            }else {
                dismiss();
            }
        }
    };

    private void showToast(String msg) {
        Toast.makeText(SetPwdDialog.this.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void show() {
        super.show();
        pwdEt.setText("");
        pwdEt2.setText("");
    }
}
