package org.onetwo.ext.apiclient.work.serve.vo.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ContactUpdateUserMessage extends ContactCreateUserMessage {

	/***
	 * 新的UserID，变更时推送（userid由系统生成时可更改一次）
	 */
	@JacksonXmlProperty(localName="NewUserID")
	private String newUserId;
}

