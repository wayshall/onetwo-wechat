package org.onetwo.ext.apiclient.wechat.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author weishao zeng
 * <br/>
 */
@AllArgsConstructor
public enum WechatEvents {
	
	CHANGE_CONTACT("通讯录变更");
	
	@Getter
	private final String label;
	
	public String getEventName() {
		return name().toLowerCase();
	}

}

