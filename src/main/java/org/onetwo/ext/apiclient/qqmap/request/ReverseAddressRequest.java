package org.onetwo.ext.apiclient.qqmap.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReverseAddressRequest {

	@NotNull
	private String key;
	/***
	 * 地址（注：地址中请包含城市名称，否则会影响解析效果）
	 * address=北京市海淀区彩和坊路海淀西大街74号
	 */
	@NotNull
	private String address;
	
	/***
	 * 指定地址所属城市	region=北京
	 */
	private String region;
	

}

