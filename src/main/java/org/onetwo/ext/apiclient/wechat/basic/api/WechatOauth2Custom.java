package org.onetwo.ext.apiclient.wechat.basic.api;

import org.onetwo.ext.apiclient.wechat.basic.response.AuthorizeData;

/**
 * @author wayshall
 * <br/>
 */
public interface WechatOauth2Custom {
	
	/***
	 * 第一步：
	 * 创建oauth2的授权url
	 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842
	 * 
	 * @author wayshall
	 * @return
	 */
	AuthorizeData createAuthorize();

}
