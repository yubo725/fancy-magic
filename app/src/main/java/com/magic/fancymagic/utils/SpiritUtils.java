package com.magic.fancymagic.utils;

import android.util.Log;

import com.magic.fancymagic.view.NumberView;
import com.magic.fancymagic.view.QuestionView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        //从结果中随机选择两个数
        if(index == 0) {
            selectedNumList.clear();
        }
        int n1 = getRandomNum();
        int n2 = getRandomNum();
        while(n2 == n1) {
            n2 = getRandomNum();
        }
        return new int[]{n1 - nums[index], n2 - nums[index]};
    }

    private int getRandomNum() {
        int n = resultNums[random.nextInt(4)];
        while(selectedNumList.contains(n)) {
            n = resultNums[random.nextInt(4)];
        }
        return n;
    }

    private QuestionView.OnQuestionSelectedListener onQuestionSelectedListener = new QuestionView.OnQuestionSelectedListener() {
        @Override
        public void onQuestionSelected(int index, int score) {
            selectedNumList.add(nums[index] + score);
            questionView.showQuestion(getScore(index + 1));
        }
    };

    public void startQuestion() {
        questionView.setOnQuestionSelectedListener(onQuestionSelectedListener);
        questionView.showQuestion(getScore(0));
    }

}
