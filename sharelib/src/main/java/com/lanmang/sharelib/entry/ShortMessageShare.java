package com.lanmang.sharelib.entry;

import android.content.Context;
import android.text.TextUtils;

import com.lanmang.sharelib.util.PlatformUtil;
import com.lanmang.sharelib.util.ShareConfigUtil;

import cn.sharesdk.framework.Platform;

/**
 * Created by lanmang on 2017/1/20.
 */

public class ShortMessageShare extends BaseShare {

    private static ShortMessageShare INSTANCE;

    public static ShortMessageShare getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ShortMessageShare();
        }
        return INSTANCE;
    }

    @Override
    public String getPlatformName() {
        return PlatformUtil.PLATFORM_SHORT_MESSAGE;
    }

    @Override
    public String getPackageName() {
        return null;
    }

    @Override
    public boolean isValid(Context context) {
        return baseCheck() && !TextUtils.isEmpty(getShareBean().getSms());
    }

    @Override
    public boolean isShow(boolean isAppInstalled) {
        return baseCheck() && !TextUtils.isEmpty(getShareBean().getSms()) && ShareConfigUtil.isShowShortMessageShare && isAppInstalled;
    }

    @Override
    public void setShareParams(Platform.ShareParams sp) {
        try {
            sp.setText(getShareBean().getSms());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected String getClasspath() {
        return PlatformUtil.CLASSPATH_SHORT_MESSAGE;
    }
}
