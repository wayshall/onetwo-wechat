package org.onetwo.ext.apiclient.wechat.view.api;

import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.view.request.CreateMenuRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wayshall
 * <br/>
 */
@WechatApiClient(path="/menu")
public interface MenuService {
	
	/***
	 * <a href="https://mp.weixin.qq.com/wiki?action=doc&id=mp1421141013">view wechat api doc</a>
	 * @author wayshall
	 * @param createMenuRequest
	 * @return
	 */
	@PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	WechatResponse create(AccessTokenInfo accessTokenInfo, @RequestBody CreateMenuRequest createMenuRequest);
	

	@PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	WechatResponse create(AccessTokenInfo accessTokenInfo, @RequestBody String menuJson);

	/***
	 * <a href="https://mp.weixin.qq.com/wiki?action=doc&id=mp1421141015">view wechat api doc</a>
	 * @author wayshall
	 * @return
	 */
	@GetMapping(value="/delete")
	WechatResponse delete(AccessTokenInfo accessTokenInfo);
}
