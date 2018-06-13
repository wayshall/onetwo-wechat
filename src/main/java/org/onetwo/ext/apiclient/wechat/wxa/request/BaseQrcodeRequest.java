package org.onetwo.ext.apiclient.wechat.wxa.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wayshall
 * <br/>
 */
@SuppressWarnings("serial")
@Data
public class BaseQrcodeRequest implements Serializable {
	
	Integer width = 430;
	/***
	 * 自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调
	 */
	@JsonProperty("auto_color")
	Boolean autoColor = false;
	
	/***
	 * 是否需要透明底色， is_hyaline 为true时，生成透明底色的小程序码
	 */
	@JsonProperty("is_hyaline")
	Boolean hyaline = false;
	
	/***
	 * auth_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"},十进制表示
	 */
	@JsonProperty("line_color")
	LineColor lineColor;

	@Data
	@EqualsAndHashCode(callSuper=true)
	public static class GetwxacodeRequest extends BaseQrcodeRequest {
		/***
		 * 不能为空，最大长度 128 字节
		 */
		String path;
	}

	@Data
	@EqualsAndHashCode(callSuper=true)
	public static class GetwxacodeunlimitRequest extends BaseQrcodeRequest {
		/***
		 * 最大32个可见字符，只支持数字，大小写英文以及部分特殊字符：!#$&'()*+,/:;=?@-._~，
		 * 其它字符请自行编码为合法字符（因不支持%，中文无法使用 urlencode 处理，请使用其他编码方式）
		 */
		String scene;
		String page;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CreatewxaqrcodeRequest implements Serializable {
		String path;
		Integer width = 430;
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class LineColor {
		String r;
		String g;
		String b;
	}

}
