package org.onetwo.ext.apiclient.wechat.menu.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;
import org.onetwo.common.file.FileUtils;
import org.onetwo.common.spring.utils.ClassPathJsonDataBinder;
import org.onetwo.ext.apiclient.wechat.WechatBaseTestsAdapter;
import org.onetwo.ext.apiclient.wechat.accesstoken.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.wechat.view.api.MenuService;
import org.onetwo.ext.apiclient.wechat.view.request.CreateMenuRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

/**
 * @author wayshall
 * <br/>
 */
public class MenuServiceTest extends WechatBaseTestsAdapter {
	
	@Autowired
	MenuService menuService;
	@Autowired
	AccessTokenService accessTokenService;
	
	@Test
	public void testCreateMenu() throws IOException{
		String classPath = "menu_create.json";

		CreateMenuRequest request = ClassPathJsonDataBinder.from(CreateMenuRequest.class, classPath);
//		json = JsonMapper.ignoreNull().toJson(request);
		WechatResponse res = menuService.create(getAccessToken(), request);
		assertThat(res.isSuccess()).isTrue();
		
		menuService.delete(getAccessToken());
	}
	
	@Test
	public void testCreateMenu2() throws IOException{
		String classPath = "menu_create.json";

		ClassPathResource cpr = new ClassPathResource(classPath);
		String json = FileUtils.readAsString(cpr.getInputStream());
		WechatResponse res = menuService.create(getAccessToken(), json);
		assertThat(res.isSuccess()).isTrue();
		
		
		
		menuService.delete(getAccessToken());
	}

}
