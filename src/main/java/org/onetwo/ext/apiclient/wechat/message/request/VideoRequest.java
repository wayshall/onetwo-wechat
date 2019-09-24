package org.onetwo.ext.apiclient.wechat.message.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MsgTypes;

/**
 * 视频

请注意，此处视频的media_id需通过POST请求到下述接口特别地得到：
https://api.weixin.qq.com/cgi-bin/media/uploadvideo?access_token=ACCESS_TOKEN POST
数据如下（此处media_id需通过基础支持中的上传下载多媒体文件来得到）
 * @author LeeKITMAN
 * @author wayshall
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class VideoRequest extends SendAllRequest{

    private MediaRequestItem mpvideo = new MediaRequestItem();
    
	public VideoRequest() {
		super(MsgTypes.MPVIDEO);
	}
}
