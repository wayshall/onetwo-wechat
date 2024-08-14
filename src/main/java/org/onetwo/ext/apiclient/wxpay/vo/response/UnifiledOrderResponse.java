package org.onetwo.ext.apiclient.wxpay.vo.response;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
 * 
 * @author weishao zeng
 * <br/>
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class UnifiledOrderResponse extends WechatPayResponse {

	// 以下字段在return_code为SUCCESS的时候有返回
	
	@Length(max=32)
	@NotNull
	@NotBlank
	private String appid;
	
	@Length(max=32)
	@NotNull
	@NotBlank
	@JsonProperty("mch_id")
	private String mchId;

	/***
	 * 自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
	 */
	@JsonProperty("device_info")
	@Length(max=32)
	private String deviceInfo;
	

	@NotNull
	@NotBlank
	@JsonProperty("nonce_str")
	@Length(max=32)
	private String nonceStr;

	@NotNull
	@NotBlank
	@JacksonXmlCData
	@Length(max=32)
	private String sign;
	
	//以下字段在return_code 和result_code都为SUCCESS的时候有返回
	

	/****
	 * JSAPI -JSAPI支付
NATIVE -Native支付
APP -APP支付
说明详见参数规定
	 */
	@NotNull
	@NotBlank
	@Length(max=16)
	@JsonProperty("trade_type")
	private String tradeType;
	
	/***
	 * 微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时
	 */
	@NotNull
	@NotBlank
	@Length(max=16)
	@JsonProperty("prepay_id")
	private String prepayId;

	/****
	 * trade_type=NATIVE时有返回，此url用于生成支付二维码，然后提供给用户进行扫码支付。

注意：code_url的值并非固定，使用时按照URL格式转成二维码即可
	 */
	@Length(max=64)
	@JsonProperty("code_url")
	private String codeUrl;
	
}

