package org.onetwo.ext.apiclient.wxpay.vo.request;

import jakarta.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weishao zeng
 * <br/>
 */
@JacksonXmlRootElement(localName="xml")
@Data
@NoArgsConstructor
public class BasePayRequest {

	@NotBlank
	@NotNull
	@Length(max=32)
	private String appid;

	@NotNull
	@NotBlank
	@Length(max=32)
	@JsonProperty("mch_id")
	private String mchId;

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

	@JsonProperty("sign_type")
	private String signType;


	/*public BasePayRequest() {
		super();
	}*/
	
	public BasePayRequest(String appid, String mchId, String nonceStr, String sign, String signType) {
		super();
		this.appid = appid;
		this.mchId = mchId;
		this.nonceStr = nonceStr;
		this.sign = sign;
		if (StringUtils.isNotBlank(signType)) {
			this.signType = signType;
		} else {
			this.signType = WechatConstants.SIGN_MD5;
		}
	}

}

