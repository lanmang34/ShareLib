package com.lanmang.sharelib.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.Platform;

/**
 * Created by lanmang on 2017/1/17.
 */

public class LibCommonUtil {

    public static Map<String, Class<Platform.ShareParams>> mClassMaps;

    /**
     * 判断单个应用是否安装
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 批量判断应用是否安装
     * @param context
     * @param map
     */
    public static void isInstalled(Context context, Map<String, Boolean> map) {
        try {
            List<PackageInfo> infos = context.getPackageManager().getInstalledPackages(0);
            for (PackageInfo info : infos) {
                String packageName = info.packageName;
                Boolean aBoolean = map.get(packageName);
                if (aBoolean != null) {
                    map.put(packageName, true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过反射获取分享类
     * @param platformName
     * @return
     */
    public static Class<Platform.ShareParams> getShareClass(String platformName) {
        if (mClassMaps == null) {
            mClassMaps = new HashMap<>();
        }
        Class<Platform.ShareParams> shareParamsClass = mClassMaps.get(platformName);
        if (shareParamsClass != null) {
            return shareParamsClass;
        }

        String classPath = null;
        switch (platformName) {
            case PlatformUtil.PLATFORM_QQ :
                classPath = "cn.sharesdk.tencent.qq.QQ$ShareParams";
                break;
            case PlatformUtil.PLATFORM_QZONE :
                classPath = "cn.sharesdk.tencent.qzone.QZone$ShareParams";
                break;
            case PlatformUtil.PLATFORM_WECHAT :
                classPath = "cn.sharesdk.wechat.friends.Wechat$ShareParams";
                break;
            case PlatformUtil.PLATFORM_WECHAT_MOMENTS :
                classPath = "cn.sharesdk.wechat.moments.WechatMoments$ShareParams";
                break;
            case PlatformUtil.PLATFORM_SINA_WEIBO :
                classPath = "cn.sharesdk.sina.weibo.SinaWeibo$ShareParams";
                break;
            case PlatformUtil.PLATFORM_SHORT_MESSAGE:
                classPath = "cn.sharesdk.system.text.ShortMessage$ShareParams";
                break;
        }

        try {
            shareParamsClass = (Class<Platform.ShareParams>) Class.forName(classPath);
            mClassMaps.put(platformName, shareParamsClass);
            return shareParamsClass;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据错误码生成throwable
     * @param errCode
     * @return
     */
    public static Throwable getThrowable(int errCode) {
        Throwable throwable;
        switch (errCode) {
            case PlatformUtil.ERR_APP_UNINSTALLED :
                throwable = new Throwable("应用程序未安装");
                break;
            case PlatformUtil.ERR_APP_WITHOUT_JAR :
                throwable = new Throwable("未导入Jar包");
                break;
            default :
                throwable = new Throwable("未知错误");
                break;
        }

        return throwable;
    }
}
