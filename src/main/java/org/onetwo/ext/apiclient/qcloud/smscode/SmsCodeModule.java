package org.onetwo.ext.apiclient.qcloud.smscode;
/**
 * @author weishao zeng
 * <br/>
 */
public interface SmsCodeModule {
	
	default String getModuleCode() {
		return ((Enum<?>)this).name();
	}

}

