package org.onetwo.ext.apiclient.wechat.utils;

import org.onetwo.dbm.mapping.DbmEnumValueMapping;

/**
 * @author weishao zeng
 * <br/>
 */

public enum WechatOAuthScopes implements DbmEnumValueMapping<String> {
	SNSAPI_BASE,
	SNSAPI_USERINFO;

	@Override
	public String getEnumMappingValue() {
		return name().toLowerCase();
	}
	
}
