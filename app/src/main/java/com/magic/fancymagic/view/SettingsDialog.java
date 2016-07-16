package com.magic.fancymagic.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.magic.fancymagic.BaseApplication;
import com.magic.fancymagic.R;
import com.magic.fancymagic.activity.RewardActivity;
import com.magic.fancymagic.activity.SecretActivity;
import com.magic.fancymagic.utils.FileUtils;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by yubo on 2016/7/14.
 * 设置对话框
 */
public class SettingsDialog extends Dialog {

    @ViewInject(id = R.id.secret_menu, click = "menuClicked")
    TextView secretBtn;

    @ViewInject(id = R.id.pwd_menu, click = "menuClicked")
    TextView pwdBtn;

    @ViewInject(id = R.id.share_menu, click = "menuClicked")
    TextView shareBtn;

    @ViewInject(id = R.id.reward_menu, click = "menuClicked")
    TextView rewardBtn;

    private SetPwdDialog setPwdDialog;
    private PwdDialog pwdDialog;
    private int selectedMenuId;

    public SettingsDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.settings_dialog_layout, null);
        setContentView(view);
        FinalActivity.initInjectedView(this, view);

        secretBtn.setTypeface(BaseApplication.getInstance().getTypeface());
        pwdBtn.setTypeface(BaseApplication.getInstance().getTypeface());
        shareBtn.setTypeface(BaseApplication.getInstance().getTypeface());
        rewardBtn.setTypeface(BaseApplication.getInstance().getTypeface());

        setPwdDialog = new SetPwdDialog(getContext(), R.style.loading_dialog);
        pwdDialog = new PwdDialog(getContext(), R.style.loading_dialog);
        pwdDialog.setOnPwdCheckedListener(new PwdDialog.OnPwdCheckedListener() {
            @Override
            public void onPwdChecked(boolean valid) {
                if (valid) {
                    switch (selectedMenuId) {
                        case R.id.secret_menu:
                            getContext().startActivity(new Intent(getContext(), SecretActivity.class));
                            break;
                        case R.id.pwd_menu:
                            setPwdDialog.show();
                            break;
                    }
                }
            }
        });
    }

    public void menuClicked(View view) {
        switch (view.getId()) {
            case R.id.secret_menu: //魔术揭秘
                selectedMenuId = R.id.secret_menu;
                if (FileUtils.pwdExist(getContext())) {
                    //如果密码已存在，则设置密码需要确认身份
                    pwdDialog.show();
                } else {
                    getContext().startActivity(new Intent(getContext(), SecretActivity.class));
                }
                break;
            case R.id.pwd_menu: //设置密码
                selectedMenuId = R.id.pwd_menu;
                if (FileUtils.pwdExist(getContext())) {
                    //如果密码已存在，则设置密码需要确认身份
                    pwdDialog.show();
                } else {
                    setPwdDialog.show();
                }
                break;
            case R.id.share_menu: //分享
                share();
                break;
            case R.id.reward_menu: //打赏
                getContext().startActivity(new Intent(getContext(), RewardActivity.class));
                break;
        }
        dismiss();
    }

    private void share() {
        ShareSDK.initSDK(getContext());
        OnekeyShare oks = new OnekeyShare();

        String shareUrl = "http://a.app.qq.com/o/simple.jsp?pkgname=com.magic.fancymagic";

        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("小小魔术");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://fir.im/3fzt");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我正在使用小小魔术这个APP，很好玩的魔术应用，你也来试试吧~" + shareUrl);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(shareUrl);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(shareUrl);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(shareUrl);

        // 启动分享GUI
        oks.show(getContext());
    }

}
