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
public class LinkWorkMessage extends BaseWorkMessage {

	/***
	 * 标题
	 */
	@JacksonXmlProperty(localName="Title")
	String title;
	
	/***
	 * 封面缩略图的url
	 */
	@JacksonXmlProperty(localName="PicUrl")
	String picUrl;
	
	/***
	 * 描述
	 */
	@JacksonXmlProperty(localName="Description")
	String description;
}
