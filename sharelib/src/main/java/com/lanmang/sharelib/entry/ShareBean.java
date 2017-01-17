package com.lanmang.sharelib.entry;

import java.io.Serializable;

/**
 * @author JonahWangw
 *         Created by JonahWang on 2016/9/1.
 */
public class ShareBean implements Serializable {

    //Wechat
    public AppMessageBean appMessage;

    //WechatMoments
    public TimelineBean timeline;

    //ShortMessage
    public String sms;

    //QQ
    public QQFriend qqFriend;

    //QZone
    public QQZone qqZone;

    //SinaWeibo
    public WeiBo weibo;

    public AppMessageBean getAppMessage() {
        return appMessage;
    }

    public void setAppMessage(AppMessageBean appMessage) {
        this.appMessage = appMessage;
    }

    public TimelineBean getTimeline() {
        return timeline;
    }

    public void setTimeline(TimelineBean timeline) {
        this.timeline = timeline;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public QQFriend getQqFriend() {
        return qqFriend;
    }

    public void setQqFriend(QQFriend qqFriend) {
        this.qqFriend = qqFriend;
    }

    public QQZone getQqZone() {
        return qqZone;
    }

    public void setQqZone(QQZone qqZone) {
        this.qqZone = qqZone;
    }

    public WeiBo getWeibo() {
        return weibo;
    }

    public void setWeibo(WeiBo weibo) {
        this.weibo = weibo;
    }

    public static class AppMessageBean implements Serializable {
        public String title;
        public String desc;
        public String link;
        public String imgUrl;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }

    public static class TimelineBean implements Serializable {
        public String title;
        public String link;
        public String imgUrl;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }

    public static class QQFriend implements Serializable {
        public String title;
        public String targetUrl;
        public String imageUrl;
        public String summary;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTargetUrl() {
            return targetUrl;
        }

        public void setTargetUrl(String targetUrl) {
            this.targetUrl = targetUrl;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }
    }

    public static class QQZone implements Serializable {
        public String title;
        public String targetUrl;
        public String imageUrl;
        public String summary;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTargetUrl() {
            return targetUrl;
        }

        public void setTargetUrl(String targetUrl) {
            this.targetUrl = targetUrl;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }
    }

    public static class WeiBo implements Serializable {
        public String shareTitle;
        public String shareDesc;
        public String shareUrl;
        public String thumb;

        public String getShareTitle() {
            return shareTitle;
        }

        public void setShareTitle(String shareTitle) {
            this.shareTitle = shareTitle;
        }

        public String getShareDesc() {
            return shareDesc;
        }

        public void setShareDesc(String shareDesc) {
            this.shareDesc = shareDesc;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }
}
