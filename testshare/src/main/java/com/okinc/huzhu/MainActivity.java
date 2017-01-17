package com.okinc.huzhu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lanmang.sharelib.entry.ShareBean;
import com.lanmang.sharelib.login.ThirdLoginUtil;
import com.lanmang.sharelib.share.ShareUtil;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

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
        public void failure(Platform platform, int errCode, Throwable throwable) {
            Toast.makeText(MainActivity.this, platform.getName() + "授权失败, errCode = " + errCode, Toast.LENGTH_SHORT).show();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void directShare(View v) {
        Platform.ShareParams sp = new Wechat.ShareParams();
        ShareUtil.directShare(this, sp, Wechat.NAME, shareBean);
    }

    public void shareWithBtn(View v) {
        ShareUtil.share(this, shareBean);
    }

    public void loginByWechat(View v) {
        ThirdLoginUtil.getInstance().thirdLogin(this, Wechat.NAME, mOnThirdLoginListener);
    }

    public void loginByQQ(View v) {
        ThirdLoginUtil.getInstance().thirdLogin(this, QQ.NAME, mOnThirdLoginListener);
    }

    public void loginByWeibo(View v) {
        ThirdLoginUtil.getInstance().thirdLogin(this, SinaWeibo.NAME, mOnThirdLoginListener);
    }

}
