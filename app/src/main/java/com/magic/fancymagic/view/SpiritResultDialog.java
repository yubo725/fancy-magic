package com.magic.fancymagic.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.magic.fancymagic.BaseApplication;
import com.magic.fancymagic.R;
import com.magic.fancymagic.utils.SPUtils;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * Created by yubo on 2016/7/14.
 */
public class SpiritResultDialog extends Dialog {

    @ViewInject(id = R.id.hint_tv)
    TextView hintTv;

    @ViewInject(id = R.id.phone_text_view)
    TextView phoneTv;

    @ViewInject(id = R.id.ok_btn)
    Button okBtn;

    private OnDialogCloseListener onDialogCloseListener;

    public SpiritResultDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.spirit_result_dialog_layout, null);
        FinalActivity.initInjectedView(this, view);
        setContentView(view);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        String hint = hintTv.getText().toString();
        String phone = SPUtils.getInstance().getString(SPUtils.PHONE_NUM, "");
        String prefix = "";
        if(!TextUtils.isEmpty(phone)) {
            prefix = phone.substring(0, 3);
        }
        phoneTv.setText(phone);
        hintTv.setText(hint.replace("{prefix}", prefix));
        hintTv.setTypeface(BaseApplication.getInstance().getTypeface());
        phoneTv.setTypeface(BaseApplication.getInstance().getTypeface());

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(onDialogCloseListener != null) {
                    onDialogCloseListener.onDialogClosed();
                }
            }
        });
    }

    public interface OnDialogCloseListener{
        void onDialogClosed();
    }

    public void setOnDialogCloseListener(OnDialogCloseListener listener) {
        if(listener != null) {
            this.onDialogCloseListener = listener;
        }
    }

}
