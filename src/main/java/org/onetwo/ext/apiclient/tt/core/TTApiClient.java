package org.onetwo.ext.apiclient.tt.core;

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
public @interface TTApiClient {

	String name() default "";
	String path() default "";
	String url() default "";
//	AccessTokenTypes accessTokenType() default AccessTokenTypes.WECHAT;
}
