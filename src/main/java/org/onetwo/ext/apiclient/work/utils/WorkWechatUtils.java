package org.onetwo.ext.apiclient.work.utils;

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

