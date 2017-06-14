package org.onetwo.ext.apiclient.wechat.menu.api;

import org.onetwo.ext.apiclient.wechat.basic.response.BaseResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.core.WechatMethodConfig;
import org.onetwo.ext.apiclient.wechat.menu.request.CreateMenuRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wayshall
 * <br/>
 */
@WechatApiClient(path="/menu")
public interface MenuService {
	
//	@RequestMapping(value="create?access_token=ACCESS_TOKEN")
	@PostMapping(value="/create")
	@WechatMethodConfig(accessToken=true)
	BaseResponse create(@RequestBody CreateMenuRequest createMenuRequest);

}
