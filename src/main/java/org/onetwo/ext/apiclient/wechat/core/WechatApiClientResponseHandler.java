package org.onetwo.ext.apiclient.wechat.core;

import java.util.Map;

import org.onetwo.common.apiclient.ApiClientMethod;
import org.onetwo.common.apiclient.impl.DefaultApiClientResponseHandler;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponsable;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClientFactoryBean.WechatMethod;
import org.onetwo.ext.apiclient.wechat.utils.WechatUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

/**
 * @author wayshall
 * <br/>
 */
public class WechatApiClientResponseHandler extends DefaultApiClientResponseHandler<WechatMethod> {
	private static final String KEY_ERRCODE = "errcode";
	private static final String KEY_ERRMSG = "errmsg";
	
	@Override
	public Class<?> getActualResponseType(WechatMethod invokeMethod){
		Class<?> responseType = invokeMethod.getMethodReturnType();
		if (CustomizeMappingField.class.isAssignableFrom(responseType)) {
			return Map.class;
		}
		return responseType;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Object handleResponse(WechatMethod invokeMethod, ResponseEntity<?> responseEntity, Class<?> actualResponseType){
		Object response = responseEntity.getBody();
		if(responseEntity.getStatusCode().is2xxSuccessful()){
			WechatResponsable baseResponse = null;
			if(WechatResponsable.class.isInstance(response)){
				baseResponse = (WechatResponsable) response;
			}else if(Map.class.isAssignableFrom(actualResponseType)){
				//reponseType have not define errcode and errmsg
				Map<String, ?> map = (Map<String, ?>) response;
				if (map.containsKey(KEY_ERRCODE)) {
					baseResponse = createBaseResponseByMap(map);
					if(!invokeMethod.isReturnVoid()){
//						response = map2Bean(map, invokeMethod.getMethodReturnType());
						response = handleResponseMap(map, invokeMethod.getMethodReturnType());
					}
				} else if (invokeMethod.isReturnVoid()) {
					//返回值为void，并且请求没有返回错误，则返回null
					return null;
				}
				else {
//					response = map2Bean(map, invokeMethod.getMethodReturnType());
					response = handleResponseMap(map, invokeMethod.getMethodReturnType());
				}
				/*if (response instanceof CustomizeMappingField) {
					CustomizeMappingField customRes = (CustomizeMappingField) response;
					customRes.mappingFields(map);
				}*/
			}else{
				/*BeanWrapper bw = SpringUtils.newBeanWrapper(resposne);
				if(!bw.isWritableProperty("errcode")){
					throw new RestClientException("field[errcode] not found in rest client api response type: " + actualResponseType);
				}
				if(!bw.isWritableProperty("errmsg")){
					throw new RestClientException("field[errmsg] not found in rest client api response type: " + actualResponseType);
				}*/
				if(logger.isDebugEnabled()){
					logger.debug("Non-WechatResponse type: {}", response.getClass());
				}
			}
			
			if(baseResponse!=null && !baseResponse.isSuccess() && invokeMethod.isAutoThrowIfErrorCode()){
				logger.error("api[{}] error response: {}", invokeMethod.getMethod().getName(), baseResponse);
				/*throw WechatErrors.byErrcode(baseResponse.getErrcode())
				 			 .map(err->new ApiClientException(err, invokeMethod.getMethod(), null))
				 			 .orElse(new ApiClientException(ErrorTypes.of(baseResponse.getErrcode().toString(), 
				 					 										baseResponse.getErrmsg(), 
				 					 										responseEntity.getStatusCodeValue())
				 					 									));*/
				throw translateToApiClientException(invokeMethod, baseResponse, responseEntity);
//				throw new ApiClientException(ErrorTypes.of(baseResponse.getErrcode().toString(), baseResponse.getErrmsg(), responseEntity.getStatusCodeValue()));
			}
			
			if(invokeMethod.isReturnVoid()){
				//返回值为void，并且请求没有返回错误，则返回null
				return null;
			}
			
			return response;
		}
		throw new RestClientException("error response: " + responseEntity.getStatusCodeValue());
	}
	
	public Object handleResponseMap(Map<String, ?> map, Class<?> responseType) {
		Object response = map2Bean(map, responseType);
		if (response instanceof CustomizeMappingField) {
			CustomizeMappingField customRes = (CustomizeMappingField) response;
			customRes.mappingFields(map);
		}
		return response;
	}
	protected WechatResponsable createBaseResponseByMap(Map<String, ?> map) {
		WechatResponse baseResponse = WechatResponse.baseBuilder()
													.errcode(Integer.valueOf(map.get(KEY_ERRCODE).toString()))
													.errmsg((String)map.get(KEY_ERRMSG))
													.build();
		return baseResponse;
	}
	

	protected ApiClientException translateToApiClientException(ApiClientMethod invokeMethod, WechatResponsable baseResponse, ResponseEntity<?> responseEntity){
		return WechatUtils.translateToApiClientException(invokeMethod, (WechatResponse)baseResponse, responseEntity);
	}

}
