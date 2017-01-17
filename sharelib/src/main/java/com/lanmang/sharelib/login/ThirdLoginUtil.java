package com.lanmang.sharelib.login;

import android.content.Context;

import com.lanmang.sharelib.util.CommonUtil;
import com.lanmang.sharelib.util.PlatformUtil;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by lanmang on 2017/1/17.
 * 第三方登录工具类
 */

public class ThirdLoginUtil {

    private static ThirdLoginUtil INSTANCE;
    private boolean isThirdLoging;//是否正在第三方登录

    public static ThirdLoginUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ThirdLoginUtil();
        }
        return INSTANCE;
    }

    private OnThirdLoginListener mOnThirdLoginListener;

    private PlatformActionListener mPlatformActionListener = new PlatformActionListener() {//第三方登录回调监听
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            //分线程
            if (mOnThirdLoginListener != null) {
                PlatformDb db = platform.getDb();
                mOnThirdLoginListener.success(platform, db.getToken(), db.getUserId());
            }
            isThirdLoging = false;
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            showErr(platform, i, throwable);
        }

        @Override
        public void onCancel(Platform platform, int i) {
            if (mOnThirdLoginListener != null) {
                mOnThirdLoginListener.cancel(platform);
            }
            isThirdLoging = false;
        }
    };

    /**
     * 获取第三访登陆受权信息
     * @param context
     * @param platformName
     * @param onThirdLoginListener
     */
    public void thirdLogin(Context context, String platformName, OnThirdLoginListener onThirdLoginListener) {
        if (isThirdLoging) {
            return;
        }
        isThirdLoging = true;
        mOnThirdLoginListener = onThirdLoginListener;
        boolean appIsInstalled = checkAppIsInstalled(context, platformName);
        if (!appIsInstalled) {
            showErr(null, PlatformUtil.ERR_APP_UNINSTALLED, null);
            return;
        }

        try {
            ShareSDK.initSDK(context);
            Platform platform = ShareSDK.getPlatform(platformName);
            platform.removeAccount(true);
            platform.setPlatformActionListener(mPlatformActionListener);
            platform.SSOSetting(false);//调用本地应用
            platform.showUser(null);//获取用户信息
            ShareSDK.stopSDK(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkAppIsInstalled(Context context, String platformName) {
        switch (platformName) {
            case PlatformUtil.PLATFORM_WECHAT :
                if (CommonUtil.isInstalled(context, PlatformUtil.PACKAGE_WECHAT)) {
                    return true;
                }
                break;
            case PlatformUtil.PLATFORM_QQ :
                if (CommonUtil.isInstalled(context, PlatformUtil.PACKAGE_QQ)) {
                    return true;
                }
                break;
            case PlatformUtil.PLATFORM_SINA_WEIBO :
                if (CommonUtil.isInstalled(context, PlatformUtil.PACKAGE_SINA)) {
                    return true;
                }
                break;
        }

        return false;
    }

    private void showErr(Platform platform, int errCode, Throwable throwable) {
        if (mOnThirdLoginListener != null) {
            mOnThirdLoginListener.failure(platform, errCode, throwable);
        }
        isThirdLoging = false;
    }

    public interface OnThirdLoginListener {
        void success(Platform platform, String token, String openId);
        void cancel(Platform platform);
        void failure(Platform platform, int errCode, Throwable throwable);
    }
}
