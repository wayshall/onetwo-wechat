package org.onetwo.ext.apiclient.work.serve.vo.message;

import org.onetwo.ext.apiclient.wechat.serve.dto.ReceiveMessage;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class BaseWorkMessage extends ReceiveMessage {

	@JacksonXmlProperty(localName="AgentID")
	Long agentID; //	企业应用的id，整型。可在应用的设置页面查看
}
