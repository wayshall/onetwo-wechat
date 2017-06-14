package org.onetwo.ext.apiclient.wechat.utils;
/**
 * @author wayshall
 * <br/>
 */
public abstract class WechatConstants {
	
	public static final String PARAMS_ACCESS_TOKEN = "access_token";
	
	//grantType
	public static final String GT_CLIENT_CREDENTIAL = "client_credential";
	
	/***
	 * 菜单按钮类型
	 * @author wayshall
	 *
	 */
	public abstract class ButtonTypes {
		public static final String CLICK = "click";
		public static final String VIEW = "view";
		public static final String SCANCODE_PUSH = "scancode_push";
		public static final String SCANCODE_WAITMSG = "scancode_waitmsg";
		public static final String PIC_SYSPHOTO = "pic_sysphoto";
		public static final String PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";
		public static final String PIC_WEIXIN = "pic_weixin";
		public static final String LOCATION_SELECT = "location_select";
		public static final String MEDIA_ID = "media_id";
		public static final String VIEW_LIMITED = "view_limited";
	}
	

}
