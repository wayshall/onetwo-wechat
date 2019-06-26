package org.onetwo.ext.apiclient.wechat.serve.service;

import java.util.Map;

import org.onetwo.common.jackson.JacksonXmlMapper;
import org.onetwo.ext.apiclient.wechat.serve.dto.MessageContext;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MessageTypeParams;

/**
 * @author weishao zeng
 * <br/>
 */
public class DefaultMessageConverter implements MessageMetaExtractor {

	private JacksonXmlMapper jacksonXmlMapper;

	public DefaultMessageConverter(JacksonXmlMapper jacksonXmlMapper) {
		super();
		this.jacksonXmlMapper = jacksonXmlMapper;
	}

	@Override
	public MessageMeta extract(MessageContext message) {
		Map<String, Object> messageMap = jacksonXmlMapper.fromXml(message.getDecryptBody(), Map.class);
		String type = (String)messageMap.get(MessageTypeParams.MSG_TYPE);
		// ChangeType为企业微信通知返回   https://work.weixin.qq.com/api/doc#90000/90135/90970
		if ("event".equals(type) && messageMap.containsKey("ChangeType")) {
			type = (String)messageMap.get("ChangeType");
		}
		return MessageMeta.builder().type(type).build();
	}
	
}

