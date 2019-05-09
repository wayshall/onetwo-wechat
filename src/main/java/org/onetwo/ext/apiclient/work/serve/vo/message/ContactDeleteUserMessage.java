package org.onetwo.ext.apiclient.work.serve.vo.message;

import org.onetwo.ext.apiclient.wechat.serve.dto.ReceiveMessage;
import org.onetwo.ext.apiclient.wechat.serve.spi.Message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * https://work.weixin.qq.com/api/doc#90000/90135/90970/%E5%88%A0%E9%99%A4%E6%88%90%E5%91%98%E4%BA%8B%E4%BB%B6
 * 
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ContactDeleteUserMessage extends ReceiveMessage implements Message {

	@JacksonXmlProperty(localName="Event")
	private String event;
	
	@JacksonXmlProperty(localName="ChangeType")
	private String changeType;
	
	@JacksonXmlProperty(localName="UserID")
	private String userId;
}

