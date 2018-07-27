package org.onetwo.ext.apiclient.wechat.article.vo.resp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;

import java.util.Date;

/**
 * 上传图文消息返回结果
 *
 * @author LeeKITMAN
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UploadNewsResBody extends WechatResponse {

    /**
     * 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb），图文消息（news）
     */
    private String type;
    /**
     * 媒体文件/图文消息上传后获取的唯一标识
     */
    private String media_id;

    /**
     * 媒体文件上传时间
     */
    private Date created_at;
}
