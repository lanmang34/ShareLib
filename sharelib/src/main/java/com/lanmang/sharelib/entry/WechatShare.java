package com.lanmang.sharelib.entry;

import android.content.Context;

import com.lanmang.sharelib.util.PlatformUtil;
import com.lanmang.sharelib.util.ShareConfigUtil;

import cn.sharesdk.framework.Platform;

/**
 * Created by lanmang on 2017/1/20.
 */

public class WechatShare extends BaseShare {

    private static WechatShare INSTANCE;

    public static WechatShare getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WechatShare();
        }
        return INSTANCE;
    }

    @Override
    public String getPlatformName() {
        return PlatformUtil.PLATFORM_WECHAT;
    }

    @Override
    public String getPackageName() {
        return PlatformUtil.PACKAGE_WECHAT;
    }

    @Override
    public boolean isValid(Context context) {
        return baseCheck() && getShareBean().getAppMessage() != null && isAppInstalled(context);
    }

    @Override
    public boolean isShow(boolean isAppInstalled) {
        return baseCheck() && getShareBean().getAppMessage() != null && ShareConfigUtil.isShowWechatShare && isAppInstalled;
    }

    @Override
    public void setShareParams(Platform.ShareParams sp) {
        try {
            ShareBean.AppMessageBean appMessage = getShareBean().getAppMessage();
            sp.setUrl(appMessage.getLink());
            sp.setImageUrl(appMessage.getImgUrl());
            sp.setTitle(appMessage.getTitle());
            sp.setText(appMessage.getDesc());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getClasspath() {
        return PlatformUtil.CLASSPATH_WECHAT;
    }
}
