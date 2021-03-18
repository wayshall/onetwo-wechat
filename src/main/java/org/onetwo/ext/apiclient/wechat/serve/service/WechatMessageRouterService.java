package org.onetwo.ext.apiclient.wechat.serve.service;

import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.core.WechatConfigProvider;
import org.onetwo.ext.apiclient.wechat.crypt.WechatMsgCrypt;
import org.onetwo.ext.apiclient.wechat.utils.WechatUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishao zeng
 * <br/>
 */
public class WechatMessageRouterService extends MessageRouterServiceImpl {
	@Autowired
	private WechatConfigProvider wechatConfigProvider;
	
	@Override
	protected WechatMsgCrypt getMessageCrypt(String clientId) {
		return wechatConfigProvider.getWXBizMsgCrypt(clientId);
	}
	
	public WechatConfig getWechatConfig(String clientId) {
		WechatConfig wechatConfig = this.wechatConfigProvider.getWechatConfig(clientId);
		WechatUtils.assertWechatConfigNotNull(wechatConfig, clientId);
		return wechatConfig;
	}

}

