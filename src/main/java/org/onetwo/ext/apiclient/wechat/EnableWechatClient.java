package org.onetwo.ext.apiclient.wechat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.onetwo.ext.apiclient.qqmap.api.GeocoderClient;
import org.onetwo.ext.apiclient.wechat.utils.EnableWechatClientSelector;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.UrlConst;
import org.springframework.context.annotation.Import;

/**
 * @author wayshall
 * <br/>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({EnableWechatClientSelector.class})
public @interface EnableWechatClient {
	
	String[] basePackages() default {};
	Class<?>[] basePackageClasses() default {GeocoderClient.class};
	
	String baseUrl() default UrlConst.API_BASE_URL;
	
	/****
	 * 是否启用消息接收的相关服务
	 * @author wayshall
	 * @return
	 */
	boolean enableMessageServe() default false;
	boolean enableOAuth2Interceptor() default false;

}
