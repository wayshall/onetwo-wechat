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
public class LocationWorkMessage extends BaseWorkMessage {

	/***
	 * 地理位置纬度
	 */
	@JacksonXmlProperty(localName="Location_X")
	Double locationx;
	
	/***
	 * 地理位置经度
	 */
	@JacksonXmlProperty(localName="Location_Y")
	Double locationy;
	
	/***
	 * 地图缩放大小
	 */
	@JacksonXmlProperty(localName="Scale")
	Integer scale;
	
	/***
	 * 地理位置信息
	 */
	@JacksonXmlProperty(localName="Label")
	String Label;
}
