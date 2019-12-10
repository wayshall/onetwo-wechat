package org.onetwo.ext.apiclient.wxpay.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@ToString(callSuper=true)
public class CloseOrderResponse extends WechatPayResponse {
	
	// 以下字段在return_code为SUCCESS的时候有返回

	protected String appid;

	/***
	 * 微信支付分配的商户号
	 */
	@JsonProperty("mch_id")
	protected String mchId;

	/***
	 * 随机字符串，不长于32位
	 */
	@JsonProperty("nonce_str")
	protected String nonceStr;

	protected String sign;
	
}

