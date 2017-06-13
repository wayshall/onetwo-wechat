package org.onetwo.ext.apiclient.wechat.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.onetwo.common.apiclient.AbstractApiClientFactoryBean;
import org.onetwo.common.apiclient.ApiClientMethod;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.common.exception.ErrorTypes;
import org.onetwo.ext.apiclient.wechat.basic.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WechatApiClientFactoryBean extends AbstractApiClientFactoryBean<ApiClientMethod> {

	final protected static LoadingCache<Method, ApiClientMethod> API_METHOD_CACHES = CacheBuilder.newBuilder()
																.build(new CacheLoader<Method, ApiClientMethod>() {
																	@Override
																	public ApiClientMethod load(Method method) throws Exception {
																		return new ApiClientMethod(method);
																	}
																});

	private static final String KEY_ERRCODE = "errcode";
	private static final String KEY_ERRMSG = "errmsg";

	@Override
	protected DefaultApiMethodInterceptor createApiMethodInterceptor() {
		ApiClientMethodInterceptor apiClient = new ApiClientMethodInterceptor(API_METHOD_CACHES);
		return apiClient;
	}
	
	final class ApiClientMethodInterceptor extends DefaultApiMethodInterceptor {

		public ApiClientMethodInterceptor(LoadingCache<Method, ApiClientMethod> methodCache) {
			super(methodCache);
		}


		protected Class<?> getActualResponseType(ApiClientMethod invokeMethod){
			Class<?> responseType = invokeMethod.getMethodReturnType();
			if(!BaseResponse.class.isAssignableFrom(responseType)){
				responseType = HashMap.class;
			}
			return responseType;
		}
		
		@SuppressWarnings("unchecked")
		protected Object handleResponse(ApiClientMethod invokeMethod, ResponseEntity<?> responseEntity, Class<?> actualResponseType){
			if(responseEntity.getStatusCode().is2xxSuccessful()){
				Object resposne = responseEntity.getBody();
				BaseResponse baseResponse = null;
				if(Map.class.isAssignableFrom(actualResponseType)){
					Map<String, ?> map = (Map<String, ?>) resposne;
					if(map.containsKey(KEY_ERRCODE)){
						baseResponse = BaseResponse.builder()
													.errcode(Integer.valueOf(map.get(KEY_ERRCODE).toString()))
													.errmsg((String)map.get(KEY_ERRMSG))
													.build();
					}else{
						resposne = map2Bean(map, invokeMethod.getMethodReturnType());
					}
				}else{
					baseResponse = (BaseResponse) resposne;
				}
				if(baseResponse!=null && !baseResponse.isSuccess()){
					throw new ApiClientException(ErrorTypes.of(baseResponse.getErrcode().toString(), baseResponse.getErrmsg(), responseEntity.getStatusCodeValue()));
				}
				return resposne;
			}
			throw new RestClientException("error response: " + responseEntity.getStatusCodeValue());
		}


		

	}

}
