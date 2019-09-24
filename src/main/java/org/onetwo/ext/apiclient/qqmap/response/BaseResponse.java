package org.onetwo.ext.apiclient.qqmap.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
public class BaseResponse {
	/***
	 * 状态码，0为正常
310请求参数信息有误
311Key格式错误
306请求有护持信息请检查字符串
110请求来源未被授权
	 */
	private int status;
	private String message;
	@JsonProperty("request_id")
	private String requestId;
}

