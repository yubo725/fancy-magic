package com.magic.fancymagic.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.magic.fancymagic.BaseApplication;
import com.magic.fancymagic.R;
import com.magic.fancymagic.bean.SpiritBean;
import com.magic.fancymagic.view.NumberView;
import com.magic.fancymagic.view.TitleView;

import net.tsz.afinal.annotation.view.ViewInject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 心灵密码
 */
public class SpiritActivity extends BaseActivity {

    @ViewInject(id = R.id.title_view)
    TitleView titleView;

    @ViewInject(id = R.id.number_view)
    NumberView numberView;

    @ViewInject(id = R.id.hint_text)
    TextView hintTv;

    private String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spirit);
        initView();
    }

    private void initView() {
        titleView.setTitle("心灵密码");

        phoneNum = getIntent().getStringExtra("PhoneNumber");
        numberView.setPhoneNumber(phoneNum);

        hintTv.setText(Html.fromHtml("很好！现在你的手机号<font color='red'>后8位</font>被分成了上面<font color='red'>4组数字</font>，接下来你会看到几个简单的问题，按照你的直觉做出选择吧，答案会对上面4组数字做出相应的修正，最后得出你的<font color='red'>命运数字</font>哦~"));
        hintTv.setTypeface(BaseApplication.getInstance().getTypeface());
    }

    //加载问题
    private List<SpiritBean> loadQuestions() {
        InputStream is = getResources().openRawResource(R.raw.spirit);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder sb = new StringBuilder();
        try {
            while((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<SpiritBean> list = JSON.parseArray(sb.toString(), SpiritBean.class);
        return list;
    }

}
