package org.onetwo.ext.apiclient.wechat.wxa.api;

import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.UrlConst;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * https://developers.weixin.qq.com/miniprogram/dev/api/imgSecCheck.html
 * 
 * @author wayshall
 * <br/>
 */
@WechatApiClient(url=UrlConst.API_DOMAIN_URL)
public interface ContentSecurityClient {

	/***
	 * https://developers.weixin.qq.com/miniprogram/dev/api/imgSecCheck.html
	 * 
	 * https://api.weixin.qq.com/wxa/img_sec_check?access_token=ACCESS_TOKEN
	 * 
	 * @author wayshall
	 * @param accessToken
	 * @param media
	 */
	@PostMapping(value="/wxa/img_sec_check", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	WechatResponse imgSecCheck(AccessTokenInfo accessToken, Resource media);
	

	@PostMapping(value="/wxa/msg_sec_check", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	WechatResponse msgSecCheck(AccessTokenInfo accessToken, String content);
	
}
