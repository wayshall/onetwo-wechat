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
	 * 如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE。
code说明 ： code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。 
	 * 
	 * @author wayshall
	 * @return
	 */
	AuthorizeData createAuthorize();

}
