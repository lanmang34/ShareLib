package com.lanmang.sharelib.util;

/**
 * Created by lanmang on 2017/1/16.
 * 分享常量类
 */

public class PlatformUtil {
    //Platform类名
    public static final String PLATFORM_WECHAT = "Wechat";
    public static final String PLATFORM_WECHAT_MOMENTS = "WechatMoments";
    public static final String PLATFORM_QQ = "QQ";
    public static final String PLATFORM_QZONE = "QZone";
    public static final String PLATFORM_SINA_WEIBO = "SinaWeibo";
    public static final String PLATFORM_SHHORT_MESSAGE = "ShortMessage";

    //第三方登录应用的包名
    public static final String PACKAGE_WECHAT = "com.tencent.mm";//微信
    public static final String PACKAGE_QQ = "com.tencent.mobileqq";//QQ
    public static final String PACKAGE_SINA = "com.sina.weibo";//新浪微博

    //第三方登录回调错误码
    public static final int ERR_APP_UNINSTALLED = 101;//应用未安装
}
