package org.onetwo.ext.apiclient.work.contact.response;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class WorkUserInfoResponse extends WechatResponse {
	@NotNull
	@NotBlank
	private String userid;//": "zhangsan",
    private String name;//": "李四",
    private Long[] department;//": [1, 2],
    private Integer[] order;//": [1, 2],
    private String position; //": "后台工程师",
    private String mobile; //": "15913215421",
    private String gender; //": "1",
    private String email; //": "zhangsan@gzdev.com",
    @JsonProperty("is_leader_in_dept")
    private Integer[] isLeaderInDept; //": [1, 0],
    private String avatar; //": "http://wx.qlogo.cn/mmopen/ajNVdqHZLLA3WJ6DSZUfiakYe37PKnQhBIeOQBO4czqrnZDS79FH5Wm5m4X69TBicnHFlhiafvDwklOpZeXYQQ2icg/0",
    private String telephone; //": "020-123456",
    private Integer enable; //": 1,
    private String alias; //": "jackzhang",
    private String address; //": "广州市海珠区新港中路",
    private ExtattrData extattr;
    
    // 接口新增的字段
    /***
     * 全局唯一。对于同一个服务商，不同应用获取到企业内同一个成员的open_userid是相同的，最多64个字节。仅第三方应用可获取
     */
    @JsonProperty("open_userid")
    private String openUserid;
    /***
     * 主部门
     */
    @JsonProperty("main_department")
    private Long mainDepartment;
    
    /****
     * 激活状态: 1=已激活，2=已禁用，4=未激活。
已激活代表已激活企业微信或已关注微工作台（原企业号）。未激活代表既未激活企业微信又未关注微工作台（原企业号）。
     */
    private int status; //": 1,
    /***
     * 员工个人二维码，扫描可添加为外部联系人；第三方仅通讯录应用可获取
     */
    @JsonProperty("qr_code")
    private String qrCode; //": "https://open.work.weixin.qq.com/wwopen/userQRCode?vcode=xxx",
    @JsonProperty("external_position")
    private String externalPosition;//": "产品经理",
    @JsonProperty("external_profile")
    private ExternalProfileData externalProfile;

    @Data
    public static class ExtattrData {
//    	@JacksonXmlProperty(localName="Item")
//    	@JacksonXmlElementWrapper(localName = "ExtAttr")
    	private List<Attribute> attrs;
    }
    
    public static enum AttributeType {
    	TEXT,
    	WEB,
    	Miniprogram;
    	
    	@JsonValue
    	public int toValue() {
    		return ordinal();
    	}
    	
    	public static AttributeType findByType(Class<?> type) {
    		if (type==TextValue.class) {
    			return TEXT;
    		} else if (type==WebValue.class) {
    			return WEB;
    		} else if (type==MiniprogramValue.class) {
    			return Miniprogram;
    		} else {
    			throw new IllegalArgumentException("unsupported data type: " + type);
    		}
    	}
    }
    
    @Data
	@JacksonXmlRootElement(localName="Item")
    public static class Attribute {
    	/***
    	 * 0: 文本
    	 * 1： 网页
    	 * 2： 小程序
    	 */
    	@JacksonXmlProperty(localName="Type")
    	private AttributeType type;
    	/****
    	 * 经试验，成员自定义属性名称要唯一
    	 * 但对外属性名称可重复……
    	 */
    	@JacksonXmlProperty(localName="Name")
    	private String name;
    	
    	/***
    	 * 如果是文本
    	 */
    	@JacksonXmlProperty(localName="Text")
    	private TextValue text;
    	/***
    	 * 如果是网页
    	 */
    	@JacksonXmlProperty(localName="Web")
    	private WebValue web;
    	/***
    	 * miniprogram
    	 */
    	@JacksonXmlProperty(localName="Miniprogram")
    	private MiniprogramValue miniprogram;
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Attribute other = (Attribute) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}
    	
    }

    @Data
    public static class TextValue {
    	@JacksonXmlProperty(localName="Value")
    	private String value;
    }

    @Data
    public static class WebValue {
    	@JacksonXmlProperty(localName="Url")
    	private String url;
    	@JacksonXmlProperty(localName="Title")
    	private String title;
    }

    @Data
    public static class MiniprogramValue {
    	@JacksonXmlProperty(localName="Appid")
    	private String appid;
    	@JacksonXmlProperty(localName="Pagepath")
    	private String pagepath;
    	@JacksonXmlProperty(localName="Title")
    	private String title;
    }
    
    @Data
    public static class ExternalProfileData {
    	@JsonProperty("external_corp_name")
    	private String externalCorpName;
    	@JsonProperty("external_attr")
    	private List<Attribute> externalAttr;
    }

}

