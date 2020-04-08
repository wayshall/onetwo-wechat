package org.onetwo.ext.apiclient.yly.core;

import java.util.List;

import org.onetwo.common.apiclient.RestExecutorFactory;
import org.onetwo.common.apiclient.impl.AbstractApiClentRegistrar;
import org.onetwo.common.apiclient.impl.DefaultApiClientResponseHandler;
import org.onetwo.ext.apiclient.wechat.core.RemovableTokenError;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClientFactoryBean;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClientFactoryBean.WechatMethod;
import org.onetwo.ext.apiclient.yly.EnableYilianyunClient;
import org.onetwo.ext.apiclient.yly.core.YlyAccessTokenProvider.YlyAccessTokenTypes;
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
public class YlyApiClentRegistrar extends AbstractApiClentRegistrar<EnableYilianyunClient, YlyApiClient> {
	
	static private DefaultApiClientResponseHandler<WechatMethod> responseHandler = new YlyApiClientResponseHandler();
	static private RemovableTokenError removableTokenError = new RemovableTokenError() {
		
		@Override
		public boolean isNeedToRemoveToken(String errorCode) {
			return YlyErrors.ACCESS_TOKEN_EXPIRED.getErrorCode().equals(errorCode);
		}
	};

	@Override
	protected List<BeanDefinition> scanBeanDefinitions(AnnotationMetadata importingClassMetadata) {
		String extraPackage = ClassUtils.getPackageName(getImportingAnnotationClass());
		return getAnnotationMetadataHelper(importingClassMetadata).scanBeanDefinitions(getComponentAnnotationClass(), extraPackage);
	}

	@Override
	protected BeanDefinitionBuilder createApiClientFactoryBeanBuilder(AnnotationMetadata annotationMetadata, AnnotationAttributes attributes) {
		String className = annotationMetadata.getClassName();
		BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(WechatApiClientFactoryBean.class);
 
		definition.addPropertyValue("accessTokenType", YlyAccessTokenTypes.YI_LIAN_YUN);
//		definition.addPropertyValue("accessTokenType", attributes.getEnum("accessTokenType"));
		definition.addPropertyValue("url", resolveUrl(attributes));
		definition.addPropertyValue("path", resolvePath(attributes));
//		definition.addPropertyValue("name", name);
		definition.addPropertyValue("interfaceClass", className);
		definition.addPropertyValue("responseHandler", responseHandler);
		// 错误处理
//		definition.addPropertyValue("apiErrorHandler", apiErrorHandler);
		// 是否需要刷新token
		definition.addPropertyValue("removableTokenError", removableTokenError);
		definition.addPropertyReference("restExecutor", RestExecutorFactory.REST_EXECUTOR_FACTORY_BEAN_NAME);
//		definition.addPropertyValue("decode404", attributes.get("decode404"));
//		definition.addPropertyValue("fallback", attributes.get("fallback"));
		definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
		
		return definition;
	}

}
