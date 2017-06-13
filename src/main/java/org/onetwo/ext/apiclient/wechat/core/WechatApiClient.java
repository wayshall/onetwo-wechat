package org.onetwo.ext.apiclient.wechat.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wayshall
 * <br/>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface WechatApiClient {

	String name() default "";

	/**
	 * Whether 404s should be decoded instead of throwing FeignExceptions
	 */
	boolean decode404() default false;

	Class<?> fallback() default void.class;

	String path() default "";
}
