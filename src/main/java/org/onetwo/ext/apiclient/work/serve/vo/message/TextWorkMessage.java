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
public class TextWorkMessage extends BaseWorkMessage {

	@JacksonXmlProperty(localName="Content")
	String content;
}
