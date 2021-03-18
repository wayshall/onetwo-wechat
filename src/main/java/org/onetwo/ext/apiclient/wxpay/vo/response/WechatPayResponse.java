package org.onetwo.ext.apiclient.wxpay.vo.response;

import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponsable;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants;
import org.onetwo.ext.apiclient.wxpay.utils.WechatPayUtils.PayResponseFields;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 修改：
 * 继承hashmap，防止微信增加字段后签名验证错误
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Builder(builderMethodName="baseBuilder")
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName="xml")
public class WechatPayResponse implements WechatResponsable {
	@JsonProperty(PayResponseFields.KEY_ERRCODE)
	private String returnCode;
	@JsonProperty(PayResponseFields.KEY_ERRMSG)
	private String returnMsg;

	/***
	 * 业务结果
	 */
	@JsonProperty(PayResponseFields.KEY_RESULT_CODE)
	protected String resultCode;
	
	@JsonProperty(PayResponseFields.KEY_ERR_CODE)
	protected String errCode;

	/***
	 * 错误代码描述 	String(128)
	 * 当result_code为FAIL时返回错误描述，详细参见下文错误列表
	 */
	@JsonProperty(PayResponseFields.KEY_ERR_CODE_DES)
	protected String errCodeDes;
	
	public boolean isSuccess(){
		return WechatConstants.SUCCESS.equals(returnCode) && WechatConstants.SUCCESS.equals(resultCode);
	}

}
