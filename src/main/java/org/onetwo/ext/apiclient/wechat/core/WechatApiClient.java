package org.onetwo.ext.apiclient.wechat.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.onetwo.ext.apiclient.wechat.accesstoken.AccessTokenTypes;

/**
 * @author wayshall
 * <br/>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface WechatApiClient {

	String name() default "";
	String path() default "";
	String url() default "";
	AccessTokenTypes accessTokenType() default AccessTokenTypes.WECHAT;
}
