package org.onetwo.ext.apiclient.wechat.core;

import java.util.stream.Stream;

import org.onetwo.common.apiclient.impl.AbstractApiClentRegistrar;
import org.onetwo.ext.apiclient.wechat.EnableWechatClient;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

/**
 * @author wayshall
 * <br/>
 */
public class WechatApiClentRegistrar extends AbstractApiClentRegistrar<EnableWechatClient, WechatApiClient> {
	
	private WechatApiClientResponseHandler responseHandler = new WechatApiClientResponseHandler();

	@Override
	protected Stream<BeanDefinition> scanBeanDefinitions(AnnotationMetadata importingClassMetadata) {
		String extraPackage = ClassUtils.getPackageName(getImportingAnnotationClass());
		return getAnnotationMetadataHelper(importingClassMetadata).scanBeanDefinitions(getComponentAnnotationClass(), extraPackage);
	}

	@Override
	protected BeanDefinitionBuilder createApiClientFactoryBeanBuilder(AnnotationMetadata annotationMetadata, AnnotationAttributes attributes) {
		String className = annotationMetadata.getClassName();
		BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(WechatApiClientFactoryBean.class);

		definition.addPropertyValue("url", resolveUrl(attributes));
		definition.addPropertyValue("path", resolvePath(attributes));
//		definition.addPropertyValue("name", name);
		definition.addPropertyValue("interfaceClass", className);
		definition.addPropertyValue("responseHandler", responseHandler);
		definition.addPropertyValue("restExecutor", getRestExecutor());
//		definition.addPropertyValue("decode404", attributes.get("decode404"));
//		definition.addPropertyValue("fallback", attributes.get("fallback"));
		definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
		
		return definition;
	}

}
