package com.lanmang.sharelib.entry;

import android.content.Context;

import com.lanmang.sharelib.util.PlatformUtil;
import com.lanmang.sharelib.util.ShareConfigUtil;

import cn.sharesdk.framework.Platform;

/**
 * Created by lanmang on 2017/1/20.
 */

public class WechatMomentsShare extends BaseShare {

    private static WechatMomentsShare INSTANCE;

    public static WechatMomentsShare getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WechatMomentsShare();
        }
        return INSTANCE;
    }

    @Override
    public String getPlatformName() {
        return PlatformUtil.PLATFORM_WECHAT_MOMENTS;
    }

    @Override
    public String getPackageName() {
        return PlatformUtil.PACKAGE_WECHAT;
    }

    @Override
    public boolean isValid(Context context) {
        return baseCheck() && getShareBean().getTimeline() != null && isAppInstalled(context);
    }

    @Override
    public boolean isShow(boolean isAppInstalled) {
        return baseCheck() && getShareBean().getTimeline() != null && ShareConfigUtil.isShowWechatMomentsShare && isAppInstalled;
    }

    @Override
    public void setShareParams(Platform.ShareParams sp) {
        try {
            ShareBean.TimelineBean timeline = getShareBean().getTimeline();
            sp.setTitle(timeline.getTitle());
            sp.setText(timeline.getTitle());
            sp.setUrl(timeline.getLink());
            sp.setImageUrl(timeline.getImgUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getClasspath() {
        return PlatformUtil.CLASSPATH_WECAHT_MOMENTS;
    }
}
