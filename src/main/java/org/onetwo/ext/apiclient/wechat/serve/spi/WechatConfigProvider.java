package org.onetwo.ext.apiclient.wechat.serve.spi;

import org.onetwo.common.exception.BaseException;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.crypt.AesException;
import org.onetwo.ext.apiclient.wechat.crypt.WXBizMsgCrypt;

/**
 * @author wayshall
 * <br/>
 */
public interface WechatConfigProvider {
	
	WechatConfig getWechatConfig();
	
	default public WXBizMsgCrypt getWXBizMsgCrypt(){
		WechatConfig wechatConfig = getWechatConfig();
		try {
			return new WXBizMsgCrypt(wechatConfig.getToken(), wechatConfig.getEncodingAESKey(), wechatConfig.getAppid());
		} catch (AesException e) {
			throw new BaseException(e.getMessage(), e);
		}
	}

}
