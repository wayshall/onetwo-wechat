package org.onetwo.ext.apiclient.yly;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.onetwo.ext.apiclient.yly.core.EnableYilianyunClientSelector;
import org.springframework.context.annotation.Import;

/**
 * @author wayshall
 * <br/>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({EnableYilianyunClientSelector.class})
public @interface EnableYilianyunClient {
	
	String[] basePackages() default {};
	Class<?>[] basePackageClasses() default {};
	
	String baseUrl() default "https://open-api.10ss.net";
	

}
