package org.onetwo.ext.apiclient.work.contact.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * https://work.weixin.qq.com/api/doc#90000/90135/90971
 * 
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ContactUpdateDepartMessage extends ContactBaseMessage {

	@JacksonXmlProperty(localName="Id")
	private Long id;
	
	@JacksonXmlProperty(localName="Name")
	private String name;
	
	@JacksonXmlProperty(localName="ParentId")
	private Long parentid;
	
	
	@Data
	@EqualsAndHashCode(callSuper=true)
	static public class ContactCreateDepartMessage extends ContactUpdateDepartMessage {
		
		@JacksonXmlProperty(localName="Order")
		private Integer order;
		
	}
	
	
	@Data
	@EqualsAndHashCode(callSuper=true)
	static public class ContactDeleteDepartMessage extends ContactBaseMessage {

		@JacksonXmlProperty(localName="Id")
		private Long id;
		
	}
}

