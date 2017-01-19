package com.lanmang.sharelib.share;

import android.content.Context;

import com.lanmang.sharelib.entry.ShareBean;
import com.lanmang.sharelib.util.LibCommonUtil;
import com.lanmang.sharelib.util.PlatformUtil;
import com.lanmang.sharelib.util.ShareConfigUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.onekeyshare.themes.classic.port.PlatformPageAdapterPort;

/**
 * Created by lanmang on 2017/1/16.
 * 分享工具类
 */

public class ShareUtil {

    private boolean mWechatValid;
    private boolean mQqValid;
    private boolean mSinaValid;
    private Map<String, Boolean> mAppMaps;

    private static ShareUtil INSTANCE;

    public static ShareUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ShareUtil();
        }
        return INSTANCE;
    }

    /**
     * 调起分享按钮分享
     */
    public void share(Context context, ShareBean shareBean) {
        share(context, Platform.SHARE_WEBPAGE, shareBean);
    }

    public void share(Context context, int shareType, ShareBean shareBean) {
        if (shareBean == null){
            return;
        }
        try {
            ShareSDK.closeDebug();
            ShareSDK.initSDK(context);
            OnekeyShare oks = new OnekeyShare();
            //关闭sso授权
            oks.disableSSOWhenAuthorize();
            oks.setSilent(true);

            prepareShare(context, shareType, shareBean, oks);

            // 启动分享GUI
            oks.show(context);
            ShareSDK.stopSDK();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接分享
     */
    public void directShare(Context context, String platformName, ShareBean shareBean) {
        directShare(context, Platform.SHARE_WEBPAGE, platformName, shareBean);
    }

    public void directShare(Context context, int shareType, String platformName, ShareBean shareBean) {

        Platform.ShareParams sp = getShareParams(platformName);

        if (shareBean == null || sp == null) {
            return;
        }

        checkAppIsInstalled(context);

        if (PlatformUtil.PLATFORM_WECHAT_MOMENTS.equals(platformName) && (shareBean.getTimeline() == null || !mWechatValid)) {
            return;
        }else if (PlatformUtil.PLATFORM_WECHAT.equals(platformName) && (shareBean.getAppMessage() == null || !mWechatValid)) {
            return;
        }else if (PlatformUtil.PLATFORM_SHORT_MESSAGE.equals(platformName) && shareBean.getSms() == null) {
            return;
        }else if (PlatformUtil.PLATFORM_QQ.equals(platformName) && (shareBean.getQqFriend() == null || !mQqValid)) {
            return;
        }else if (PlatformUtil.PLATFORM_QZONE.equals(platformName) && (shareBean.getQqZone() == null || !mQqValid)) {
            return;
        }else if (PlatformUtil.PLATFORM_SINA_WEIBO.equals(platformName) && (shareBean.getWeibo() == null || !mSinaValid)) {
            return;
        }

        try {
            ShareSDK.closeDebug();
            ShareSDK.initSDK(context);
            Platform platform = ShareSDK.getPlatform(platformName);

            setShareParams(platform, sp, shareType, shareBean);

            platform.SSOSetting(true);
            platform.share(sp);
            ShareSDK.stopSDK();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    ////////////////////////////////////////////////////////////////////////////////

    /**
     * 通过反射获取ShareParams对象
     * @param platformName
     * @return
     */
    private Platform.ShareParams getShareParams(String platformName) {
        try {
            return LibCommonUtil.getShareClass(platformName).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void prepareShare(Context context, final int shareType, final ShareBean shareBean, OnekeyShare oks) {
        checkAppIsInstalled(context);

        List<String> showTypes = new ArrayList<>();
        List<String> hideTypes = new ArrayList<>();
        if (ShareConfigUtil.isShowWechatMomentsShare && shareBean.getTimeline() != null && mWechatValid && LibCommonUtil.getShareClass(PlatformUtil.PLATFORM_WECHAT_MOMENTS) != null) {
            showTypes.add(PlatformUtil.PLATFORM_WECHAT_MOMENTS);
        }else{
            hideTypes.add(PlatformUtil.PLATFORM_WECHAT_MOMENTS);
        }
        if (ShareConfigUtil.isShowWechatShare && shareBean.getAppMessage() != null && mWechatValid && LibCommonUtil.getShareClass(PlatformUtil.PLATFORM_WECHAT) != null) {
            showTypes.add(PlatformUtil.PLATFORM_WECHAT);
        }else{
            hideTypes.add(PlatformUtil.PLATFORM_WECHAT);
        }
        if (ShareConfigUtil.isShowShortMessageShare && shareBean.getSms() != null && LibCommonUtil.getShareClass(PlatformUtil.PLATFORM_SHORT_MESSAGE) != null) {
            showTypes.add(PlatformUtil.PLATFORM_SHORT_MESSAGE);
        }else{
            hideTypes.add(PlatformUtil.PLATFORM_SHORT_MESSAGE);
        }
        if (ShareConfigUtil.isShowQQShare && shareBean.getQqFriend() != null && mQqValid && LibCommonUtil.getShareClass(PlatformUtil.PLATFORM_QQ) != null) {
            showTypes.add(PlatformUtil.PLATFORM_QQ);
        }else{
            hideTypes.add(PlatformUtil.PLATFORM_QQ);
        }
        if (ShareConfigUtil.isShowQZoneShare && shareBean.getQqZone() != null && mQqValid && LibCommonUtil.getShareClass(PlatformUtil.PLATFORM_QZONE) != null) {
            showTypes.add(PlatformUtil.PLATFORM_QZONE);
        }else{
            hideTypes.add(PlatformUtil.PLATFORM_QZONE);
        }
        if (ShareConfigUtil.isShowSinaWeiboShare && shareBean.getWeibo() != null && mSinaValid && LibCommonUtil.getShareClass(PlatformUtil.PLATFORM_SINA_WEIBO) != null) {
            showTypes.add(PlatformUtil.PLATFORM_SINA_WEIBO);
        }else{
            hideTypes.add(PlatformUtil.PLATFORM_SINA_WEIBO);
        }

        shareCustomizeContent(showTypes, hideTypes, oks, new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams sp) {
                setShareParams(platform, sp, shareType, shareBean);
            }
        });
    }

    private void checkAppIsInstalled(Context context) {
        /*
        //以下写法会导致Binder超出1M 抛出异常
        mWechatValid = LibCommonUtil.isInstalled(context, PlatformUtil.PACKAGE_WECHAT);
        mQqValid = LibCommonUtil.isInstalled(context, PlatformUtil.PACKAGE_QQ);
        mSinaValid = LibCommonUtil.isInstalled(context, PlatformUtil.PACKAGE_SINA);*/

        if (mAppMaps == null) {
            mAppMaps = new HashMap<>();
        }
        mAppMaps.put(PlatformUtil.PACKAGE_QQ, false);
        mAppMaps.put(PlatformUtil.PACKAGE_SINA, false);
        mAppMaps.put(PlatformUtil.PACKAGE_WECHAT, false);
        LibCommonUtil.isInstalled(context, mAppMaps);

        mQqValid = mAppMaps.get(PlatformUtil.PACKAGE_QQ);
        mSinaValid = mAppMaps.get((PlatformUtil.PACKAGE_SINA));
        mWechatValid = mAppMaps.get(PlatformUtil.PACKAGE_WECHAT);
    }

    private void setShareParams(Platform platform, Platform.ShareParams sp, int shareType, ShareBean shareBean) {
        sp.setShareType(shareType);
        if (PlatformUtil.PLATFORM_WECHAT.equals(platform.getName()) && shareBean.getAppMessage() != null) {
            sp.setUrl(shareBean.getAppMessage().getLink());
            sp.setImageUrl(shareBean.getAppMessage().getImgUrl());
            sp.setTitle(shareBean.getAppMessage().getTitle());
            sp.setText(shareBean.getAppMessage().getDesc());
        } else if (PlatformUtil.PLATFORM_WECHAT_MOMENTS.equals(platform.getName()) && shareBean.getTimeline() != null) {
            sp.setTitle(shareBean.getTimeline().getTitle());
            sp.setText(shareBean.getTimeline().getTitle());
            sp.setUrl(shareBean.getTimeline().getLink());
            sp.setImageUrl(shareBean.getTimeline().getImgUrl());
        } else if (PlatformUtil.PLATFORM_SHORT_MESSAGE.equals(platform.getName()) && shareBean.getTimeline() != null) {
            sp.setText(shareBean.getSms());
        } else if (PlatformUtil.PLATFORM_QQ.equals(platform.getName()) && shareBean.getQqFriend() != null) {
            ShareBean.QQFriend qqFriend = shareBean.getQqFriend();
            sp.setTitle(qqFriend.title);
            sp.setTitleUrl(qqFriend.targetUrl);
            sp.setImageUrl(qqFriend.imageUrl);
            sp.setText(qqFriend.summary);
        } else if (PlatformUtil.PLATFORM_QZONE.equals(platform.getName()) && shareBean.getQqZone() != null) {
            ShareBean.QQZone qqZone = shareBean.getQqZone();
            sp.setTitle(qqZone.title);
            sp.setTitleUrl(qqZone.targetUrl);
            sp.setText(qqZone.summary);
            sp.setImageUrl(qqZone.imageUrl);
        } else if (PlatformUtil.PLATFORM_SINA_WEIBO.equals(platform.getName()) && shareBean.getWeibo() != null) {
            ShareBean.WeiBo weibo = shareBean.getWeibo();
            sp.setText(weibo.shareDesc + " " + weibo.shareUrl);
            sp.setTitle(weibo.shareTitle);
            sp.setImageUrl(weibo.thumb);
        }
    }

    private void shareCustomizeContent(List<String> showTypes, List<String> hideTypes, OnekeyShare oks, ShareContentCustomizeCallback customizeCallback) {
        //隐藏没有数据或者未安装的app
        for (String platform : hideTypes) {
            oks.addHiddenPlatform(platform);
        }

        //设置一行显示几个
        int size = showTypes.size();
        switch (size) {
            case 1 :
                size = 1;
                break;
            case 2 :
            case 4 :
                size = 2;
                break;
            case 3 :
            case 5 :
            case 6 :
            default:
                size = 3;
        }
        PlatformPageAdapterPort.setLinearSize(size);

        oks.setShareContentCustomizeCallback(customizeCallback);
    }

}
