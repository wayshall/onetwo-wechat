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
import org.onetwo.common.exception.BaseException;
import org.onetwo.common.spring.SpringUtils;
import org.onetwo.ext.apiclient.wechat.accesstoken.request.AppidRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenType;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClientFactoryBean.WechatMethod;
import org.onetwo.ext.apiclient.wechat.utils.WechatClientErrors;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants;
import org.onetwo.ext.apiclient.wechat.utils.WechatErrors;
import org.onetwo.ext.apiclient.wechat.utils.WechatException;
import org.slf4j.Logger;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WechatApiClientFactoryBean extends AbstractApiClientFactoryBean<WechatMethod> {

	final protected static Cache<Method, WechatMethod> API_METHOD_CACHES = CacheBuilder.newBuilder().<Method, WechatMethod>build();
//																.build(new CacheLoader<Method, WechatMethod>() {
//																	@Override
//																	public WechatMethod load(Method method) throws Exception {
//																		WechatMethod wechatMethod = new WechatMethod(method);
//																		wechatMethod.initialize();
//																		return wechatMethod;
//																	}
//																});


//	@Value(WechatConfigKeys.ACCESSTOKEN_AUTO_REMOVE_KEY)
	private boolean autoRemove = true;
	
	private AccessTokenType accessTokenType;
//	private AccessTokenServiceStrategy accessTokenServiceStrategy;
	private RemovableTokenError removableTokenError;
	private boolean autoThrowIfErrorCode = true;
	private String accessTokenParameterName = WechatConstants.PARAMS_ACCESS_TOKEN;
	private AccessTokenParameterTypes accessTokenParameterTypes = AccessTokenParameterTypes.URL;
	
	public void setAccessTokenParameterName(String accessTokenParameterName) {
		this.accessTokenParameterName = accessTokenParameterName;
	}
	
	public void setAccessTokenParameterTypes(AccessTokenParameterTypes accessTokenParameterTypes) {
		this.accessTokenParameterTypes = accessTokenParameterTypes;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		/*if (accessTokenServiceStrategy==null) {
			this.accessTokenServiceStrategy = DEFAULT_TOKEN_STRATEGY;
		} */
//		Assert.notNull(accessTokenType, "accessTokenType can not be null");
	}
	
	public void setRemovableTokenError(RemovableTokenError removableTokenError) {
		this.removableTokenError = removableTokenError;
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
//		AccessTokenService accessTokenService = accessTokenServiceStrategy.findAccessTokenService(applicationContext);
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
		public WechatClientMethodInterceptor(Cache<Method, WechatMethod> methodCache) {
			super(methodCache);
		}

		
		@Override
		protected Object doInvoke(MethodInvocation invocation, WechatMethod invokeMethod) throws Throwable {
			try {
				return super.doInvoke(invocation, invokeMethod);
			} catch (ApiClientException e) {
				if(isNeedToRemoveToken(e)){
					if (invokeMethod.getAccessTokenParameter().isPresent()) {
						Optional<AccessTokenInfo> at = invokeMethod.getAccessToken(invocation.getArguments());
						if(at.isPresent() && StringUtils.isNotBlank(at.get().getAppid())){
							return this.processAutoRemove(invocation, invokeMethod, at.get(), e);
						}
					} else if (invokeMethod.getAccessTokenRequest().isPresent()) {
						logger.info("AccessToken not found, but AccessTokenRequest found!");
						AccessTokenRequest atRequest = invokeMethod.<AccessTokenRequest>getParameterValue(invocation.getArguments(), invokeMethod.getAccessTokenRequest()).get();
						AccessTokenInfo at = new AccessTokenInfo(atRequest.obtainAppId(), null, atRequest.getAccessToken(), 0, null, null);
						at.setAccessTokenType(accessTokenType);
						return this.processAutoRemove(invocation, invokeMethod, at, e);
					} else {
						logger.warn("accesstoken is invalid and AccessTokenInfo not found");
					}
				} else {
					logger.warn("not removable token exception...");
				}
				throw e;
			}
		}
		
		@Override
		protected WechatMethod createMethod(Method method) {
			WechatMethod wechatMethod = new WechatMethod(method);
			wechatMethod.setAutoThrowIfErrorCode(autoThrowIfErrorCode);
			wechatMethod.initialize();
			return wechatMethod;
		}


		/***
		 * 根据错误代码判断是否需要移除token
		 * @author weishao zeng
		 * @param errorCode
		 * @return
		 */
		protected boolean isNeedToRemoveToken(ApiClientException e) {
			if (removableTokenError!=null) {
				return removableTokenError.isNeedToRemoveToken(e);
			} else {
				return WechatErrors.isNeedToRemoveToken(e.getCode());
			}
		}
		
		/***
		 * 处理自动删除accessToken，并重试
		 * @author weishao zeng
		 * @param invocation
		 * @param invokeMethod
		 * @param at
		 * @param e
		 * @return
		 */
		protected Object processAutoRemove(MethodInvocation invocation, WechatMethod invokeMethod, AccessTokenInfo at, ApiClientException e) throws Throwable {
			if (autoRemove) {
				String appid = at.getAppid();
				AccessTokenType att = accessTokenType!=null?accessTokenType:at.getAccessTokenType();
				Optional<AccessTokenInfo> refreshOpt = getAccessTokenService().refreshAccessTokenByAppid(
					new AppidRequest(appid, at.getAgentId(), att)
//					AppidRequest.builder()
//							.appid(appid)
//							.accessTokenType(att)
//							.build()
				);
				if (refreshOpt.isPresent()) {
					logger.info("refreshAccessTokenByAppid success, retry invoke wechat method. token: {}", refreshOpt.get().getAccessToken());
					at.setAccessToken(refreshOpt.get().getAccessToken());
					invokeMethod.<AccessTokenRequest>getParameterValue(invocation.getArguments(), invokeMethod.getAccessTokenRequest())
								.ifPresent(request -> {
									request.setAccessToken(at.getAccessToken());
								});
					return super.doInvoke(invocation, invokeMethod);
				} else {
					logger.warn("refreshAccessTokenByAppid faild, try to remove...");
					getAccessTokenService().removeAccessToken(new AppidRequest(appid, at.getAgentId(), att));
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
		protected void afterCreateRequestContextData(WechatMethod method, RequestContextData context) {
			Optional<AccessTokenInfo> at = method.getAccessToken(context.getMethodArgs());
			if(at.isPresent()){
				AccessTokenInfo token = at.get();
//				if (token.isAutoAppendToUrl()) {
				if (accessTokenParameterTypes==null || accessTokenParameterTypes==AccessTokenParameterTypes.URL) {
//					newUrl = ParamUtils.appendParam(newUrl, accessTokenParameterName, token.getAccessToken());
					context.getQueryParameters().put(accessTokenParameterName, token.getAccessToken());
				} else if (accessTokenParameterTypes==AccessTokenParameterTypes.HEADER) {
					context.getHeaders().add(accessTokenParameterName, token.getAccessToken());
				} else {
					throw new BaseException("unsupported AccessTokenParamTypes : " + accessTokenParameterTypes);
				}
			}
		}
		
//		public String processUrlBeforeRequest(final String actualUrl, WechatMethod method, RequestContextData context){
//			String newUrl = actualUrl;
//			Optional<AccessTokenInfo> at = method.getAccessToken(context.getMethodArgs());
//			if(at.isPresent()){
//				AccessTokenInfo token = at.get();
////				if (token.isAutoAppendToUrl()) {
//				if (accessTokenParameterTypes==null || accessTokenParameterTypes==AccessTokenParameterTypes.URL) {
//					newUrl = ParamUtils.appendParam(newUrl, accessTokenParameterName, token.getAccessToken());
//				} else if (accessTokenParameterTypes==AccessTokenParameterTypes.HEADER) {
//					context.getHeaders().add(accessTokenParameterName, token.getAccessToken());
//				} else {
//					throw new BaseException("unsupported AccessTokenParamTypes : " + accessTokenParameterTypes);
//				}
//			}
//			newUrl = super.processUrlBeforeRequest(newUrl, method, context);
//			return newUrl;
//		}
	}

	public static class WechatMethod extends ApiClientMethod {
//		private boolean autoAppendAccessToken;
		private Optional<ApiClientMethodParameter> accessTokenParameter;
		private Optional<ApiClientMethodParameter> accessTokenRequest;
		
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
			accessTokenRequest = findParameterByType(AccessTokenRequest.class);
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

		public Optional<ApiClientMethodParameter> getAccessTokenRequest() {
			return accessTokenRequest;
		}

		@Override
		protected boolean isSpecalPemerater(ApiClientMethodParameter parameter){
			return super.isSpecalPemerater(parameter) || 
					(accessTokenParameter.isPresent() && accessTokenParameter.get().getParameterIndex()==parameter.getParameterIndex());
		}

//		public Optional<AccessTokenArgs> getAccessTokenArgs(final Object[] args){
//			return getAccessToken(args).map(at -> {
//				RequestParam param = accessTokenParameter.get().getParameterAnnotation(RequestParam.class);
//				String paramName = WechatConstants.PARAMS_ACCESS_TOKEN;
//				if (param!=null) {
//					paramName = param.name();
//				}
//				AccessTokenArgs arg = new AccessTokenArgs(at, paramName);
//				return arg;
//			});
//		}
		
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


		@SuppressWarnings("unchecked")
		public <T> Optional<T> getParameterValue(final Object[] args, Optional<ApiClientMethodParameter> parameter){
			Logger logger = ApiClientUtils.getApiclientlogger();
			T at = null;
			if(parameter.isPresent()){
				at = (T)args[parameter.get().getParameterIndex()];
				if(at==null){
					throw new WechatException("parameter value not found: " + parameter.get().getParameterName());
				}
			}else{
				if(logger.isDebugEnabled()){
					logger.debug("Parameter not found!");
				}
			}
			return Optional.ofNullable(at);
		}
	}
}
