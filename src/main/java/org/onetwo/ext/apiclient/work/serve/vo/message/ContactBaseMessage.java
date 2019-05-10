package org.onetwo.ext.apiclient.work.serve.vo.message;

import org.onetwo.ext.apiclient.wechat.serve.dto.ReceiveMessage;
import org.onetwo.ext.apiclient.wechat.serve.spi.Message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ContactBaseMessage extends ReceiveMessage implements Message {

	@JacksonXmlProperty(localName="Event")
	private String event;
	
	@JacksonXmlProperty(localName="ChangeType")
	private String changeType;
}

