package org.onetwo.ext.apiclient.wechat.media.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author LeeKITMAN
 */
@Data
public class AddNewsRequest {

    @NotNull
    private List<AddNewsItem> articles;
    
    @Data
    static public class AddNewsItem {

        @NotNull
        private String title;
        /**
         * 图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得
         */
        @NotNull
        @JsonProperty("thumb_media_id")
        private String thumbMediaId;
        private String author;
        /***
         * 图文消息的描述，如本字段为空，则默认抓取正文前64个字
         */
        private String digest;
        @NotNull
        @JsonProperty("show_cover_pic")
        private Boolean showCoverPic;
        /***
         * 图文消息页面的内容，支持HTML标签。具备微信支付权限的公众号，可以使用a标签，其他公众号不能使用，如需插入小程序卡片，可参考下文。
         */
        @NotNull
        private String content;
        /***
         * 在图文消息页面点击“阅读原文”后的页面，受安全限制，如需跳转Appstore，可以使用itun.es或appsto.re的短链服务，并在短链后增加 #wechat_redirect 后缀。
         */
        @NotNull
        @JsonProperty("content_source_url")
        private String contentSourceUrl;

    }

}
