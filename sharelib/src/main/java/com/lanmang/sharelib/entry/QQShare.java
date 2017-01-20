package com.lanmang.sharelib.entry;

import android.content.Context;

import com.lanmang.sharelib.util.PlatformUtil;
import com.lanmang.sharelib.util.ShareConfigUtil;

import cn.sharesdk.framework.Platform;

/**
 * Created by lanmang on 2017/1/20.
 */

public class QQShare extends BaseShare {

    private static QQShare INSTANCE;

    public static QQShare getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new QQShare();
        }
        return INSTANCE;
    }

    @Override
    public String getPlatformName() {
        return PlatformUtil.PLATFORM_QQ;
    }

    @Override
    public String getPackageName() {
        return PlatformUtil.PACKAGE_QQ;
    }

    @Override
    public boolean isValid(Context context) {
        return baseCheck() && getShareBean().getQqFriend() != null && isAppInstalled(context);
    }

    @Override
    public boolean isShow(boolean isAppInstalled) {
        return baseCheck() && getShareBean().getQqFriend() != null && ShareConfigUtil.isShowQQShare && isAppInstalled;
    }

    @Override
    public void setShareParams(Platform.ShareParams sp) {
        try {
            ShareBean.QQFriend qqFriend = getShareBean().getQqFriend();
            sp.setTitle(qqFriend.title);
            sp.setTitleUrl(qqFriend.targetUrl);
            sp.setImageUrl(qqFriend.imageUrl);
            sp.setText(qqFriend.summary);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected String getClasspath() {
        return PlatformUtil.CLASSPATH_QQ;
    }
}
