package com.magic.fancymagic.bean;

import java.io.Serializable;

/**
 * Created by yubo on 2016/7/8.
 */
public class CityBean implements Serializable {

    private int id;
    private String cityName;
    private char firstLetter;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public char getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(char firstLetter) {
        this.firstLetter = firstLetter;
    }
}
