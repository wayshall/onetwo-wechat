package org.onetwo.ext.apiclient.wechat.core;

import java.lang.reflect.Method;
import java.util.Optional;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.onetwo.common.apiclient.AbstractApiClientFactoryBean;
import org.onetwo.common.apiclient.ApiClientMethod;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.common.spring.SpringUtils;
import org.onetwo.common.utils.ParamUtils;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClientFactoryBean.WechatMethod;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
																		return new WechatMethod(method);
																	}
																});

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	protected DefaultApiMethodInterceptor createApiMethodInterceptor() {
		ApiClientMethodInterceptor apiClient = new ApiClientMethodInterceptor(API_METHOD_CACHES);
		return apiClient;
	}
	
	final class ApiClientMethodInterceptor extends DefaultApiMethodInterceptor {
		public ApiClientMethodInterceptor(LoadingCache<Method, WechatMethod> methodCache) {
			super(methodCache);
		}

		@Override
		public String processUrlBeforeRequest(WechatMethod method, final String path){
			if(method.isAutoAppendAccessToken()){
				AccessTokenService accessTokenService = SpringUtils.getBean(applicationContext, AccessTokenService.class);
				if(accessTokenService==null){
					throw new ApiClientException("AccessTokenService not found!");
				}
				String accessToken = accessTokenService.getAccessToken().getAccessToken();
				String newPath = ParamUtils.appendParam(path, WechatConstants.PARAMS_ACCESS_TOKEN, accessToken);
				return newPath;
			}
			return path;
		}
	}

	static class WechatMethod extends ApiClientMethod {
		private boolean autoAppendAccessToken;
		
		public WechatMethod(Method method) {
			super(method);
			Optional<AnnotationAttributes> configOpt = SpringUtils.getAnnotationAttributes(method, WechatMethodConfig.class);
			configOpt.ifPresent(attrs->{
				autoAppendAccessToken = attrs.getBoolean("accessToken");
			});
		}

		public boolean isAutoAppendAccessToken() {
			return autoAppendAccessToken;
		}
		
	}
}
