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
import org.onetwo.common.spring.SpringUtils;

public class JackXmlTest {
	JacksonXmlMapper jacksonXmlMapper = JacksonXmlMapper.ignoreEmpty();
	@Test
	public void test() {
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
	}

}

