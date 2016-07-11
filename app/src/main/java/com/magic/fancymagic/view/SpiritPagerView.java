package com.magic.fancymagic.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.magic.fancymagic.BaseApplication;
import com.magic.fancymagic.R;
import com.magic.fancymagic.activity.SpiritActivity;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yubo on 2016/7/5.
 */
public class SpiritPagerView extends LinearLayout {

    @ViewInject(id = R.id.start_btn)
    Button startBtn;

    @ViewInject(id = R.id.phone_et)
    EditText phoneEt;

    private Pattern pattern;

    public SpiritPagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SpiritPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpiritPagerView(Context context) {
        super(context);
        init();
    }
 
    private void init() {
        View view = inflate(getContext(), R.layout.spirit_pager_view_layout, this);
        FinalActivity.initInjectedView(this, view);
        startBtn.setTypeface(BaseApplication.getInstance().getTypeface());
        pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        startBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneStr = phoneEt.getText().toString();
                if(TextUtils.isEmpty(phoneStr) || !isPhoneNumValid(phoneStr)) {
                    Toast.makeText(getContext(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                }else {
                    getContext().startActivity(new Intent(getContext(), SpiritActivity.class));
                }
            }
        });
    }

    private boolean isPhoneNumValid(String phoneStr) {
        Matcher matcher = pattern.matcher(phoneStr);
        return matcher.matches();
    }
}
