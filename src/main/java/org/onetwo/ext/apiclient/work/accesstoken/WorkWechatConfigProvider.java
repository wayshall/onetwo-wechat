package org.onetwo.ext.apiclient.work.accesstoken;

import java.util.List;
import java.util.Optional;

import org.onetwo.common.utils.LangUtils;
import org.onetwo.common.utils.StringUtils;
import org.onetwo.ext.apiclient.wechat.accesstoken.MultiAppConfig;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.crypt.WechatMsgCrypt;
import org.onetwo.ext.apiclient.wechat.utils.WechatUtils;
import org.onetwo.ext.apiclient.work.core.WorkConfigProvider;
import org.onetwo.ext.apiclient.work.core.WorkWechatConfig;

/**
 * @author wayshall
 * <br/>
 */
public class WorkWechatConfigProvider implements WorkConfigProvider {
	/***
	 * 兼容旧配置
	 */
	protected WechatConfig wechatConfig;
	/***
	 * 企业微信
	 */
	private WorkWechatConfig workWechatConfig;
	/***
	 * 用于可扩展的其它配置
	 */
	private List<MultiAppConfig> appConfigs;
	
	public WorkWechatConfigProvider(WechatConfig wechatConfig, WorkWechatConfig workWechatConfig, List<MultiAppConfig> appConfigs) {
		this.wechatConfig = wechatConfig;
		this.workWechatConfig = workWechatConfig;
		this.appConfigs = appConfigs;
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
			
			if (config==null) {
				config = findInAppConfigs(appid);
			}
		}
		return config;
	}
	
	protected WechatConfig findInAppConfigs(String appid) {
		WechatConfig config = null;
		if (LangUtils.isEmpty(appConfigs)) {
			return config;
		}
		Optional<MultiAppConfig> mconfigs = this.appConfigs.stream().filter(cnf -> {
			return cnf.getWechatConfig(appid)!=null;
		}).findFirst();
		
		if (mconfigs.isPresent()) {
			config = mconfigs.get().getWechatConfig(appid);
		}
		return config;
	}

	@Override
	public WechatMsgCrypt getWXBizMsgCrypt(String clientId){
		WechatConfig wechatConfig = getWechatConfig(clientId);
		WechatUtils.assertWechatConfigNotNull(wechatConfig, clientId);
		WechatMsgCrypt wxbizMsgCrypt = WechatUtils.createWXBizMsgCrypt(wechatConfig);
		return wxbizMsgCrypt;
	}
}
