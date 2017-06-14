package org.onetwo.ext.apiclient.wechat.core;

import java.lang.annotation.Annotation;

import org.onetwo.common.apiclient.AbstractApiClentRegistrar;
import org.onetwo.ext.apiclient.wechat.EnableWechatClient;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author wayshall
 * <br/>
 */
public class WechatApiClentRegistrar extends AbstractApiClentRegistrar {
	
	private WechatApiClientResponseHandler responseHandler = new WechatApiClientResponseHandler();

	@Override
	protected Class<? extends Annotation> getImportingAnnotationClass() {
		return EnableWechatClient.class;
	}

	@Override
	protected Class<? extends Annotation> getApiClientTagAnnotationClass() {
		return WechatApiClient.class;
	}

	@Override
	protected BeanDefinitionBuilder createApiClientFactoryBeanBuilder(AnnotationMetadata annotationMetadata, AnnotationAttributes attributes) {
		String className = annotationMetadata.getClassName();
		BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(WechatApiClientFactoryBean.class);

		definition.addPropertyValue("url", resolveUrl());
		definition.addPropertyValue("path", resolvePath(attributes));
//		definition.addPropertyValue("name", name);
		definition.addPropertyValue("interfaceClass", className);
		definition.addPropertyValue("responseHandler", responseHandler);
//		definition.addPropertyValue("decode404", attributes.get("decode404"));
//		definition.addPropertyValue("fallback", attributes.get("fallback"));
		definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
		
		return definition;
	}

}
