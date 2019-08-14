package org.onetwo.ext.apiclient.work.contact.response;

import java.util.List;

import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TagListResponse extends WechatResponse {
	
	private List<TagData> taglist;
	
	@Data
	public static class TagData {
		private Long tagid;
		private String tagname;
	}

}

