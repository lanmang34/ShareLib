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
    public static final String PLATFORM_SHORT_MESSAGE = "ShortMessage";

    //第三方登录应用的包名
    public static final String PACKAGE_WECHAT = "com.tencent.mm";//微信
    public static final String PACKAGE_QQ = "com.tencent.mobileqq";//QQ
    public static final String PACKAGE_SINA = "com.sina.weibo";//新浪微博

    //第三方登录回调错误码
    public static final int ERR_APP_UNINSTALLED = 101;//应用未安装
    public static final int ERR_APP_WITHOUT_JAR = 102;//未导入jar包

    //分享参数类的classpath
    public static final String CLASSPATH_QQ = "cn.sharesdk.tencent.qq.QQ$ShareParams";//QQ的分享classpath
    public static final String CLASSPATH_QZONE = "cn.sharesdk.tencent.qzone.QZone$ShareParams";
    public static final String CLASSPATH_WECHAT = "cn.sharesdk.wechat.friends.Wechat$ShareParams";
    public static final String CLASSPATH_WECAHT_MOMENTS = "cn.sharesdk.wechat.moments.WechatMoments$ShareParams";
    public static final String CLASSPATH_SINA_WEIBO = "cn.sharesdk.sina.weibo.SinaWeibo$ShareParams";
    public static final String CLASSPATH_SHORT_MESSAGE = "cn.sharesdk.system.text.ShortMessage$ShareParams";
}
