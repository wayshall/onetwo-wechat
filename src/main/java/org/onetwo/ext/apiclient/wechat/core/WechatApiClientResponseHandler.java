package org.onetwo.ext.apiclient.wechat.core;

import java.util.Map;

import org.onetwo.common.apiclient.impl.DefaultApiClientResponseHandler;
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
		/*if(invokeMethod.isReturnVoid()){
			responseType = WechatResponse.class;
		}*/
		/*if(!WechatResponse.class.isAssignableFrom(responseType)){
			Intro<?> intro = ReflectUtils.getIntro(responseType);
			if(!intro.hasProperty("errcode") || !intro.hasProperty("errmsg")){
				responseType = HashMap.class;
			}
//			ReflectUtils.getIntro(responseType).checkField("errcode", true);
//			ReflectUtils.getIntro(responseType).checkField("errmsg", true);
		}*/
		return responseType;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Object handleResponse(WechatMethod invokeMethod, ResponseEntity<?> responseEntity, Class<?> actualResponseType){
		Object resposne = responseEntity.getBody();
		if(responseEntity.getStatusCode().is2xxSuccessful()){
			WechatResponse baseResponse = null;
			if(WechatResponse.class.isInstance(resposne)){
				baseResponse = (WechatResponse) resposne;
			}else if(Map.class.isAssignableFrom(actualResponseType)){
				//reponseType have not define errcode and errmsg
				Map<String, ?> map = (Map<String, ?>) resposne;
				if(map.containsKey(KEY_ERRCODE)){
					baseResponse = WechatResponse.baseBuilder()
												.errcode(Integer.valueOf(map.get(KEY_ERRCODE).toString()))
												.errmsg((String)map.get(KEY_ERRMSG))
												.build();
					if(!invokeMethod.isReturnVoid()){
						resposne = map2Bean(map, invokeMethod.getMethodReturnType());
					}
				}else if(invokeMethod.isReturnVoid()){
					//返回值为void，并且请求没有返回错误，则返回null
					return null;
				}else{
					resposne = map2Bean(map, invokeMethod.getMethodReturnType());
				}
			}else{
				/*BeanWrapper bw = SpringUtils.newBeanWrapper(resposne);
				if(!bw.isWritableProperty("errcode")){
					throw new RestClientException("field[errcode] not found in rest client api response type: " + actualResponseType);
				}
				if(!bw.isWritableProperty("errmsg")){
					throw new RestClientException("field[errmsg] not found in rest client api response type: " + actualResponseType);
				}*/
				if(logger.isDebugEnabled()){
					logger.debug("Non-WechatResponse type: {}", resposne.getClass());
				}
			}
			
			if(baseResponse!=null && !baseResponse.isSuccess()){
				logger.error("api[{}] error response: {}", invokeMethod.getMethod().getName(), baseResponse);
				/*throw WechatErrors.byErrcode(baseResponse.getErrcode())
				 			 .map(err->new ApiClientException(err, invokeMethod.getMethod(), null))
				 			 .orElse(new ApiClientException(ErrorTypes.of(baseResponse.getErrcode().toString(), 
				 					 										baseResponse.getErrmsg(), 
				 					 										responseEntity.getStatusCodeValue())
				 					 									));*/
				throw WechatUtils.translateToApiClientException(invokeMethod, baseResponse, responseEntity);
//				throw new ApiClientException(ErrorTypes.of(baseResponse.getErrcode().toString(), baseResponse.getErrmsg(), responseEntity.getStatusCodeValue()));
			}
			
			if(invokeMethod.isReturnVoid()){
				//返回值为void，并且请求没有返回错误，则返回null
				return null;
			}
			
			return resposne;
		}
		throw new RestClientException("error response: " + responseEntity.getStatusCodeValue());
	}

}
