package org.onetwo.ext.apiclient.work.contact.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ContactUpdateDepartMessage extends ContactBaseMessage {

	@JacksonXmlProperty(localName="Id")
	private String id;
	
	@JacksonXmlProperty(localName="Name")
	private String name;
	
	@JacksonXmlProperty(localName="ParentId")
	private String parentid;
	
	
	@Data
	@EqualsAndHashCode(callSuper=true)
	public class ContactCreateDepartMessage extends ContactUpdateDepartMessage {
		
		@JacksonXmlProperty(localName="Order")
		private String order;
		
	}
}

