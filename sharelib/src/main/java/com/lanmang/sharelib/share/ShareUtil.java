package com.lanmang.sharelib.share;

import android.content.Context;

import com.lanmang.sharelib.entry.ShareBean;
import com.lanmang.sharelib.util.CommonUtil;
import com.lanmang.sharelib.util.PlatformUtil;

import java.util.ArrayList;
import java.util.List;

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

    private static boolean wechatValid;
    private static boolean qqValid;
    private static boolean sinaValid;

    /**
     * 调起分享按钮分享
     */
    public static void share(Context context, ShareBean shareBean) {
        share(context, Platform.SHARE_WEBPAGE, shareBean);
    }

    public static void share(Context context, int shareType, ShareBean shareBean) {
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
    public static <T extends Platform.ShareParams> void directShare(Context context, T sp, String platformName, ShareBean shareBean) {
        directShare(context, sp, Platform.SHARE_WEBPAGE, platformName, shareBean);
    }

    public static <T extends Platform.ShareParams> void directShare(Context context, T sp, int shareType, String platformName, ShareBean shareBean) {
        if (shareBean == null) {
            return;
        }

        checkAppIsInstalled(context);

        if (PlatformUtil.PLATFORM_WECHAT_MOMENTS.equals(platformName) && (shareBean.getTimeline() == null || !wechatValid)) {
            return;
        }else if (PlatformUtil.PLATFORM_WECHAT.equals(platformName) && (shareBean.getAppMessage() == null || !wechatValid)) {
            return;
        }else if (PlatformUtil.PLATFORM_SHHORT_MESSAGE.equals(platformName) && shareBean.getSms() == null) {
            return;
        }else if (PlatformUtil.PLATFORM_QQ.equals(platformName) && (shareBean.getQqFriend() == null || !qqValid)) {
            return;
        }else if (PlatformUtil.PLATFORM_QZONE.equals(platformName) && (shareBean.getQqZone() == null || !qqValid)) {
            return;
        }else if (PlatformUtil.PLATFORM_SINA_WEIBO.equals(platformName) && (shareBean.getWeibo() == null || !sinaValid)) {
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

    private static void prepareShare(Context context, final int shareType, final ShareBean shareBean, OnekeyShare oks) {
        if (shareBean == null){
            return;
        }
        checkAppIsInstalled(context);

        List<String> showTypes = new ArrayList<>();
        List<String> hideTypes = new ArrayList<>();
        if (shareBean.getTimeline() != null && wechatValid) {
            showTypes.add(PlatformUtil.PLATFORM_WECHAT_MOMENTS);
        }else{
            hideTypes.add(PlatformUtil.PLATFORM_WECHAT_MOMENTS);
        }
        if (shareBean.getAppMessage() != null && wechatValid) {
            showTypes.add(PlatformUtil.PLATFORM_WECHAT);
        }else{
            hideTypes.add(PlatformUtil.PLATFORM_WECHAT);
        }
        if (shareBean.getSms() != null) {
            showTypes.add(PlatformUtil.PLATFORM_SHHORT_MESSAGE);
        }else{
            hideTypes.add(PlatformUtil.PLATFORM_SHHORT_MESSAGE);
        }
        if (shareBean.getQqFriend() != null && qqValid) {
            showTypes.add(PlatformUtil.PLATFORM_QQ);
        }else{
            hideTypes.add(PlatformUtil.PLATFORM_QQ);
        }
        if (shareBean.getQqZone() != null && qqValid) {
            showTypes.add(PlatformUtil.PLATFORM_QZONE);
        }else{
            hideTypes.add(PlatformUtil.PLATFORM_QZONE);
        }
        if (shareBean.getWeibo() != null && sinaValid) {
            showTypes.add(PlatformUtil.PLATFORM_SINA_WEIBO);
        }else{
            hideTypes.add(PlatformUtil.PLATFORM_SINA_WEIBO);
        }

        ShareUtil.shareCustomizeContent(showTypes, hideTypes, oks, new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams sp) {
                setShareParams(platform, sp, shareType, shareBean);
            }
        });
    }

    private static void checkAppIsInstalled(Context context) {
        wechatValid = CommonUtil.isInstalled(context, PlatformUtil.PACKAGE_WECHAT);
        qqValid = CommonUtil.isInstalled(context, PlatformUtil.PACKAGE_QQ);
        sinaValid = CommonUtil.isInstalled(context, PlatformUtil.PACKAGE_SINA);
    }

    private static void setShareParams(Platform platform, Platform.ShareParams sp, int shareType, ShareBean shareBean) {
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
        } else if (PlatformUtil.PLATFORM_SHHORT_MESSAGE.equals(platform.getName()) && shareBean.getTimeline() != null) {
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

    private static void shareCustomizeContent(List<String> showTypes, List<String> hideTypes, OnekeyShare oks, ShareContentCustomizeCallback customizeCallback) {
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
