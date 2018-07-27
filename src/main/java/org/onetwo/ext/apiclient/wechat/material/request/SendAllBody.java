package org.onetwo.ext.apiclient.wechat.material.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author LeeKITMAN
 */
@Data
public class SendAllBody {

    @NotNull
    private SendAllBodyFilter filter;
    /**
     * 语音/音频
     */
    private SendAllBodyMpNews voice;
    /**
     * 图文
     */
    @NotNull
    private SendAllBodyMpNews mpnews;
    /**
     * 视频
     */
    private SendAllBodyMpNews mpvideo;
    /**
     * 微信卡券
     */
    private SendAllBodyWXCard wxcard;
    /**
     * 文本
     */
    private SendAllBodyText text;
    /**
     * 消息类型：mpnews（图文消息）、text（文本）、voice（语音/音频）、image（图片）、mpvideo（视频）、wxcard（微信卡券）
     */
    @NotNull
    private String msgtype;

    /**
     * 参数设置为1时，文章被判定为转载时，且原创文允许转载时，将继续进行群发操作。
     * 参数设置为0时，文章被判定为转载时，将停止群发操作。
     */
    @NotNull
    private Integer send_ignore_reprint;

    /**
     * 开发者侧群发msgid，长度限制64字节，如不填，则后台默认以群发范围和群发内容的摘要值做为clientmsgid
     */
    private String clientmsgid;
}
