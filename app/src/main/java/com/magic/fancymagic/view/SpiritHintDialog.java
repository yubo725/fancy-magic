package com.magic.fancymagic.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.magic.fancymagic.BaseApplication;
import com.magic.fancymagic.R;
import com.magic.fancymagic.utils.PhoneUtils;
import com.magic.fancymagic.utils.SPUtils;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * Created by yubo on 2016/7/13.
 */
public class SpiritHintDialog extends Dialog {

    @ViewInject(id = R.id.hint_tv)
    TextView hintTv;

    @ViewInject(id = R.id.phone_et)
    EditText phoneEt;

    @ViewInject(id = R.id.save_btn)
    Button saveBtn;

    public SpiritHintDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.spirit_hint_dialog_layout, null);
        FinalActivity.initInjectedView(this, view);
        setContentView(view);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        hintTv.setTypeface(BaseApplication.getInstance().getTypeface());
        saveBtn.setOnClickListener(saveBtnClickListener);
    }

    private View.OnClickListener saveBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String phoneStr = phoneEt.getText().toString();
            if(TextUtils.isEmpty(phoneStr) || !PhoneUtils.isPhoneNumberValid(phoneStr)) {
                Toast.makeText(SpiritHintDialog.this.getContext(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            }else {
                SPUtils.getInstance().setBoolean(SPUtils.PHONE_SAVED, true);
                SPUtils.getInstance().setString(SPUtils.PHONE_NUM, phoneStr);
                SpiritHintDialog.this.cancel();
                Toast.makeText(SpiritHintDialog.this.getContext(), "手机号已保存", Toast.LENGTH_SHORT).show();
            }
        }
    };

}
