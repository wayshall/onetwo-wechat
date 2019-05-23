package org.onetwo.ext.apiclient.wechat.basic.response;

import org.onetwo.common.annotation.IgnoreField;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author weishao zeng
 * <br/>
 */
public interface WechatResponsable {
	/***
	 * 相应是否成功
	 * @author weishao zeng
	 * @return
	 */
	@IgnoreField
	@JsonIgnore
	boolean isSuccess();
}

