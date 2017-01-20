package com.lanmang.sharelib.entry;

import android.content.Context;

import com.lanmang.sharelib.util.LibCommonUtil;

import cn.sharesdk.framework.Platform;

/**
 * Created by lanmang on 2017/1/20.
 */

public abstract class BaseShare {

    private int shareType = Platform.SHARE_WEBPAGE;
    private ShareBean shareBean;
    private Class<Platform.ShareParams> shareParamsClass;
    private Platform.ShareParams mSp;

    public int getShareType() {
        return shareType;
    }

    public void setShareType(int shareType) {
        this.shareType = shareType;
    }

    public ShareBean getShareBean() {
        return shareBean;
    }

    public void setShareBean(ShareBean shareBean) {
        this.shareBean = shareBean;
    }

    /**
     * 只能用于单个App判断是否安装, 判断复数App是否安装会出错, 导致Binder超出1M
     * @param context
     * @return
     */
    public boolean isAppInstalled(Context context) {
        return LibCommonUtil.isInstalled(context, getPackageName());
    }

    /**
     * 通过反射获取ShareParams对象
     * @return
     */
    public Platform.ShareParams getShareParams() {
        if (mSp == null) {
            try {
                mSp = getShareClass().newInstance();
                //设置shareType, 微信会检测链接, 链接错误会导致无法分享
                mSp.setShareType(shareType);
                setShareParams(mSp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return mSp;
    }

    /**
     * 通过反射获取分享类
     * @return
     */
    private Class<Platform.ShareParams> getShareClass() {
        if (shareParamsClass == null) {
            try {
                shareParamsClass = (Class<Platform.ShareParams>) Class.forName(getClasspath());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return shareParamsClass;
    }

    protected boolean baseCheck() {
        return getShareBean() != null && getShareParams() != null;
    }

    public abstract String getPlatformName();
    public abstract String getPackageName();
    public abstract boolean isValid(Context context);
    public abstract boolean isShow(boolean isAppInstalled);
    public abstract void setShareParams(Platform.ShareParams shareParams);
    protected abstract String getClasspath();
}
