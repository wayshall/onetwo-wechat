package org.onetwo.ext.apiclient.tokenable;

import java.lang.annotation.Annotation;
import java.util.List;

import org.onetwo.common.apiclient.ApiClientMethod;
import org.onetwo.common.apiclient.ApiClientResponseHandler;
import org.onetwo.common.apiclient.RestExecutorFactory;
import org.onetwo.common.apiclient.impl.AbstractApiClentRegistrar;
import org.onetwo.common.apiclient.impl.AbstractApiClientFactoryBean;
import org.onetwo.common.apiclient.simple.SimpleApiClientResponseHandler;
import org.onetwo.common.reflect.ReflectUtils;
import org.onetwo.common.spring.context.AnnotationMetadataHelper;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenType;
import org.onetwo.ext.apiclient.wechat.core.AccessTokenParameterTypes;
import org.onetwo.ext.apiclient.wechat.core.RemovableTokenError;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClientFactoryBean;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClientFactoryBean.WechatMethod;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

/**
 * @author wayshall
 * <br/>
 */
public class TokenableApiClentRegistrar extends AbstractApiClentRegistrar {
	
//	private AbstractApiClientFactoryBean<? extends ApiClientMethod> apiClientFactoryBeanClass;
	private ApiClientResponseHandler<?> responseHandler; // = new WechatApiClientResponseHandler();
	private RemovableTokenError removableTokenError; // = new RemovableTokenError() {
		
//		@Override
//		public boolean isNeedToRemoveToken(String errorCode) {
//			return YlyErrors.ACCESS_TOKEN_EXPIRED.getErrorCode().equals(errorCode);
//		}
//	};

	public TokenableApiClentRegistrar(Class<? extends Annotation> importingAnnotationClass,
			Class<? extends Annotation> componentAnnotationClass) {
		super(importingAnnotationClass, componentAnnotationClass);
	}
	
	protected AccessTokenType getAccessTokenType() {
		return null;
	}
	
	/****
	 * 自定义accessToken参数
	 * @author weishao zeng
	 * @return
	 */
	protected String getAccessTokenParameterName() {
		return WechatConstants.PARAMS_ACCESS_TOKEN;
	}
	protected AccessTokenParameterTypes getAccessTokenParameterTypes() {
		return AccessTokenParameterTypes.URL;
	}
	
	/***
	 * 可自定义 ApiClientFactoryBean 
	 * @author weishao zeng
	 * @return
	 */
	protected Class<? extends AbstractApiClientFactoryBean<? extends ApiClientMethod>> getApiClientFactoryBeanClass() {
		return WechatApiClientFactoryBean.class;
	}
	
	@Override
	protected void initBeforeRegisterBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		AnnotationMetadataHelper annotationMetadataHelper = getAnnotationMetadataHelper(importingClassMetadata);
		
		Class<? extends ApiClientResponseHandler<?>> resHandlerClass = annotationMetadataHelper.getAttributes().getClass("responseHandler");
		if (ApiClientResponseHandler.class.equals(resHandlerClass)) {
			responseHandler  = new SimpleApiClientResponseHandler<WechatMethod>();
		} else {
			responseHandler = ReflectUtils.newInstance(resHandlerClass);
		}

		Class<? extends RemovableTokenError> removableTokenErrorClass = annotationMetadataHelper.getAttributes().getClass("removableTokenError");
		if (!RemovableTokenError.class.equals(removableTokenErrorClass)) {
			removableTokenError = ReflectUtils.newInstance(removableTokenErrorClass);
		}
	}
	
	@Override
	protected List<BeanDefinition> scanBeanDefinitions(AnnotationMetadata importingClassMetadata) {
		String extraPackage = ClassUtils.getPackageName(getImportingAnnotationClass());
		return getAnnotationMetadataHelper(importingClassMetadata).scanBeanDefinitions(getComponentAnnotationClass(), extraPackage);
	}

	@Override
	protected BeanDefinitionBuilder createApiClientFactoryBeanBuilder(AnnotationMetadata annotationMetadata, AnnotationAttributes attributes) {
		String className = annotationMetadata.getClassName();
		BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(getApiClientFactoryBeanClass());
 
		AccessTokenType accessTokenType = getAccessTokenType();
		if (accessTokenType==null) {
			accessTokenType = (AccessTokenType)annotationMetadataHelper.getAttributes().getEnum("accessTokenType");
		}
		Boolean autoThrowIfErrorCode = (Boolean)annotationMetadataHelper.getAttributes().get("autoThrowIfErrorCode");
		if (autoThrowIfErrorCode==null) {
			autoThrowIfErrorCode = true;
		}
		
		definition.addPropertyValue("accessTokenType", accessTokenType);
		definition.addPropertyValue("accessTokenParameterName", getAccessTokenParameterName());
		definition.addPropertyValue("accessTokenParameterTypes", getAccessTokenParameterTypes());
//		definition.addPropertyValue("accessTokenType", attributes.getEnum("accessTokenType"));
		definition.addPropertyValue("url", resolveUrl(attributes));
		definition.addPropertyValue("path", resolvePath(attributes));
		definition.addPropertyValue("autoThrowIfErrorCode", autoThrowIfErrorCode);
		definition.addPropertyValue("interfaceClass", className);
		definition.addPropertyValue("responseHandler", responseHandler);
		// 错误处理
//		definition.addPropertyValue("apiErrorHandler", apiErrorHandler);
		// 是否需要刷新token
		definition.addPropertyValue("removableTokenError", removableTokenError);
		definition.addPropertyReference("restExecutor", RestExecutorFactory.REST_EXECUTOR_FACTORY_BEAN_NAME);
		
		return definition;
	}

}
