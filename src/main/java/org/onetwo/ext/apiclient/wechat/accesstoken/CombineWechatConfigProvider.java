package org.onetwo.ext.apiclient.wechat.accesstoken;

import org.onetwo.common.utils.StringUtils;
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
	public WechatConfig getWechatConfig(String appid) {
		WechatConfig config = null;
		if (StringUtils.isBlank(appid)) {
			if (StringUtils.isNotBlank(wechatConfig.getAppid())) {
				config = wechatConfig;
			} else {
				config = this.workWechatConfig.getDefaultWechatConfig();
			}
		} else if (appid.equals(wechatConfig.getAppid())) {
			config = wechatConfig;
		} else {
			config = this.workWechatConfig.getWechatConfig(appid);
		}
		return config;
	}


}
