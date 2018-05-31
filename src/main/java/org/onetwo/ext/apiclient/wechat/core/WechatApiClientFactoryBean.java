package org.onetwo.ext.apiclient.wechat.core;

import java.lang.reflect.Method;
import java.util.Optional;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.onetwo.common.apiclient.ApiClientMethod;
import org.onetwo.common.apiclient.RequestContextData;
import org.onetwo.common.apiclient.impl.AbstractApiClientFactoryBean;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.common.spring.SpringUtils;
import org.onetwo.common.utils.ParamUtils;
import org.onetwo.ext.apiclient.wechat.basic.request.AccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClientFactoryBean.WechatMethod;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.utils.WechatClientErrors;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants;
import org.onetwo.ext.apiclient.wechat.utils.WechatErrors;
import org.springframework.core.annotation.AnnotationAttributes;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;


/**
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WechatApiClientFactoryBean extends AbstractApiClientFactoryBean<WechatMethod> {

	final protected static LoadingCache<Method, WechatMethod> API_METHOD_CACHES = CacheBuilder.newBuilder()
																.build(new CacheLoader<Method, WechatMethod>() {
																	@Override
																	public WechatMethod load(Method method) throws Exception {
																		WechatMethod wechatMethod = new WechatMethod(method);
																		wechatMethod.initialize();
																		return wechatMethod;
																	}
																});

	@Override
	protected DefaultApiMethodInterceptor createApiMethodInterceptor() {
		WechatClientMethodInterceptor apiClient = new WechatClientMethodInterceptor(API_METHOD_CACHES);
		return apiClient;
	}
	
	protected String getAccessToken(){
		String accessToken = getAccessTokenService().getAccessToken().getAccessToken();
		return accessToken;
	}
	
	protected AccessTokenService getAccessTokenService(){
		AccessTokenService accessTokenService = SpringUtils.getBean(applicationContext, AccessTokenService.class);
		if(accessTokenService==null){
			throw new ApiClientException(WechatClientErrors.ACCESS_TOKEN_SERVICE_NOT_FOUND);
		}
		return accessTokenService;
	}
	
	final class WechatClientMethodInterceptor extends DefaultApiMethodInterceptor {
		public WechatClientMethodInterceptor(LoadingCache<Method, WechatMethod> methodCache) {
			super(methodCache);
		}

		
		@Override
		protected Object doInvoke(MethodInvocation invocation, WechatMethod invokeMethod) {
			try {
				return super.doInvoke(invocation, invokeMethod);
			} catch (ApiClientException e) {
				if(WechatErrors.ACCESS_TOKEN_INVALID.getErrorCode().equals(e.getCode()) && 
						invokeMethod.getAccessTokenParameter().isPresent()){
					Optional<AccessTokenInfo> at = invokeMethod.getAccessToken(invocation.getArguments());
					if(at.isPresent()){
						logger.info("remove accesstoken and retry...");
						getAccessTokenService().removeAccessToken(at.get().getAppid());
						return super.doInvoke(invocation, invokeMethod);
					}
				}
				throw e;
			}
		}


		/***
		 * 如果AccessTokenRequest没有设置过accessToken，则自动设置
		 * @author wayshall
		 * @param invocation
		 * @param method
		 * @return
		 */
		@Override
		protected Object[] processArgumentsBeforeRequest(MethodInvocation invocation, WechatMethod method){
			final Object[] args = super.processArgumentsBeforeRequest(invocation, method);
			method.findParameterByType(AccessTokenRequest.class)
					.ifPresent(p->{
						AccessTokenRequest atRequest = (AccessTokenRequest)args[p.getParameterIndex()];
						if(StringUtils.isBlank(atRequest.getAccessToken())){
							atRequest.setAccessToken(getAccessToken());
						}
					});
			return args;
		}
		
		/***
		 * 根据配置自动附加accessToken到url上
		 * @author wayshall
		 * @param actualUrl
		 * @param method
		 * @param uriVariables
		 * @return
		 */
		@Override
		public String processUrlBeforeRequest(final String actualUrl, WechatMethod method, RequestContextData context){
			String newUrl = actualUrl;
			if(method.isAutoAppendAccessToken()){
				String accessToken = getAccessToken();
				newUrl = ParamUtils.appendParam(newUrl, WechatConstants.PARAMS_ACCESS_TOKEN, accessToken);
			}
			Optional<AccessTokenInfo> at = method.getAccessToken(context.getMethodArgs());
			if(at.isPresent()){
				newUrl = ParamUtils.appendParam(newUrl, WechatConstants.PARAMS_ACCESS_TOKEN, at.get().getAccessToken());
			}
			newUrl = super.processUrlBeforeRequest(newUrl, method, context);
			return newUrl;
		}
	}

	static class WechatMethod extends ApiClientMethod {
		private boolean autoAppendAccessToken;
		private Optional<ApiClientMethodParameter> accessTokenParameter;
		
		public WechatMethod(Method method) {
			super(method);
		}

		@Override
		public void initialize(){
			super.initialize();
			Optional<AnnotationAttributes> configOpt = SpringUtils.getAnnotationAttributes(method, WechatRequestConfig.class, true);
			configOpt.ifPresent(attrs->{
				autoAppendAccessToken = attrs.getBoolean("accessToken");
			});

			accessTokenParameter = findParameterByType(AccessTokenInfo.class);
		}

		public boolean isAutoAppendAccessToken() {
			return autoAppendAccessToken;
		}

		public Optional<ApiClientMethodParameter> getAccessTokenParameter() {
			return accessTokenParameter;
		}
		
		public Optional<AccessTokenInfo> getAccessToken(final Object[] args){
			return accessTokenParameter.map(parameter->{
				return (AccessTokenInfo)args[parameter.getParameterIndex()];
			});
		}
		
	}
}
