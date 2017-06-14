package org.onetwo.ext.apiclient.wechat.menu.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.onetwo.common.spring.utils.ClassPathJsonDataBinder;
import org.onetwo.ext.apiclient.wechat.WechatBaseTests;
import org.onetwo.ext.apiclient.wechat.basic.response.BaseResponse;
import org.onetwo.ext.apiclient.wechat.menu.request.CreateMenuRequest;
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
		BaseResponse res = menuService.create(request);
		assertThat(res.isSuccess()).isTrue();
	}

}
