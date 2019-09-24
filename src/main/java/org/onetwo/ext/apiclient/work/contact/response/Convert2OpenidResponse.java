package org.onetwo.ext.apiclient.work.contact.response;

import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Convert2OpenidResponse extends WechatResponse {
	
	private String openid;
	
	@Data
	@EqualsAndHashCode(callSuper=true)
	static public class Convert2UseridResponse extends WechatResponse {
		private String userid;
	}

}

