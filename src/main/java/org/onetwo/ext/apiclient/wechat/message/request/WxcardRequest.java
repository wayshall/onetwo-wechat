package org.onetwo.ext.apiclient.wechat.message.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MsgTypes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 卡券消息（注意图文消息的media_id需要通过上述方法来得到）：
 * @author LeeKITMAN
 * @author wayshall
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class WxcardRequest extends SendAllRequest{

    private WxcardRequestItem wxcard = new WxcardRequestItem();

	public WxcardRequest() {
		super(MsgTypes.WXCARD);
	}
    
	@Data
	public static class WxcardRequestItem {
		@JsonProperty("card_id")
	    private String cardId;
	}
}
