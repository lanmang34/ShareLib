package com.lanmang.sharelib.share;

import android.content.Context;

import com.lanmang.sharelib.entry.BaseShare;
import com.lanmang.sharelib.entry.QQShare;
import com.lanmang.sharelib.entry.QZoneShare;
import com.lanmang.sharelib.entry.ShareBean;
import com.lanmang.sharelib.entry.ShortMessageShare;
import com.lanmang.sharelib.entry.SinaWeiboShare;
import com.lanmang.sharelib.entry.WechatMomentsShare;
import com.lanmang.sharelib.entry.WechatShare;
import com.lanmang.sharelib.util.LibCommonUtil;
import com.lanmang.sharelib.util.PlatformUtil;

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

    private Map<String, Boolean> mAppMaps;
    private Map<String, BaseShare> mBaseShares;
    private static ShareUtil INSTANCE;

    public static ShareUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ShareUtil();
        }
        return INSTANCE;
    }

    private ShareUtil() {
        mBaseShares = new HashMap<>();
        mBaseShares.put(PlatformUtil.PLATFORM_QQ, QQShare.getInstance());
        mBaseShares.put(PlatformUtil.PLATFORM_QZONE, QZoneShare.getInstance());
        mBaseShares.put(PlatformUtil.PLATFORM_WECHAT, WechatShare.getInstance());
        mBaseShares.put(PlatformUtil.PLATFORM_WECHAT_MOMENTS, WechatMomentsShare.getInstance());
        mBaseShares.put(PlatformUtil.PLATFORM_SINA_WEIBO, SinaWeiboShare.getInstance());
        mBaseShares.put(PlatformUtil.PLATFORM_SHORT_MESSAGE, ShortMessageShare.getInstance());
    }

    public boolean hasShareParams(String platformName) {
        return mBaseShares.get(platformName) == null;
    }

    /**
     * 直接分享
     */
    public void directShare(Context context, String platformName, ShareBean shareBean) {
        directShare(context, Platform.SHARE_WEBPAGE, platformName, shareBean);
    }

    public void directShare(Context context, int shareType, String platformName, ShareBean shareBean) {
        BaseShare baseShare = mBaseShares.get(platformName);
        baseShare.setShareType(shareType);
        baseShare.setShareBean(shareBean);
        directShare(context, baseShare);
    }

    public void directShare(Context context, BaseShare baseShare) {

        if (!baseShare.isValid(context)) {
            return;
        }

        try {
            ShareSDK.closeDebug();
            ShareSDK.initSDK(context);
            Platform platform = ShareSDK.getPlatform(baseShare.getPlatformName());
            platform.share(baseShare.getShareParams());
            platform.SSOSetting(true);
            ShareSDK.stopSDK();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 调起分享按钮分享
     */
    public void share(Context context, ShareBean shareBean) {
        share(context, Platform.SHARE_WEBPAGE, shareBean);
    }

    public void share(Context context, BaseShare baseShare) {
        mBaseShares.put(baseShare.getPlatformName(), baseShare);
        share(context, baseShare.getShareType(), baseShare.getShareBean());
    }

    public void share(Context context, List<BaseShare> baseShares, ShareBean shareBean) {
        share(context, baseShares, Platform.SHARE_WEBPAGE, shareBean);
    }

    public void share(Context context, List<BaseShare> baseShares, int shareType, ShareBean shareBean) {
        for (BaseShare baseShare : baseShares) {
            mBaseShares.put(baseShare.getPlatformName(), baseShare);
        }
        share(context, shareType, shareBean);
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

            prepareShare(context, shareType, oks, shareBean);

            // 启动分享GUI
            oks.show(context);
            ShareSDK.stopSDK();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////

    private void prepareShare(Context context, final int shareType, OnekeyShare oks, ShareBean shareBean) {
        checkAppIsInstalled(context);

        for (BaseShare baseShare : mBaseShares.values()) {
            baseShare.setShareBean(shareBean);
        }

        hideTypes(oks);

        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams sp) {
                BaseShare baseShare = mBaseShares.get(platform.getName());
                //设置shareType, 微信会检测链接, 链接错误会导致无法分享
                baseShare.setShareType(shareType);
                sp.setShareType(shareType);
                baseShare.setShareParams(sp);
            }
        });
    }

    private void checkAppIsInstalled(Context context) {

        if (mAppMaps == null) {
            mAppMaps = new HashMap<>();
        }
        for (BaseShare baseShare : mBaseShares.values()) {
            mAppMaps.put(baseShare.getPackageName(), false);
        }

        LibCommonUtil.isInstalled(context, mAppMaps);

    }

    /**
     * 隐藏没有数据或者未安装的app
     * @param oks
     */
    private void hideTypes(OnekeyShare oks) {

        List<String> showTypes = new ArrayList<>();
        List<String> hideTypes = new ArrayList<>();
        for (BaseShare baseShare : mBaseShares.values()) {
            if (baseShare.isShow(mAppMaps.get(baseShare.getPackageName()))) {
                showTypes.add(baseShare.getPlatformName());
            }else{
                hideTypes.add(baseShare.getPlatformName());
            }
        }

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

    }

}
