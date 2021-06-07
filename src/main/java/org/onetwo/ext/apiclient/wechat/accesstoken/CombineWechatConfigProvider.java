package org.onetwo.ext.apiclient.wechat.accesstoken;

import java.util.List;
import java.util.Optional;

import org.onetwo.common.utils.LangUtils;
import org.onetwo.ext.apiclient.wechat.core.SimpleWechatConfigProvider;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.crypt.WechatMsgCrypt;
import org.onetwo.ext.apiclient.wechat.utils.WechatUtils;

/**
 * @author wayshall
 * <br/>
 */
public class CombineWechatConfigProvider extends SimpleWechatConfigProvider {
	/***
	 * 用于可扩展的其它配置
	 */
	private List<MultiAppConfig> appConfigs;
	
	public CombineWechatConfigProvider(WechatConfig wechatConfig, List<MultiAppConfig> appConfigs) {
		super(wechatConfig);
		this.appConfigs = appConfigs;
	}

	/****
	 * 如果appid为空，并且存在默认的wechat配置，直接返回默认的wechat配置，否则返回work-wechat的默认配置（即第一个配置）
	 * 不为空，则查找扩展配置 
	 */
	@Override
	public WechatConfig getWechatConfig(String appid) {
		WechatConfig config = null;
		if (appid.equals(wechatConfig.getAppid())) {
			config = wechatConfig;
		} else {
			config = findInAppConfigs(appid);
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
