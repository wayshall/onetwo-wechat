package org.onetwo.ext.apiclient.qcloud;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * 所有模块启用后都另外可以通过配置文件disabled掉
 * @author wayshall
 * <br/>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({EnableQCloudServiceSelector.class})
public @interface EnableQCloudService {
	
	boolean live() default false;
	boolean sms() default false;
	
}
