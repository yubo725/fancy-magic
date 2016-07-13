package com.magic.fancymagic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.magic.fancymagic.R;
import com.magic.fancymagic.bean.SpiritBean;
import com.magic.fancymagic.utils.SpiritUtils;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by yubo on 2016/7/13.
 */
public class QuestionView extends LinearLayout {

    @ViewInject(id = R.id.tv_title)
    TextView titleTv;

    @ViewInject(id = R.id.tv_answer1)
    TextView answer1Tv;

    @ViewInject(id = R.id.tv_answer2)
    TextView answer2Tv;

    private List<SpiritBean> questions;
    private int questionIndex;
    private OnQuestionSelectedListener listener;

    public QuestionView(Context context) {
        super(context);
        init();
    }

    public QuestionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QuestionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.question_view_layout, this);
        FinalActivity.initInjectedView(this, view);
        questionIndex = -1;
        loadQuestions();
    }

    //加载问题
    private void loadQuestions() {
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
        questions = JSON.parseArray(sb.toString(), SpiritBean.class);
    }

    public void showQuestion(int[] scores) {
        questionIndex++;
        SpiritBean bean = questions.get(questionIndex);
        titleTv.setText(bean.getTitle());
        answer1Tv.setText(String.format("1、%s", bean.getAnswer1()));
        answer2Tv.setText(String.format("2、%s", bean.getAnswer2()));
        if(scores[0] > 0) {
            answer1Tv.append(String.format("（+%d分）", scores[0]));
        }else {
            answer1Tv.append(String.format("（-%d分）", -scores[0]));
        }
        if(scores[1] > 0) {
            answer2Tv.append(String.format("（+%d分）", scores[1]));
        }else {
            answer2Tv.append(String.format("（-%d分）", -scores[1]));
        }
        answer1Tv.setTag(scores[0]);
        answer2Tv.setTag(scores[1]);
        answer1Tv.setOnClickListener(onAnswerClickListener);
        answer2Tv.setOnClickListener(onAnswerClickListener);
    }

    private OnClickListener onAnswerClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int score = (int) v.getTag();
            if(listener != null) {
                listener.onQuestionSelected(questionIndex, score);
            }
        }
    };

    public interface OnQuestionSelectedListener {
        void onQuestionSelected(int index, int score);
    }

    public void setOnQuestionSelectedListener(OnQuestionSelectedListener listener) {
        if(listener != null) {
            this.listener = listener;
        }
    }

    //返回当前问题索引
    public int getQuestionIndex() {
        return questionIndex;
    }

}
