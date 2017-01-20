package com.lanmang.sharelib.entry;

import android.content.Context;

import com.lanmang.sharelib.util.PlatformUtil;
import com.lanmang.sharelib.util.ShareConfigUtil;

import cn.sharesdk.framework.Platform;

/**
 * Created by lanmang on 2017/1/20.
 */

public class SinaWeiboShare extends BaseShare {

    private static SinaWeiboShare INSTANCE;

    public static SinaWeiboShare getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SinaWeiboShare();
        }
        return INSTANCE;
    }

    @Override
    public String getPlatformName() {
        return PlatformUtil.PLATFORM_SINA_WEIBO;
    }

    @Override
    public String getPackageName() {
        return PlatformUtil.PACKAGE_SINA;
    }

    @Override
    public boolean isValid(Context context) {
        return baseCheck() && getShareBean().getWeibo() != null && isAppInstalled(context);
    }

    @Override
    public boolean isShow(boolean isAppInstalled) {
        return baseCheck() && getShareBean().getWeibo() != null && ShareConfigUtil.isShowSinaWeiboShare && isAppInstalled;
    }

    @Override
    public void setShareParams(Platform.ShareParams sp) {
        try {
            ShareBean.WeiBo weibo = getShareBean().getWeibo();
            sp.setText(weibo.shareDesc + " " + weibo.shareUrl);
            sp.setTitle(weibo.shareTitle);
            sp.setImageUrl(weibo.thumb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected String getClasspath() {
        return PlatformUtil.CLASSPATH_SINA_WEIBO;
    }
}
