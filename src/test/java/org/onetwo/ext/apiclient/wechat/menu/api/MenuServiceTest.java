package org.onetwo.ext.apiclient.wechat.menu.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;
import org.onetwo.common.file.FileUtils;
import org.onetwo.common.jackson.JsonMapper;
import org.onetwo.common.spring.utils.ClassPathJsonDataBinder;
import org.onetwo.ext.apiclient.wechat.WechatBaseTests;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.wechat.view.api.MenuService;
import org.onetwo.ext.apiclient.wechat.view.request.CreateMenuRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

/**
 * @author wayshall
 * <br/>
 */
public class MenuServiceTest extends WechatBaseTests {
	
	@Autowired
	MenuService menuService;
	
	@Test
	public void testCreateMenu() throws IOException{
		String classPath = "menu_create.json";

		CreateMenuRequest request = ClassPathJsonDataBinder.from(CreateMenuRequest.class, classPath);
//		json = JsonMapper.ignoreNull().toJson(request);
		WechatResponse res = menuService.create(request);
		assertThat(res.isSuccess()).isTrue();
		
		ClassPathResource cpr = new ClassPathResource(classPath);
		String json = FileUtils.readAsString(cpr.getInputStream());
		res = menuService.create(json);
		assertThat(res.isSuccess()).isTrue();
		
		
		
		menuService.delete();
	}

}
