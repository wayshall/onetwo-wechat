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
public class TagMemberResponse extends WechatResponse {
	
	private String tagname;
	private List<UserMember> userlist;
	private List<Long> partylist;
	
	
	@Data
	public static class UserMember {
		private String userid;
		private String name;
	}

}

