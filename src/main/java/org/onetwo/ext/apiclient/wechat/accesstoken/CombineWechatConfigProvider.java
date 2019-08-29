package org.onetwo.ext.apiclient.wechat.accesstoken;

import org.onetwo.common.utils.StringUtils;
import org.onetwo.ext.apiclient.wechat.core.SimpleWechatConfigProvider;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.crypt.WechatMsgCrypt;
import org.onetwo.ext.apiclient.wechat.utils.WechatUtils;
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

	/****
	 * 如果appid为空，并且存在默认的wechat配置，直接返回默认的wechat配置，否则返回work-wechat的默认配置（即第一个配置）
	 * 不为空，则按顺序通过wechat.appid、work-wechat.apps.appid、work-wechat.apps.agentid, work-wechat.apps.name查找
	 */
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
			if (config==null) {
				config = this.workWechatConfig.getWechatConfigByAgentId(appid);
			}
		}
		return config;
	}

	@Override
	public WechatMsgCrypt getWXBizMsgCrypt(String clientId){
		WechatConfig wechatConfig = getWechatConfig(clientId);
		WechatUtils.assertWechatConfigNotNull(wechatConfig, clientId);
		WechatMsgCrypt wxbizMsgCrypt = createWXBizMsgCrypt(wechatConfig);
		return wxbizMsgCrypt;
	}
}
