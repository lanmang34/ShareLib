package com.lanmang.sharelib.entry;

import android.content.Context;

import com.lanmang.sharelib.util.PlatformUtil;
import com.lanmang.sharelib.util.ShareConfigUtil;

import cn.sharesdk.framework.Platform;

/**
 * Created by lanmang on 2017/1/20.
 */

public class QZoneShare extends BaseShare {

    private static QZoneShare INSTANCE;

    public static QZoneShare getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new QZoneShare();
        }
        return INSTANCE;
    }

    @Override
    public String getPlatformName() {
        return PlatformUtil.PLATFORM_QZONE;
    }

    @Override
    public String getPackageName() {
        return PlatformUtil.PACKAGE_QQ;
    }

    @Override
    public boolean isValid(Context context) {
        return baseCheck() && getShareBean().getQqZone() != null && isAppInstalled(context);
    }

    @Override
    public boolean isShow(boolean isAppInstalled) {
        return baseCheck() && getShareBean().getQqZone() != null && ShareConfigUtil.isShowQZoneShare && isAppInstalled;
    }

    @Override
    public void setShareParams(Platform.ShareParams sp) {
        try {
            ShareBean.QQZone qqZone = getShareBean().getQqZone();
            sp.setTitle(qqZone.title);
            sp.setTitleUrl(qqZone.targetUrl);
            sp.setText(qqZone.summary);
            sp.setImageUrl(qqZone.imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getClasspath() {
        return PlatformUtil.CLASSPATH_QZONE;
    }
}
