package org.onetwo.ext.apiclient.work.serve.vo.message;

import java.util.List;

import org.onetwo.common.jackson.ArrayToStringSerializer;
import org.onetwo.common.jackson.StringToIntegerArrayDerializer;
import org.onetwo.common.jackson.StringToLongArrayDerializer;
import org.onetwo.ext.apiclient.wechat.serve.spi.Message;
import org.onetwo.ext.apiclient.work.contact.response.WorkUserInfoResponse.Attribute;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * https://work.weixin.qq.com/api/doc#90000/90135/90970/%E6%96%B0%E5%A2%9E%E6%88%90%E5%91%98%E4%BA%8B%E4%BB%B6
 * 
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ContactCreateUserMessage extends ContactBaseMessage implements Message {

	@JacksonXmlProperty(localName="UserID")
	private String userId;
	
	@JacksonXmlProperty(localName="Name")
	private String name;
	
	@JacksonXmlProperty(localName="Department")
	@JsonDeserialize(using=StringToLongArrayDerializer.class)
	@JsonSerialize(using=ArrayToStringSerializer.class)
	private Long[] department;
//	private String department;
	
	@JacksonXmlProperty(localName="IsLeaderInDept")
	@JsonDeserialize(using=StringToIntegerArrayDerializer.class)
	@JsonSerialize(using=ArrayToStringSerializer.class)
    private Integer[] isLeaderInDept; //": [1, 0],
//	private String isLeaderInDept; //": [1, 0],
	
	@JacksonXmlProperty(localName="Position")
    private String position; //": "后台工程师",
	
	@JacksonXmlProperty(localName="Mobile")
    private String mobile; //": "后台工程师",
	
	@JacksonXmlProperty(localName="Gender")
    private String gender; //": "1",
	
	@JacksonXmlProperty(localName="Email")
    private String email;
	
	/****
    * 激活状态: 1=已激活，2=已禁用，4=未激活。
已激活代表已激活企业微信或已关注微工作台（原企业号）。未激活代表既未激活企业微信又未关注微工作台（原企业号）。
    */
	@JacksonXmlProperty(localName="Status")
	private int status; //": 1,
	
	@JacksonXmlProperty(localName="Avatar")
    private String avatar;
	
	@JacksonXmlProperty(localName="Alias")
    private String alias;
	
	@JacksonXmlProperty(localName="Telephone")
    private String telephone;
	
	@JacksonXmlProperty(localName="Address")
    private String address;
	
	@JacksonXmlProperty(localName="ExtAttr")
//  private ExtattrData extattr;
	private List<Attribute> attrs;
}

