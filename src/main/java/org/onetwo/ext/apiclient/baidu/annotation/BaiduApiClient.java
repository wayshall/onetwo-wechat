package org.onetwo.ext.apiclient.baidu.annotation;

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
public @interface BaiduApiClient {

	String name() default "";
	String path() default "";
	String url() default "";
//	boolean throwIfError() default true;
//	AccessTokenTypes accessTokenType() default YlyAccessTokenTypes.YI_LIAN_YUN;
}
