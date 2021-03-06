package org.onetwo.ext.apiclient.wechat.oauth2.response;

import java.util.List;

import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * snsapi_base
 * 
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class OAuth2UserInfoResponse extends WechatResponse {
	
	private String nickname;
	
	private String sex;
	private String province;
	private String city;
	private String country;
	private String headimgurl;
	
	private String openid;
	private List<String> privilege;
	private String unionid;

}
