package com.magic.fancymagic.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yubo on 2016/7/14.
 */
public class FileUtils {

    private static String pwdFileName = "config.dat";

    /**
     * 获取MD5加密后的字符串
     * @param str
     * @return
     */
    public static String getMD5(String str) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 保存密码
     * @param context
     * @param pwd 明文的密码
     */
    public static void savePwd(Context context, String pwd) {
        try {
            String dir = context.getFilesDir().getAbsolutePath();
            File f = new File(dir + File.separator + pwdFileName);
            if(!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(getMD5(pwd).getBytes());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查密码是否合法
     * @param context
     * @param pwd
     * @return 合法返回true，否则返回false
     */
    public static boolean checkPwd(Context context, String pwd) {
        if(TextUtils.isEmpty(pwd)) {
            return false;
        }
        try {
            String dir = context.getFilesDir().getAbsolutePath();
            File f = new File(dir + File.separator + pwdFileName);
            if(!f.exists()) {
                return false;
            }else {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line = "";
                StringBuilder sb = new StringBuilder();
                while((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                return pwd.equals(sb.toString());
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断密码是否存在
     * @param context
     * @return
     */
    public static boolean pwdExist(Context context) {
        String dir = context.getFilesDir().getAbsolutePath();
        File f = new File(dir + File.separator + pwdFileName);
        return f.exists() && f.length() > 0;
    }

}
