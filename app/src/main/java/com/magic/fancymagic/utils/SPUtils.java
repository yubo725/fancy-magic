package com.magic.fancymagic.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yubo on 2015/7/25.
 * 操作SharedPreference的工具类
 */
public class SPUtils {

    private static final String SP_NAME = "Config";
    private static SPUtils spUtils;
    private static SharedPreferences sp;

    public static final String PHONE_NUM = "phone_num";
    public static final String PHONE_SAVED = "phone_saved";

    private SPUtils(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
    }

    public static void init(Context context) {
        if (spUtils == null) {
            spUtils = new SPUtils(context);
        }
    }

    public static SPUtils getInstance() {
        if (spUtils == null) {
            throw new IllegalStateException("please call init before use SPUtils.");
        }
        return spUtils;
    }

    public void setBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public void setString(String key, String value) {
        sp.edit().putString(key, value).commit();
    }

    public String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public void setFloat(String key, float value){
        sp.edit().putFloat(key, value).commit();
    }

    public float getFloat(String key, float defValue){
        return sp.getFloat(key, defValue);
    }

    public void setInt(String key, int value){
        sp.edit().putInt(key, value).commit();
    }

    public int getInt(String key, int defValue){
        return sp.getInt(key, defValue);
    }

    public void setLong(String key, long value){
        sp.edit().putLong(key, value).commit();
    }

    public long getLong(String key, long defValue){
        return sp.getLong(key, defValue);
    }

}
