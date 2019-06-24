package org.onetwo.ext.apiclient.wechat.core;

import org.onetwo.common.exception.BaseException;
import org.onetwo.common.utils.StringUtils;
import org.onetwo.ext.apiclient.wechat.crypt.AesException;
import org.onetwo.ext.apiclient.wechat.crypt.WXBizMsgCrypt;
import org.onetwo.ext.apiclient.wechat.crypt.WechatMsgCrypt;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatConfigProvider;
import org.onetwo.ext.apiclient.wechat.utils.WechatException;
import org.onetwo.ext.apiclient.work.crypt.WXBizMsgCryptAdaptor;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author wayshall
 * <br/>
 */
public class SimpleWechatConfigProvider implements WechatConfigProvider, InitializingBean {

//	protected final Logger logger = JFishLoggerFactory.getLogger(getClass());
	
	protected WechatConfig wechatConfig;
	private WechatMsgCrypt wxbizMsgCrypt;
	
	public SimpleWechatConfigProvider(WechatConfig wechatConfig) {
		super();
		this.wechatConfig = wechatConfig;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(wechatConfig!=null && StringUtils.isNotBlank(wechatConfig.getEncodingAESKey())){
			this.wxbizMsgCrypt = createWXBizMsgCrypt(wechatConfig);
		}
	}
	
	protected WechatMsgCrypt createWXBizMsgCrypt(WechatConfig wechatConfig) {
		if (wechatConfig==null) {
			throw new IllegalArgumentException("wechat config can not be null!");
		}
		try {
			WechatMsgCrypt wxbizMsgCrypt = null;
			if (!wechatConfig.isWorkWechat()) { //普通微信
				wxbizMsgCrypt = new WXBizMsgCrypt(wechatConfig.getToken(), wechatConfig.getEncodingAESKey(), wechatConfig.getAppid());
			} else { // 企业微信
				wxbizMsgCrypt = new WXBizMsgCryptAdaptor(wechatConfig.getToken(), wechatConfig.getEncodingAESKey(), wechatConfig.getAppid());
			}
			return wxbizMsgCrypt;
		} catch (AesException e) {
			throw new BaseException(e.getMessage(), e);
		}
	}
	

	@Override
	public WechatConfig getWechatConfig(String appid) {
		return wechatConfig;
	}

	@Override
	public WechatMsgCrypt getWXBizMsgCrypt(String clientId){
		if(this.wxbizMsgCrypt==null){
			throw new WechatException("可能没有配置encodingAesKey");
		}
		return this.wxbizMsgCrypt;
	}

	public void setWechatConfig(WechatConfig wechatConfig) {
		this.wechatConfig = wechatConfig;
	}

}
