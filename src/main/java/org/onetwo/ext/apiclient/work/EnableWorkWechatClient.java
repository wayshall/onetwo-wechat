package org.onetwo.ext.apiclient.work;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.onetwo.ext.apiclient.work.core.EnableWorkWechatClientSelector;
import org.onetwo.ext.apiclient.work.utils.WorkWechatConstants.WorkUrlConst;
import org.springframework.context.annotation.Import;

/**
 * @author wayshall
 * <br/>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({EnableWorkWechatClientSelector.class})
public @interface EnableWorkWechatClient {
	
	String[] basePackages() default {};
	Class<?>[] basePackageClasses() default {};
	
	String baseUrl() default WorkUrlConst.API_BASE_URL;
	

}
