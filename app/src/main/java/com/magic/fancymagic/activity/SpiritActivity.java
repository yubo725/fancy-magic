package com.magic.fancymagic.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.magic.fancymagic.BaseApplication;
import com.magic.fancymagic.R;
import com.magic.fancymagic.utils.SPUtils;
import com.magic.fancymagic.utils.SpiritUtils;
import com.magic.fancymagic.view.NumberView;
import com.magic.fancymagic.view.QuestionView;
import com.magic.fancymagic.view.SpiritResultDialog;
import com.magic.fancymagic.view.TitleView;

import net.tsz.afinal.annotation.view.ViewInject;

/**
 * 心灵密码
 */
public class SpiritActivity extends BaseActivity {

    @ViewInject(id = R.id.title_view)
    TitleView titleView;

    @ViewInject(id = R.id.number_view)
    NumberView numberView;

    @ViewInject(id = R.id.number_view_result)
    NumberView resultNumberView;

    @ViewInject(id = R.id.hint_text)
    TextView hintTv;

    @ViewInject(id = R.id.start_btn, click = "startBtnClick")
    Button startBtn;

    @ViewInject(id = R.id.question_view)
    QuestionView questionView;

    private String phoneNum;
    private SpiritUtils spiritUtils;

    private SpiritResultDialog spiritResultDialog;

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
        startBtn.setTypeface(BaseApplication.getInstance().getTypeface());

        spiritResultDialog = new SpiritResultDialog(this, R.style.loading_dialog);
        spiritResultDialog.setOnDialogCloseListener(new SpiritResultDialog.OnDialogCloseListener() {
            @Override
            public void onDialogClosed() {
                questionView.setVisibility(View.GONE);
                startBtn.setVisibility(View.VISIBLE);
                resultNumberView.setResultNumberView(true);
            }
        });

        boolean phoneSaved = SPUtils.getInstance().getBoolean(SPUtils.PHONE_SAVED, false);
        if(phoneSaved) {
            resultNumberView.setPhoneNumber(SPUtils.getInstance().getString(SPUtils.PHONE_NUM, ""));
            resultNumberView.setResultNumberView(true);
        }
    }

    private SpiritUtils.OnCompleteListener onCompleteListener = new SpiritUtils.OnCompleteListener() {
        @Override
        public void onComplete() {
            questionView.resetQuestionIndex();
            spiritResultDialog.show();
        }
    };

    public void startBtnClick(View view) {
        view.setVisibility(View.GONE);
        questionView.setVisibility(View.VISIBLE);
        spiritUtils = new SpiritUtils(numberView, resultNumberView, questionView);
        spiritUtils.setOnCompleteListener(onCompleteListener);
        spiritUtils.startQuestion();
    }

}
