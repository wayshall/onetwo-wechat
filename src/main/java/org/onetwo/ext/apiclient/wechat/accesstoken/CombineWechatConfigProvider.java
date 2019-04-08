package org.onetwo.ext.apiclient.wechat.accesstoken;

import org.onetwo.ext.apiclient.wechat.accesstoken.request.AppidRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenTypes;
import org.onetwo.ext.apiclient.wechat.core.SimpleWechatConfigProvider;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.work.core.WorkWechatConfig;

/**
 * @author wayshall
 * <br/>
 */
public class CombineWechatConfigProvider extends SimpleWechatConfigProvider {
	private WorkWechatConfig workWechatConfig;
	
	public CombineWechatConfigProvider(WechatConfig wechatConfig, WorkWechatConfig workWechatConfig) {
		super(wechatConfig);
		this.workWechatConfig = workWechatConfig;
	}

	@Override
	public WechatConfig getWechatConfig(AppidRequest appidRequest) {
		String appid = appidRequest.getAppid();
		if (appidRequest.getAccessTokenType()==AccessTokenTypes.WECHAT) {
			return super.getWechatConfig(appid);
		}
		return this.workWechatConfig.getWechatConfig(appid);
	}


}
