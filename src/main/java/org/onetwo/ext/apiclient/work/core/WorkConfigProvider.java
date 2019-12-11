package org.onetwo.ext.apiclient.work.core;

import org.onetwo.common.exception.BaseException;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.crypt.AesException;
import org.onetwo.ext.apiclient.wechat.crypt.WXBizMsgCrypt;
import org.onetwo.ext.apiclient.wechat.crypt.WechatMsgCrypt;
import org.onetwo.ext.apiclient.wechat.utils.WechatUtils;

/**
 * 实现和WechatConfigProvider一样，只是为了区分微信和企业微信的配置
 * @author weishao zeng
 * <br/>
 */
public interface WorkConfigProvider {
	
	/****
	 * 
	 * @author weishao zeng
	 * @param clientId appid、agentId、name……
	 * @return
	 */
	WechatConfig getWechatConfig(String clientId);
	
	default public WechatMsgCrypt getWXBizMsgCrypt(String clientId){
		WechatConfig wechatConfig = getWechatConfig(clientId);
		WechatUtils.assertWechatConfigNotNull(wechatConfig, clientId);
		try {
			return new WXBizMsgCrypt(wechatConfig.getToken(), wechatConfig.getEncodingAESKey(), wechatConfig.getAppid());
		} catch (AesException e) {
			throw new BaseException(e.getMessage(), e);
		}
	}
}

