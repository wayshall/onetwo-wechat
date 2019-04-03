package org.onetwo.ext.apiclient.wechat.core;

import org.onetwo.common.exception.BaseException;
import org.onetwo.common.utils.StringUtils;
import org.onetwo.ext.apiclient.wechat.crypt.AesException;
import org.onetwo.ext.apiclient.wechat.crypt.WXBizMsgCrypt;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatConfigProvider;
import org.onetwo.ext.apiclient.wechat.utils.WechatException;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author wayshall
 * <br/>
 */
public class SimpleWechatConfigProvider implements WechatConfigProvider, InitializingBean {

	private WechatConfig wechatConfig;
	private WXBizMsgCrypt wxbizMsgCrypt;
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(wechatConfig!=null && StringUtils.isNotBlank(wechatConfig.getEncodingAESKey())){
			this.wxbizMsgCrypt = createWXBizMsgCrypt(wechatConfig);
		}
	}
	
	protected WXBizMsgCrypt createWXBizMsgCrypt(WechatConfig wechatConfig) {
		try {
			WXBizMsgCrypt wxbizMsgCrypt = new WXBizMsgCrypt(wechatConfig.getToken(), wechatConfig.getEncodingAESKey(), wechatConfig.getAppid());
			return wxbizMsgCrypt;
		} catch (AesException e) {
			throw new BaseException(e.getMessage(), e);
		}
	}
	

	@Override
	public WechatConfig getWechatConfig(String clientId) {
		return wechatConfig;
	}

	@Override
	public WXBizMsgCrypt getWXBizMsgCrypt(String clientId){
		if(this.wxbizMsgCrypt==null){
			throw new WechatException("可能没有配置encodingAesKey");
		}
		return this.wxbizMsgCrypt;
	}

	public void setWechatConfig(WechatConfig wechatConfig) {
		this.wechatConfig = wechatConfig;
	}

}
