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
public class VoiceWorkMessage extends BaseWorkMessage {

	/***
	 * 语音格式，如amr，speex等
	 */
	@JacksonXmlProperty(localName="Format")
	String format;
	
	/***
	 * MediaId	图片媒体文件id，可以调用获取媒体文件接口拉取，仅三天内有效
	 */
	@JacksonXmlProperty(localName="MediaId")
	String mediaId;
}
