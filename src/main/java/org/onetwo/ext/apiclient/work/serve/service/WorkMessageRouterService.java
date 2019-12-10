package org.onetwo.ext.apiclient.work.serve.service;

import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.crypt.WechatMsgCrypt;
import org.onetwo.ext.apiclient.wechat.serve.service.MessageRouterServiceImpl;
import org.onetwo.ext.apiclient.work.core.WorkConfigProvider;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishao zeng
 * <br/>
 */
public class WorkMessageRouterService extends MessageRouterServiceImpl {
	
	@Autowired
	private WorkConfigProvider workConfigProvider;

	@Override
	protected WechatMsgCrypt getMessageCrypt(String clientId) {
		return workConfigProvider.getWXBizMsgCrypt(clientId);
	}

	@Override
	public WechatConfig getWechatConfig(String clientId) {
		return workConfigProvider.getWechatConfig(clientId);
	}
	
}

