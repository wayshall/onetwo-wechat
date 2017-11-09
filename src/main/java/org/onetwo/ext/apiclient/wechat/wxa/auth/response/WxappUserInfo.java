package org.onetwo.ext.apiclient.wechat.wxa.auth.response;

import lombok.Data;

/**
 * @author wayshall
 * <br/>
 */
@Data
public class WxappUserInfo {

	String openId;
	String nickName;
	Integer gender;
	String city;
	String province;
	String country;
	String avatarUrl;
	String unionId;
	
	Watermark watermark;
	
	@Data
	public static class Watermark {
		String appid;
		Long timestamp;
	}
}
