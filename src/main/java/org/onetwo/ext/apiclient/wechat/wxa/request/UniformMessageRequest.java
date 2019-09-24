package org.onetwo.ext.apiclient.wechat.wxa.request;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;
import org.onetwo.ext.apiclient.wechat.message.request.MpTemplateMessge;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * https://developers.weixin.qq.com/miniprogram/dev/api/sendUniformMessage.html
 * 
 * @author wayshall
 * <br/>
 */
@SuppressWarnings("serial")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UniformMessageRequest implements Serializable {

	/****
	 * 用户openid，可以是小程序的openid，也可以是mp_template_msg.appid对应的公众号的openid
	 * 
	 */
	@NotBlank
	private String touser;

	@NotBlank
	@JsonProperty("weapp_template_msg")
	private MessageTemplateRequest weappTemplateMsg;

	@NotBlank
	@JsonProperty("mp_template_msg")
	private MpTemplateMessgeVO mpTemplateMsg;

	@Data
	@EqualsAndHashCode(callSuper=false)
	public static class MpTemplateMessgeVO extends MpTemplateMessge {
		/***
		 * 公众号appid，要求与小程序有绑定且同主体
		 */
		private String appid;

		@Builder
		public MpTemplateMessgeVO(String templateId, String url, MiniprogramData miniprogram, String appid) {
			super(templateId, url, miniprogram);
			this.appid = appid;
		}
		
	}

}
