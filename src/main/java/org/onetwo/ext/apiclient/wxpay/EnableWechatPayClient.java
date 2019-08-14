package org.onetwo.ext.apiclient.wxpay;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.onetwo.ext.apiclient.wxpay.core.EnableWechatPayClientSelector;
import org.onetwo.ext.apiclient.wxpay.utils.WechatPayUtils;
import org.springframework.context.annotation.Import;

/**
 * @author wayshall
 * <br/>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({EnableWechatPayClientSelector.class})
public @interface EnableWechatPayClient {
	
	String[] basePackages() default {};
	Class<?>[] basePackageClasses() default {};
	
	String baseUrl() default WechatPayUtils.API_DOMAIN_URL;
	

}
