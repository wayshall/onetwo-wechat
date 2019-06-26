package org.onetwo.ext.apiclient.work.utils;

import org.onetwo.common.utils.GuavaUtils;
import org.onetwo.ext.apiclient.wechat.serve.spi.MessageRouterService;
import org.onetwo.ext.apiclient.work.contact.message.ContactCreateUserMessage;
import org.onetwo.ext.apiclient.work.contact.message.ContactDeleteUserMessage;
import org.onetwo.ext.apiclient.work.contact.message.ContactUpdateDepartMessage;
import org.onetwo.ext.apiclient.work.contact.message.ContactUpdateDepartMessage.ContactCreateDepartMessage;
import org.onetwo.ext.apiclient.work.contact.message.ContactUpdateTagMessage;
import org.onetwo.ext.apiclient.work.contact.message.ContactUpdateUserMessage;
import org.onetwo.ext.apiclient.work.utils.WorkWechatConstants.ContactChangeTypes;
import org.onetwo.ext.apiclient.work.utils.WorkWechatConstants.WorkReveiveMessageType;

/**
 * @author weishao zeng
 * <br/>
 */
abstract public class WorkWechatUtils {
	/***
	 * 企业微信不同的应用都是使用corpid和对应应用的secret获取accesstoken，
	 * 但是accessTokenService是通过appid（这里是corrpid）作为可以来做缓存的，
	 * 这样缓存无法区分不同的应用对应不同的accessToken；
	 * 这里通过企业微信获取token的时候传入corpid:agentId的方式来区分，
	 * 所以获取token的时候要先去掉agentid
	 */
	public static final String ID_SPLITOR = ":";
	
	public static String joinCorpid(String appid, Long agentId) {
		if (agentId==null) {
			return appid;
		}
		return appid.concat(ID_SPLITOR).concat(agentId.toString());
	}
	
	public static String splitCorpid(String appid) {
		String[] ids = GuavaUtils.split(appid, WorkWechatUtils.ID_SPLITOR);
		String corpid = ids[0];
		return corpid;
	}
	
	/****
	 * 映射通讯录的修改事件的消息类型
	 * 通过常量的方法手动映射
	 * @author weishao zeng
	 * @param messageRouterService
	 * @return
	 */
	public static MessageRouterService mappingMessageClassesForContactChange(MessageRouterService messageRouterService) {
		messageRouterService.register(ContactChangeTypes.CREATE_USER, ContactCreateUserMessage.class, null)
							.register(ContactChangeTypes.UPDATE_USER, ContactUpdateUserMessage.class, null)
							.register(ContactChangeTypes.DELETE_USER, ContactDeleteUserMessage.class, null)
							.register(ContactChangeTypes.CREATE_PARTY, ContactCreateDepartMessage.class, null)
							.register(ContactChangeTypes.UPDATE_PARTY, ContactUpdateDepartMessage.class, null)
							.register(ContactChangeTypes.UPDATE_TAG, ContactUpdateTagMessage.class, null);
		return messageRouterService;
	}
	
	/***
	 * 映射企业微信接收消息类型
	 * 通过枚举的方式自动映射
	 * @author weishao zeng
	 * @param messageRouterService
	 * @return
	 */
	public static MessageRouterService mappingMessageClassesForWorkReveiveMessages(MessageRouterService messageRouterService) {
		return messageRouterService.register(WorkReveiveMessageType.values());
	}
	
	private WorkWechatUtils() {
	}
	
}

