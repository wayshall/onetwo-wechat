package org.onetwo.ext.apiclient.wechat.view.api;

import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.view.request.CreateMenuRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	@PostMapping(value="/create?access_token={accessToken}", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	WechatResponse create(@PathVariable("accessToken") String accessToken, @RequestBody CreateMenuRequest createMenuRequest);
	

	@PostMapping(value="/create?access_token={accessToken}", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	WechatResponse create(@PathVariable("accessToken") String accessToken, @RequestBody String menuJson);

	/***
	 * <a href="https://mp.weixin.qq.com/wiki?action=doc&id=mp1421141015">view wechat api doc</a>
	 * @author wayshall
	 * @return
	 */
	@GetMapping(value="/delete?access_token={accessToken}")
	void delete(@PathVariable("accessToken") String accessToken);
}
