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

	abstract public static class ContactChangeTypes {
		public static final String CREATE_USER = "create_user";
		public static final String UPDATE_USER = "update_user";
		public static final String DELETE_USER = "delete_user";
	}
	
	/*@AllArgsConstructor
	public static enum ContactChangeTypes implements ReceiveMessageType{
		
		CREATE_USER("新增成员事件", ContactCreateUserMessage.class),
//		DELETE_USER("删除成员事件"),
//		UPDATE_USER("更新成员事件")
		;
		
		@Getter
		private String label;
		@Getter
		private Class<? extends ReceiveMessage> messageClass;
		
		public String getName() {
			return name().toLowerCase();
		}

	}*/
}
