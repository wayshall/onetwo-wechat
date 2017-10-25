package org.onetwo.ext.apiclient.wechat.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wayshall
 * <br/>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WechatAppInfo {

	private String appid;
	private String appsecret;
}
