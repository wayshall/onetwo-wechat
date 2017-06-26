package org.onetwo.ext.apiclient.wechat.basic.request;

import lombok.Builder;
import lombok.Data;

import org.onetwo.common.utils.FieldName;

/**
 * @author wayshall
 * <br/>
 */
@Data
@Builder
public class OAuth2UserInfoRequest {
	
	/***
	 * 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	 */
	@FieldName("access_token")
	private String accessToken;
	/***
	 * 用户的唯一标识
	 */
	private String openid;

}
