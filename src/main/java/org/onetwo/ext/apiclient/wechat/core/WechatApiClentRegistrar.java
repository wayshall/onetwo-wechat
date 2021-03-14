package org.onetwo.ext.apiclient.wechat.core;

import java.util.List;

import org.onetwo.common.apiclient.RestExecutorFactory;
import org.onetwo.common.apiclient.simple.GenericApiClentRegistrar;
import org.onetwo.ext.apiclient.wechat.EnableWechatClient;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

/**
 * @author wayshall
 * <br/>
 */
@ConditionalOnProperty(value = WechatConfig.ENABLED_KEY, matchIfMissing = true)
public class WechatApiClentRegistrar extends GenericApiClentRegistrar<EnableWechatClient, WechatApiClient> {
	
	private WechatApiClientResponseHandler responseHandler = new WechatApiClientResponseHandler();

	@Override
	protected List<BeanDefinition> scanBeanDefinitions(AnnotationMetadata importingClassMetadata) {
		String extraPackage = ClassUtils.getPackageName(getImportingAnnotationClass());
		return getAnnotationMetadataHelper(importingClassMetadata).scanBeanDefinitions(getComponentAnnotationClass(), extraPackage);
	}

	@Override
	protected BeanDefinitionBuilder createApiClientFactoryBeanBuilder(AnnotationMetadata annotationMetadata, AnnotationAttributes attributes) {
		String className = annotationMetadata.getClassName();
		BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(WechatApiClientFactoryBean.class);

		definition.addPropertyValue("accessTokenType", attributes.getEnum("accessTokenType"));
		definition.addPropertyValue("autoThrowIfErrorCode", attributes.getBoolean("autoThrowIfErrorCode"));
		definition.addPropertyValue("url", resolveUrl(attributes));
		definition.addPropertyValue("path", resolvePath(attributes));
//		definition.addPropertyValue("name", name);
		definition.addPropertyValue("interfaceClass", className);
		definition.addPropertyValue("responseHandler", responseHandler);
		definition.addPropertyReference("restExecutor", RestExecutorFactory.REST_EXECUTOR_FACTORY_BEAN_NAME);
//		definition.addPropertyValue("decode404", attributes.get("decode404"));
//		definition.addPropertyValue("fallback", attributes.get("fallback"));
		definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
		
		return definition;
	}

}
