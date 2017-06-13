package org.onetwo.ext.apiclient.wechat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.onetwo.ext.apiclient.wechat.core.WechatApiClentRegistrar;
import org.onetwo.ext.apiclient.wechat.core.WechatSupportConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @author wayshall
 * <br/>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({WechatApiClentRegistrar.class, WechatSupportConfiguration.class})
public @interface EnableWechatClient {
	
	String[] basePackages() default {};
	Class<?>[] basePackageClasses() default {EnableWechatClient.class};
	
	String baseUrl() default "https://api.weixin.qq.com/cgi-bin";

}
