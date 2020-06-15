package org.onetwo.ext.apiclient.wechat.utils;

import lombok.Data;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
public class WechatMobileVO {
	
//	@ApiModelProperty("用户绑定的手机号（国外手机号会有区号）")
	String phoneNumber;
//	@ApiModelProperty("没有区号的手机号")
	String purePhoneNumber;
//	@ApiModelProperty("区号")
	String countryCode;
	
	WatermarkInfo watermark;

	@Data
	public static class WatermarkInfo {
		String appid;
		Long timestamp;
	}
}
