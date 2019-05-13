package org.onetwo.ext.apiclient.work.contact;
/**
 * @author weishao zeng
 * <br/>
 */

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.onetwo.common.jackson.JacksonXmlMapper;
import org.onetwo.common.jackson.JsonMapper;
import org.onetwo.common.spring.SpringUtils;
import org.onetwo.ext.apiclient.work.contact.message.ContactCreateUserMessage;

public class JackXmlTest {
	JacksonXmlMapper jacksonXmlMapper = JacksonXmlMapper.ignoreEmpty();
	@Test
	public void testCreateUserXml() {
		String path = "data/work_message.xml";
		String content = SpringUtils.readClassPathFile(path);
		System.out.println("content: " + content);
		Map<String, Object> res = jacksonXmlMapper.fromXml(content, Map.class);
		System.out.println("res: " + res);
		assertThat(res.get("ExtAttr")).isInstanceOf(LinkedHashMap.class);
		Map<String, Object> extAttr = (Map<String, Object>)res.get("ExtAttr");
		assertThat(extAttr.get("Item")).isInstanceOf(LinkedHashMap.class);
		// 多个item时被覆盖了
		Map<String, Object> item = (Map<String, Object>)res.get("Item");
		
		ContactCreateUserMessage message = jacksonXmlMapper.fromXml(content, ContactCreateUserMessage.class);
		System.out.println("message: " + message);
		assertThat(message.getDepartment()).contains(1L, 2L, 3L);
		assertThat(message.getIsLeaderInDept()).contains(1, 0, 0);
		assertThat(message.getAttrs()).size().isEqualTo(2);
		assertThat(message.getAttrs().get(0).getText().getValue()).isEqualTo("旅游");
		assertThat(message.getAttrs().get(1).getWeb().getTitle()).isEqualTo("企业微信");
	}
	
	@Test
	public void testCreateUserJson() {
		String json = "{\"clientId\":\"party\",\"event\":\"change_contact\",\"changeType\":\"create_user\",\"userId\":\"test2\",\"name\":\"测试2\",\"department\":\"1210018475\",\"isLeaderInDept\":\"0\",\"mobile\":\"13666666667\",\"gender\":\"1\",\"status\":4,\"avatar\":\"https://rescdn.qqmail.com/node/wwmng/wwmng/style/images/independent/DefaultAvatar$73ba92b5.png\",\"alias\":\"测试2\",\"flowType\":\"RECEIVE\"}";
		ContactCreateUserMessage message = JsonMapper.IGNORE_EMPTY.fromJson(json, ContactCreateUserMessage.class);
		System.out.println("message: " + message);
	}

}

