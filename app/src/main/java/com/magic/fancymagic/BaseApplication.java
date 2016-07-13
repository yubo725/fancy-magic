package com.magic.fancymagic;

import android.app.Application;
import android.graphics.Typeface;

import com.magic.fancymagic.utils.SPUtils;

/**
 * Created by yubo on 2016/7/7.
 */
public class BaseApplication extends Application {

    private static Typeface typeface;
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        SPUtils.init(this);

        /** 加载字体 */
        String path = "fonts/fzktjt.ttf";
        Typeface typeface = Typeface.createFromAsset(getAssets(), path);
        setTypeface(typeface);

        instance = this;
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    /** 设置方正卡通字体 */
    public Typeface getTypeface() {
        return typeface;
    }

    /** 获取方正卡通字体 */
    public void setTypeface(Typeface typeface) {
        BaseApplication.typeface = typeface;
    }
}
