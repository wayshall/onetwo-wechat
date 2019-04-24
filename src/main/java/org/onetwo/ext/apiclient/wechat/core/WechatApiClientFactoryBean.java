package org.onetwo.ext.apiclient.wechat.core;

import java.lang.reflect.Method;
import java.util.Optional;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.onetwo.common.apiclient.ApiClientMethod;
import org.onetwo.common.apiclient.ApiErrorHandler;
import org.onetwo.common.apiclient.RequestContextData;
import org.onetwo.common.apiclient.impl.AbstractApiClientFactoryBean;
import org.onetwo.common.apiclient.utils.ApiClientUtils;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.common.spring.SpringUtils;
import org.onetwo.common.utils.ParamUtils;
import org.onetwo.ext.apiclient.wechat.accesstoken.request.AppidRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenTypes;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClientFactoryBean.WechatMethod;
import org.onetwo.ext.apiclient.wechat.utils.WechatClientErrors;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.WechatConfigKeys;
import org.onetwo.ext.apiclient.wechat.utils.WechatErrors;
import org.onetwo.ext.apiclient.wechat.utils.WechatException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import lombok.Data;
import lombok.EqualsAndHashCode;


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


	@Value(WechatConfigKeys.ACCESSTOKEN_AUTO_REMOVE_KEY)
	private boolean autoRemove;
	
	private AccessTokenTypes accessTokenType;
	

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
//		Assert.notNull(accessTokenType, "accessTokenType can not be null");
	}
	
	@Override
	protected DefaultApiMethodInterceptor createApiMethodInterceptor() {
		WechatClientMethodInterceptor apiClient = new WechatClientMethodInterceptor(API_METHOD_CACHES);
		return apiClient;
	}
	
	/*protected String getAccessToken(){
		String accessToken = getAccessTokenService().getAccessToken().getAccessToken();
		return accessToken;
	}*/
	
	protected AccessTokenService getAccessTokenService(){
		AccessTokenService accessTokenService = SpringUtils.getBean(applicationContext, AccessTokenService.class);
		if(accessTokenService==null){
			throw new ApiClientException(WechatClientErrors.ACCESS_TOKEN_SERVICE_NOT_FOUND);
		}
		return accessTokenService;
//		AccessTokenService accessTokenService = SpringUtils.getBeans(applicationContext, AccessTokenService.class)
//															.stream()
//															.filter(ts -> ts.getSupportedClientType()==wxClientTypes)
//															.findFirst()
//															.orElseThrow(() -> new ApiClientException(WechatClientErrors.ACCESS_TOKEN_SERVICE_NOT_FOUND));
//		return accessTokenService;
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
				if(WechatErrors.isNeedToRemoveToken(e.getCode()) && 
						invokeMethod.getAccessTokenParameter().isPresent()){
					Optional<AccessTokenInfo> at = invokeMethod.getAccessToken(invocation.getArguments());
					if(at.isPresent() && StringUtils.isNotBlank(at.get().getAppid())){
						return this.processAutoRemove(invocation, invokeMethod, at.get(), e);
					} else {
						logger.warn("accesstoken is invalid and AccessTokenInfo not found");
					}
				} else {
					logger.warn("accesstoken autoRemove has disabled");
				}
				throw e;
			}
		}
		
		/***
		 * 处理自动删除……
		 * @author weishao zeng
		 * @param invocation
		 * @param invokeMethod
		 * @param at
		 * @param e
		 * @return
		 */
		protected Object processAutoRemove(MethodInvocation invocation, WechatMethod invokeMethod, AccessTokenInfo at, ApiClientException e) {
			if (autoRemove) {
				String appid = at.getAppid();
				Optional<AccessTokenInfo> refreshOpt = getAccessTokenService().refreshAccessTokenByAppid(
																							AppidRequest.builder()
																									.appid(appid)
																									.accessTokenType(accessTokenType)
																									.build()
																							);
				if (refreshOpt.isPresent()) {
					logger.info("refreshAccessTokenByAppid success, retry invoke wechat method. token: {}", refreshOpt.get().getAccessToken());
					at.setAccessToken(refreshOpt.get().getAccessToken());
					return super.doInvoke(invocation, invokeMethod);
				} else {
					logger.warn("refreshAccessTokenByAppid faild, try to remove...");
					getAccessTokenService().removeAccessToken(appid);
				}
			} else {
				logger.warn("accesstoken is invalid and disable auto remove");
			}
			throw e;
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
			/*method.findParameterByType(AccessTokenRequest.class)
					.ifPresent(p->{
						AccessTokenRequest atRequest = (AccessTokenRequest)args[p.getParameterIndex()];
						if(StringUtils.isBlank(atRequest.getAccessToken())){
							atRequest.setAccessToken(getAccessToken());
						}
					});*/
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
			/*if(method.isAutoAppendAccessToken()){
				String accessToken = getAccessToken();
				newUrl = ParamUtils.appendParam(newUrl, WechatConstants.PARAMS_ACCESS_TOKEN, accessToken);
			}*/
			Optional<AccessTokenInfo> at = method.getAccessToken(context.getMethodArgs());
			if(at.isPresent()){
				newUrl = ParamUtils.appendParam(newUrl, WechatConstants.PARAMS_ACCESS_TOKEN, at.get().getAccessToken());
			}
			newUrl = super.processUrlBeforeRequest(newUrl, method, context);
			return newUrl;
		}
	}

	public static class WechatMethod extends ApiClientMethod {
//		private boolean autoAppendAccessToken;
		private Optional<ApiClientMethodParameter> accessTokenParameter;
		/***
		 * 如果返回了业务错误代码，是否自动抛错
		 */
		private boolean autoThrowIfErrorCode = true;
		
		public WechatMethod(Method method) {
			super(method);
		}

		@Override
		public void initialize(){
			super.initialize();
			/*
			Optional<AnnotationAttributes> configOpt = SpringUtils.getAnnotationAttributes(method, WechatRequestConfig.class, true);
			configOpt.ifPresent(attrs->{
				autoAppendAccessToken = attrs.getBoolean("accessToken");
			});*/

			accessTokenParameter = findParameterByType(AccessTokenInfo.class);
		}
		
		@Override
		protected ApiErrorHandler obtainDefaultApiErrorHandler() {
			return ApiErrorHandler.DEFAULT;
		}
		
		/*public boolean isAutoAppendAccessToken() {
			return autoAppendAccessToken;
		}*/

		public Optional<ApiClientMethodParameter> getAccessTokenParameter() {
			return accessTokenParameter;
		}

		@Override
		protected boolean isSpecalPemerater(ApiClientMethodParameter parameter){
			return super.isSpecalPemerater(parameter) || 
					(accessTokenParameter.isPresent() && accessTokenParameter.get().getParameterIndex()==parameter.getParameterIndex());
		}
		
		public boolean isAutoThrowIfErrorCode() {
			return autoThrowIfErrorCode;
		}

		public Optional<AccessTokenInfo> getAccessToken(final Object[] args){
			/*return accessTokenParameter.map(parameter->{
				return (AccessTokenInfo)args[parameter.getParameterIndex()];
			});*/
			Logger logger = ApiClientUtils.getApiclientlogger();
			AccessTokenInfo at = null;
			if(accessTokenParameter.isPresent()){
				at = (AccessTokenInfo)args[accessTokenParameter.get().getParameterIndex()];
				if(at==null){
					throw new WechatException(WechatClientErrors.ACCESS_TOKEN_CANNOT_BE_NULL);
				}
			}else{
				if(logger.isDebugEnabled()){
					logger.debug("AccessTokenInfo Parameter not found!");
				}
			}
			return Optional.ofNullable(at);
		}
		
	}
}
