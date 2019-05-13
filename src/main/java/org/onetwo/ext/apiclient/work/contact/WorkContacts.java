package org.onetwo.ext.apiclient.work.contact;

import org.onetwo.ext.apiclient.wechat.serve.spi.MessageRouterService;
import org.onetwo.ext.apiclient.work.contact.message.ContactCreateUserMessage;
import org.onetwo.ext.apiclient.work.contact.message.ContactDeleteUserMessage;
import org.onetwo.ext.apiclient.work.contact.message.ContactUpdateDepartMessage;
import org.onetwo.ext.apiclient.work.contact.message.ContactUpdateDepartMessage.ContactCreateDepartMessage;
import org.onetwo.ext.apiclient.work.contact.message.ContactUpdateTagMessage;
import org.onetwo.ext.apiclient.work.contact.message.ContactUpdateUserMessage;
import org.onetwo.ext.apiclient.work.utils.WorkWechatConstants.ContactChangeTypes;

/**
 * @author weishao zeng
 * <br/>
 */
abstract public class WorkContacts {
	
	/****
	 * 映射通讯录的修改事件的消息类型
	 * 
	 * @author weishao zeng
	 * @param messageRouterService
	 * @return
	 */
	public static MessageRouterService mappingMessageClassesForChangeTypes(MessageRouterService messageRouterService) {
		messageRouterService.register(ContactChangeTypes.CREATE_USER, ContactCreateUserMessage.class, null)
							.register(ContactChangeTypes.UPDATE_USER, ContactUpdateUserMessage.class, null)
							.register(ContactChangeTypes.DELETE_USER, ContactDeleteUserMessage.class, null)
							.register(ContactChangeTypes.CREATE_PARTY, ContactCreateDepartMessage.class, null)
							.register(ContactChangeTypes.UPDATE_PARTY, ContactUpdateDepartMessage.class, null)
							.register(ContactChangeTypes.UPDATE_TAG, ContactUpdateTagMessage.class, null);
		return messageRouterService;
	}
	
	private WorkContacts() {
	}
	
}

