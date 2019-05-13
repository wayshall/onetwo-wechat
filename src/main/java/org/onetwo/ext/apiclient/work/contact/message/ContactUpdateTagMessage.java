package org.onetwo.ext.apiclient.work.contact.message;

import org.onetwo.common.jackson.ArrayToStringSerializer;
import org.onetwo.common.jackson.StringToLongArrayDerializer;
import org.onetwo.common.jackson.StringToStringArrayDerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ContactUpdateTagMessage extends ContactBaseMessage {
	
	@JacksonXmlProperty(localName="TagId")
	private Long tagId;
	
	/***
	 * 标签中新增的成员userid列表，用逗号分隔
	 */
	@JacksonXmlProperty(localName="AddUserItems")
	@JsonDeserialize(using=StringToStringArrayDerializer.class)
	@JsonSerialize(using=ArrayToStringSerializer.class)
	private String[] addUserItems;
	
	/***
	 * 标签中删除的成员userid列表，用逗号分隔
	 */
	@JacksonXmlProperty(localName="DelUserItems")
	@JsonDeserialize(using=StringToStringArrayDerializer.class)
	@JsonSerialize(using=ArrayToStringSerializer.class)
	private String[] delUserItems;
	
	/**
	 * 标签中新增的部门id列表，用逗号分隔
	 */
	@JacksonXmlProperty(localName="AddPartyItems")
	@JsonDeserialize(using=StringToLongArrayDerializer.class)
	@JsonSerialize(using=ArrayToStringSerializer.class)
	private Long[] addPartyItems;
	
	/***
	 * 标签中删除的部门id列表，用逗号分隔
	 */
	@JacksonXmlProperty(localName="DelPartyItems")
	@JsonDeserialize(using=StringToLongArrayDerializer.class)
	@JsonSerialize(using=ArrayToStringSerializer.class)
	private Long[] delPartyItems;

}

