package org.onetwo.ext.apiclient.wxpay.vo.response;

import org.onetwo.ext.apiclient.wxpay.utils.WechatPayUtils.PayResponseFields;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wayshall
 * <br/>
 */
@Data
@Builder(builderMethodName="baseBuilder")
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName="xml")
public class WechatPayResponse {
	@JsonProperty(PayResponseFields.KEY_ERRCODE)
	private String returnErrcode;
	@JsonProperty(PayResponseFields.KEY_ERRMSG)
	private String returnErrmsg;

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
		return "SUCCESS".equals(returnErrcode) && "SUCCESS".equals(resultCode);
	}

}
