package com.magic.fancymagic.bean;

import java.io.Serializable;

/**
 * Created by yubo on 2016/7/12.
 */
public class SpiritBean implements Serializable {

    private int id;
    private String title;
    private String answer1;
    private String answer2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }
}
