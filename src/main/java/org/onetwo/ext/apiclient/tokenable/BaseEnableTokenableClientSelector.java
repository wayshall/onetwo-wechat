package org.onetwo.ext.apiclient.tokenable;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.onetwo.common.apiclient.impl.RestExecutorConfiguration;
import org.onetwo.common.spring.context.AbstractImportSelector;
import org.onetwo.ext.apiclient.wechat.accesstoken.AccessTokenConfiguration;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenProvider;
import org.onetwo.ext.apiclient.yly.core.YlyAccessTokenProvider;
import org.onetwo.ext.apiclient.yly.core.YlyAppConfig;
import org.onetwo.ext.apiclient.yly.core.YlyConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 
 * 扩展：<br/>
 * <br/><br/>
 * 1，自定义@EnableXxxxApiClient和@XxxxApiClient <br/>
 * 2，定义XxxApiClentRegistrar, 继承和定制 TokenableApiClentRegistrar <br/>
 * 3，自定义EnableXxxClientSelector, 继承和定制EnableTokenableClientSelector <br/>
 * 
 * @author wayshall
 * <br/>
 */
abstract public class BaseEnableTokenableClientSelector<EnableTokenableClient extends Annotation> extends AbstractImportSelector<EnableTokenableClient> {

	@Override
	protected List<String> doSelect(AnnotationMetadata metadata, AnnotationAttributes attributes) {
		List<String> classNames = new ArrayList<String>();

//		classNames.add(YlyConfiguration1.class.getName());
		Class<?> configClass = getTokenableConfigurationClass();
		if (configClass!=null) {
			classNames.add(configClass.getName());
		}
		
		classNames.add(AccessTokenConfiguration.class.getName());
		
//		classNames.add(TokenableApiClentRegistrar.class.getName());
		classNames.add(getTokenableApiClentRegistrarClass().getName());
		
		classNames.add(RestExecutorConfiguration.class.getName());
		
		return classNames;
	}

	abstract protected Class<? extends TokenableApiClentRegistrar> getTokenableApiClentRegistrarClass();
	
	/***
	 * 一般需要配置自定义的accesstokenProvider实现即可<br/>
&#064;Configuration
public class TesetConfiguration {<br/>
	&#064;Bean<br/>
	public AccessTokenProvider ylyAccessTokenProvider() {<br/>
		TestAccessTokenProvider provider = new TestAccessTokenProvider();<br/>
		return provider;<br/>
	}<br/>
}<br/>
	 * @author weishao zeng
	 * @return
	 */
	protected Class<?> getTokenableConfigurationClass() {
		return null;
	}
}
