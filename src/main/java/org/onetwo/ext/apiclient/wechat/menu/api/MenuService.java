package org.onetwo.ext.apiclient.wechat.menu.api;

import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.core.WechatMethodConfig;
import org.onetwo.ext.apiclient.wechat.menu.request.CreateMenuRequest;
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
	@PostMapping(value="/create")
	@WechatMethodConfig(accessToken=true)
	WechatResponse create(@RequestBody CreateMenuRequest createMenuRequest);

	/***
	 * <a href="https://mp.weixin.qq.com/wiki?action=doc&id=mp1421141015">view wechat api doc</a>
	 * @author wayshall
	 * @return
	 */
	@GetMapping(value="/delete")
	@WechatMethodConfig(accessToken=true)
	WechatResponse delete();
}
