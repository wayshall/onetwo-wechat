package org.onetwo.ext.apiclient.wechat.core;

import java.util.HashMap;
import java.util.Map;

import org.onetwo.common.apiclient.DefaultApiClientResponseHandler;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.common.exception.ErrorTypes;
import org.onetwo.ext.apiclient.wechat.basic.response.BaseResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClientFactoryBean.WechatMethod;
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
		if(!BaseResponse.class.isAssignableFrom(responseType)){
			responseType = HashMap.class;
		}
		return responseType;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Object handleResponse(WechatMethod invokeMethod, ResponseEntity<?> responseEntity, Class<?> actualResponseType){
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
