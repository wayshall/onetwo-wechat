package org.onetwo.ext.apiclient.wechat.core;

import org.onetwo.common.exception.BaseException;
import org.onetwo.ext.apiclient.wechat.crypt.AesException;
import org.onetwo.ext.apiclient.wechat.crypt.WXBizMsgCrypt;
import org.onetwo.ext.apiclient.wechat.crypt.WechatMsgCrypt;
import org.onetwo.ext.apiclient.wechat.utils.WechatUtils;

/**
 * @author wayshall
 * <br/>
 */
public interface WechatConfigProvider {
	
	WechatConfig getWechatConfig(String wxappid);
	
	/*default WechatConfig getWechatConfig(String clientId) {
		return getWechatConfig(AppidRequest.builder()
											.appid(clientId)
											.accessTokenType(AccessTokenTypes.WECHAT)
											.build()
								);
	}
	WechatConfig getWechatConfig(AppidRequest appidRequest);*/
	
	default public WechatMsgCrypt getWXBizMsgCrypt(String wxappid){
		WechatConfig wechatConfig = getWechatConfig(wxappid);
		WechatUtils.assertWechatConfigNotNull(wechatConfig, wxappid);
		try {
			return new WXBizMsgCrypt(wechatConfig.getToken(), wechatConfig.getEncodingAESKey(), wechatConfig.getAppid());
		} catch (AesException e) {
			throw new BaseException(e.getMessage(), e);
		}
	}

}
