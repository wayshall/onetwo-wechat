package org.onetwo.ext.apiclient.wechat.menu.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.onetwo.common.spring.utils.ClassPathJsonDataBinder;
import org.onetwo.ext.apiclient.wechat.WechatBaseTests;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.wechat.view.api.MenuService;
import org.onetwo.ext.apiclient.wechat.view.request.CreateMenuRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wayshall
 * <br/>
 */
public class MenuServiceTest extends WechatBaseTests {
	
	@Autowired
	MenuService menuService;
	
	@Test
	public void testCreateMenu(){
		CreateMenuRequest request = ClassPathJsonDataBinder.from(CreateMenuRequest.class, "menu_create.json");
		WechatResponse res = menuService.create(request);
		assertThat(res.isSuccess()).isTrue();
		
		menuService.delete();
	}

}
