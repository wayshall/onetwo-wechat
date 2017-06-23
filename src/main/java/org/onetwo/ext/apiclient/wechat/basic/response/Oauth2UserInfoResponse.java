package org.onetwo.ext.apiclient.wechat.basic.response;

import java.util.List;

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
public class Oauth2UserInfoResponse extends WechatResponse {
	
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
