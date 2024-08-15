package org.onetwo.ext.apiclient.baidu.request;

import org.onetwo.common.annotation.FieldName;

import lombok.Data;

@Data
public class BaiduOAuthRequest {
	@FieldName("grant_type")
	String grantType = "client_credentials";
	@FieldName("client_id")
	String clientId;
	@FieldName("client_secret")
	String clientSecret;
}