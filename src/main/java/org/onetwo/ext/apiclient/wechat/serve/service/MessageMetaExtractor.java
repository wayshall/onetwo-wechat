package org.onetwo.ext.apiclient.wechat.serve.service;

import org.onetwo.ext.apiclient.wechat.serve.dto.MessageContext;
import org.onetwo.ext.apiclient.wechat.serve.spi.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weishao zeng
 * <br/>
 */
public interface MessageMetaExtractor {

	MessageMeta extract(MessageContext message);
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public class MessageMeta {
		String type;
		Class<? extends Message> messageBodyClass;
	}
}

