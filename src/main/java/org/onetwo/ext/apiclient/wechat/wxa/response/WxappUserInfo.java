package org.onetwo.ext.apiclient.wechat.wxa.response;

import lombok.Data;

/**
 * @author wayshall
 * <br/>
 */
@Data
public class WxappUserInfo {

	String openId;
	String nickName;
	/***
     * 性别，为了方便，取用微信的规则：用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 */
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
