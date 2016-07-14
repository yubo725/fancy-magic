package com.magic.fancymagic.utils;

import android.util.Log;
import android.widget.Toast;

import com.magic.fancymagic.view.NumberView;
import com.magic.fancymagic.view.QuestionView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Created by yubo on 2016/7/13.
 */
public class SpiritUtils {

    private NumberView numberView;
    private NumberView resultNumberView;
    private QuestionView questionView;

    private int[] nums;
    private int[] resultNums;

    private Random random;
    private List<Integer> selectedNumList;

    public SpiritUtils(NumberView numberView, NumberView resultNumberView, QuestionView questionView) {
        this.numberView = numberView;
        this.resultNumberView = resultNumberView;
        this.questionView = questionView;

        nums = this.numberView.getNumbers();
        resultNums = this.resultNumberView.getNumbers();

        random = new Random();
        selectedNumList = new ArrayList<>();
    }

    /** 获取分数，index为第几次获取，取值范围0-3 */
    public int[] getScore(int index) {
        int[] result = new int[2];
        int n = getUnselectedNum();
        result[0] = n - nums[index];
        result[1] = nums[index] + n;
        return result;
    }

    private int getUnselectedNum() {
        int[] list = resultNumberView.getNumbers();
        int n = list[random.nextInt(list.length)];
        while(selectedNumList.contains(n)) {
            n = list[random.nextInt(list.length)];
        }
        return n;
    }

    private QuestionView.OnQuestionSelectedListener onQuestionSelectedListener = new QuestionView.OnQuestionSelectedListener() {
        @Override
        public void onQuestionSelected(int index, String score) {
            if(index <= 3) {
                int n;
                try {
                    n = Integer.parseInt(score);
                    resultNumberView.showNumber(n + nums[index]);
                    selectedNumList.add(n + nums[index]);
                }catch (Exception e) {
                    n = Integer.parseInt(score.substring(0, score.length() - 1));
                    resultNumberView.showNumber(n - nums[index]);
                    selectedNumList.add(n - nums[index]);
                }
                if(index < 3) {
                    questionView.showQuestion(getScore(index + 1));
                    numberView.setHighLight(index + 1);
                }else {
                    Toast.makeText(questionView.getContext(), "答题完毕", Toast.LENGTH_SHORT).show();
                    numberView.setHighLight(index + 1);
                    if(onCompleteListener != null) {
                        onCompleteListener.onComplete();
                    }
                }
            }
        }
    };

    public interface OnCompleteListener{
        void onComplete();
    }

    private OnCompleteListener onCompleteListener;

    public void setOnCompleteListener(OnCompleteListener listener) {
        if(listener != null) {
            this.onCompleteListener = listener;
        }
    }

    public void startQuestion() {
        questionView.setOnQuestionSelectedListener(onQuestionSelectedListener);
        questionView.showQuestion(getScore(0));
        numberView.setHighLight(0);
        selectedNumList.clear();
    }

}
