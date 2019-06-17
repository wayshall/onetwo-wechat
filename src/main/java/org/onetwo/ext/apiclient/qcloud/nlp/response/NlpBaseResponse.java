package org.onetwo.ext.apiclient.qcloud.nlp.response;

import org.onetwo.common.apiclient.ApiResponsable;

import lombok.Data;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
public class NlpBaseResponse implements ApiResponsable<Integer> {
	Integer code; //	Int32	错误码。0：成功，其他值：失败
	String message; //	String	失败时候的错误信息，成功则无该字段
	
	public boolean isSuccess() {
		return Integer.valueOf(0).equals(code);
	}
	
	@Override
	public Integer resultCode() {
		return code;
	}
	@Override
	public String resultMessage() {
		return message;
	}
	
}
