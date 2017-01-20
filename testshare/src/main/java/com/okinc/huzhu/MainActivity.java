package com.okinc.huzhu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lanmang.sharelib.entry.BaseShare;
import com.lanmang.sharelib.entry.ShareBean;
import com.lanmang.sharelib.login.ThirdLoginUtil;
import com.lanmang.sharelib.share.ShareUtil;
import com.lanmang.sharelib.util.PlatformUtil;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.Platform;

public class MainActivity extends AppCompatActivity {

    private ShareBean shareBean;
    private ThirdLoginUtil.OnThirdLoginListener mOnThirdLoginListener = new ThirdLoginUtil.OnThirdLoginListener() {
        @Override
        public void success(final Platform platform, final String token, final String openId) {
            //回调在分线程
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, platform.getName() + "授权成功, 信息: token = " + token +
                            ", openId = " + openId, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void cancel(Platform platform) {
            Toast.makeText(MainActivity.this, platform.getName() + "授权被取消", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void failure(String platformName, int errCode, Throwable throwable) {
            Toast.makeText(MainActivity.this, platformName + "授权失败, errCode = " + errCode + ", detailMessage : " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ShareConfigUtil.isShowWechatMomentsShare = false;//设置不显示微信朋友圈分享

        String shareBeanJson = "{\"appMessage\":{\"desc\":\"如遇大病或意外，可为您提供最高30万元救助金\"," +
                "\"imgUrl\":\"http://t.bafanghuzhu.com/v_20160830027/image/share/share_default.jpg\"," +
                "\"link\":\"http://t.bafanghuzhu.com/page/index_v2.do\"," +
                "\"title\":\"我已加入八方互助，送您一份最高30万的大病互助\"}," +
                "\"qqFriend\":{\"imageUrl\":\"http://t.bafanghuzhu.com/v_20160830027/image/share/share_default.jpg\"," +
                "\"summary\":\"如遇大病或意外，可为您提供最高30万元救助金\"," +
                "\"targetUrl\":\"http://t.bafanghuzhu.com/page/index_v2.do\"," +
                "\"title\":\"我已加入八方互助，送您一份最高30万的大病互助\"}," +
                "\"qqZone\":{\"imageUrl\":\"http://t.bafanghuzhu.com/v_20160830027/image/share/share_default.jpg\"," +
                "\"summary\":\"如遇大病或意外，可为您提供最高30万元救助金\"," +
                "\"targetUrl\":\"http://t.bafanghuzhu.com/page/index_v2.do\"," +
                "\"title\":\"我已加入八方互助，送您一份最高30万的大病互助\"}," +
                "\"sms\":\"我已加入八方互助，送您一份最高30万的大病互助，如遇大病或意外，可为您提供最高30万元救助金，点击查收：https://www.bafang.com\"," +
                "\"timeline\":{\"desc\":\"如遇大病或意外，可为您提供最高30万元救助金\"," +
                "\"imgUrl\":\"http://t.bafanghuzhu.com/v_20160830027/image/share/share_default.jpg\"," +
                "\"link\":\"http://t.bafanghuzhu.com/page/index_v2.do\"," +
                "\"title\":\"我已加入八方互助，送您一份最高30万的大病互助\"}," +
                "\"weibo\":{\"shareDesc\":\"我已加入八方互助，送您一份最高30万的大病互助 如遇大病或意外，可为您提供最高30万元救助金\"," +
                "\"shareTitle\":\"我已加入八方互助，送您一份最高30万的大病互助\"," +
                "\"shareUrl\":\"http://t.bafanghuzhu.com/page/index_v2.do\"," +
                "\"thumb\":\"http://t.bafanghuzhu.com/v_20160830027/image/share/share_default.jpg\"}}";

        shareBean = JSON.parseObject(shareBeanJson, ShareBean.class);

    }

    public void directShareToWechat(View v) {
        ShareUtil.getInstance().directShare(this, PlatformUtil.PLATFORM_WECHAT, shareBean);
    }
    public void directShareToWechatMoments(View v) {
        ShareUtil.getInstance().directShare(this, PlatformUtil.PLATFORM_WECHAT_MOMENTS, shareBean);
    }
    public void directShareToQQ(View v) {
//        ShareUtil.getInstance().directShare(this, PlatformUtil.PLATFORM_QQ, shareBean);
        QQShare qqShare = QQShare.getInstance();
        qqShare.setShareBean(shareBean);
        ShareUtil.getInstance().directShare(this, qqShare);
    }
    public void directShareToQZone(View v) {
        ShareUtil.getInstance().directShare(this, Platform.SHARE_WEBPAGE, PlatformUtil.PLATFORM_QZONE, shareBean);
    }
    public void directShareToSinaWeibo(View v) {
        ShareUtil.getInstance().directShare(this, PlatformUtil.PLATFORM_SINA_WEIBO, shareBean);
    }
    public void directShareToShortMessage(View v) {
        ShareUtil.getInstance().directShare(this, Platform.SHARE_TEXT, PlatformUtil.PLATFORM_SHORT_MESSAGE, shareBean);
    }

    public void shareWithBtn(View v) {
        //ShareUtil.getInstance().share(this, shareBean);
        QQShare qqShare = QQShare.getInstance();
        List<BaseShare> shares = new ArrayList<>();
        shares.add(qqShare);
        ShareUtil.getInstance().share(this, shares, shareBean);
    }

    public void loginByWechat(View v) {
        ThirdLoginUtil.getInstance().thirdLogin(this, PlatformUtil.PLATFORM_WECHAT, mOnThirdLoginListener);
    }

    public void loginByQQ(View v) {
        ThirdLoginUtil.getInstance().thirdLogin(this, PlatformUtil.PLATFORM_QQ, mOnThirdLoginListener);
    }

    public void loginByWeibo(View v) {
        ThirdLoginUtil.getInstance().thirdLogin(this, PlatformUtil.PLATFORM_SINA_WEIBO, mOnThirdLoginListener);
    }

}
