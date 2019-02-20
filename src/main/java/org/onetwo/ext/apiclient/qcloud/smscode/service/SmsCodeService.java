package org.onetwo.ext.apiclient.qcloud.smscode.service;

import java.util.function.Supplier;

import org.onetwo.ext.apiclient.qcloud.smscode.SmsCodeModule;

/**
 * @author weishao zeng
 * <br/>
 */
public interface SmsCodeService {

	/***
	 * 检查验证码
	 * @author weishao zeng
	 * @param mobile
	 * @param code
	 * @param supplier
	 */
	<T> T check(String mobile, SmsCodeModule bizType, String code, Supplier<T> supplier);

	/***
	 * 获取验证码
	 * @author weishao zeng
	 * @param mobile
	 * @param bizType
	 * @return
	 */
	String obtain(String mobile, SmsCodeModule bizType);

}
