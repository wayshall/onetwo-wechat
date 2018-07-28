package org.onetwo.ext.apiclient.wechat.message.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.onetwo.ext.apiclient.wechat.message.request.SendAllRequest.MediaRequestItem;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MsgTypes;

/**
 * 语音/音频
 * @author LeeKITMAN
 * @author wayshall
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ImageRequest extends SendAllRequest{

    private MediaRequestItem image = new MediaRequestItem();

	public ImageRequest() {
		super(MsgTypes.IMAGE);
	}
}
