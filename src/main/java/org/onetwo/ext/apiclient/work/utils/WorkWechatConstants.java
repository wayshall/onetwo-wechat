package org.onetwo.ext.apiclient.work.utils;

import org.onetwo.ext.apiclient.work.core.WorkWechatConfig;

/**
 * @author wayshall
 * <br/>
 */
public abstract class WorkWechatConstants {

	public static abstract class LockerKeys {
		public static final String JSAPI_TICKET = "LOCKER:jsApiTicket:";
	}

	public static abstract class WorkWechatConfigKeys {
		public static final String STORER_KEY = WorkWechatConfig.CONFIG_PREFIX + ".accessToken.storer";
	}

	
	public static abstract class WorkUrlConst {
		public static final String API_DOMAIN_URL = "https://qyapi.weixin.qq.com";
		public static final String API_BASE_URL = API_DOMAIN_URL + "/cgi-bin";
		
	}
}
