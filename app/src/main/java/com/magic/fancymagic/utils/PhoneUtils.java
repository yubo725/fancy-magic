package com.magic.fancymagic.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yubo on 2015/7/22.
 * 操作手机的工具类
 */
public class PhoneUtils {

    private static boolean isCameraPermissionEnable = false;

    /**
     * 判断摄像头权限是否可用
     */
    public static boolean checkCameraPermission() {
        Thread t = new Thread() {
            @Override
            public void run() {
                super.run();
                Camera camera = null;
                try {
                    camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
                    camera.cancelAutoFocus();//如果没有权限，这句会报错
                    isCameraPermissionEnable = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    isCameraPermissionEnable = false;
                    camera = null;
                } finally {
                    if(isCameraPermissionEnable && camera != null) {
                        camera.release();
                        camera = null;
                    }
                }
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return isCameraPermissionEnable;
    }

    /**
     * 获取手机状态栏的高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context){
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resId > 0){
            result = context.getResources().getDimensionPixelSize(resId);
        }
        return result;
    }

    /**
     * 震动手机一次
     * @param context
     * @param duration 震动的时长，单位毫秒
     */
    public static void vibrateOnce(Context context, long duration){
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(duration);
    }

    /** 获取手机的Mac地址 */
    public static String getMac(Context context) {
        try {
            WifiManager manager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            return manager.getConnectionInfo().getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 获取Wifi网关地址 */
    public static String getGateway(Context context){
        try {
            WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            DhcpInfo dhcpInfo = manager.getDhcpInfo();
            return intToIP(dhcpInfo.gateway);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 获取手机的IP地址 */
    public static String getIP(Context context) {
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (wifiManager.isWifiEnabled()) {
            //如果WiFi开启
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            return intToIP(ipAddress);
        }
        //WiFi没开
        return getGPRSIP();
    }

    /** 通过流量上网时，获取IP地址 */
    private static String getGPRSIP() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 获取手机的IMEI */
    public static String getIMEI(Context context){
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getDeviceId();
    }

    /** 将整型数据转化为字符串型IP */
    private static String intToIP(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                + "." + (i >> 24 & 0xFF);
    }

    /** 判断手机是否有SD卡 */
    public static boolean hasSDCard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**判断应用是否在后台运行*/
    public static boolean isAppRunningInBackground(Context context){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /** 获取当前的Activity的全名 */
    public static String getCurrentActivityName(Context context){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        return componentInfo.getClassName();
    }

    /**
     * 判断是否有前置摄像
     * @return
     */
    @SuppressLint("NewApi")
    public static boolean hasFrontCamera(){
        Camera.CameraInfo info = new Camera.CameraInfo();
        int count = Camera.getNumberOfCameras();
        for(int i = 0; i < count; i++){
            Camera.getCameraInfo(i, info);
            if(info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                return true;
            }
        }
        return false;
    }

    /**
     * 获取手机屏幕的长宽
     * @param context
     * @return
     */
    public static Point getScreenSize(Context context){
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return new Point(metrics.widthPixels, metrics.heightPixels);
    }

    /**
     * 获取当前应用的版本号
     * 获取失败返回-1
     * @param context
     * @return
     */
    public static int getVersionCode(Context context){
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 判断网络是否可用
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context){
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if(info != null) {
                return info.isAvailable();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取全屏的最佳预览分辨率
     * @param context
     * @param camera
     * @return
     */
    public static Camera.Size getBestFullScreenPreviewSize(Context context, Camera camera) {
        Point point = getScreenSize(context);
        float screenRate = point.y * 1.0f / point.x;
        float delta = 5;
        float supportPreviewRate;
        Camera.Size bestPreviewSize = null;
        List<Camera.Size> list = camera.getParameters().getSupportedPreviewSizes();
        if(list != null && list.size() > 0) {
            Collections.sort(list, comparator);
            int selectSize = list.size();
            if(selectSize > 3) {
                selectSize = 3;
            }
            for(int i = 0; i < selectSize; i++) {
                Camera.Size size = list.get(i);
                supportPreviewRate = Math.abs(screenRate - size.width * 1.0f / size.height);
//                Log.e("yubo", "preview size: width = " + size.width + ", height = " + size.height + ", rate = " + supportPreviewRate);
                if(supportPreviewRate < delta) {
                    delta = supportPreviewRate;
                    bestPreviewSize = size;
                }
            }
        }
//        Log.e("yubo", String.format("get best preview size, width = %d, height = %d", bestPreviewSize.width, bestPreviewSize.height));
        return bestPreviewSize;
    }

    private static Comparator<Camera.Size> comparator = new Comparator<Camera.Size>() {
        @Override
        public int compare(Camera.Size size, Camera.Size t1) {
            int a = size.width * size.height;
            int b = t1.width * t1.height;
            return a > b ? -1 : (a == b ? 0 : 1);
        }
    };

    /** 正则判断手机号是否合法 */
    public static boolean isPhoneNumberValid(String phoneStr) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(phoneStr);
        return matcher.matches();
    }

}
