package org.onetwo.ext.apiclient.wechat.basic.response;

import org.onetwo.common.annotation.IgnoreField;

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
	boolean isSuccess();
}

