package org.onetwo.ext.apiclient.yly.core;

import org.onetwo.common.apiclient.ApiClientMethod;
import org.onetwo.common.apiclient.impl.DefaultApiClientResponseHandler;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClientFactoryBean.WechatMethod;
import org.onetwo.ext.apiclient.yly.response.YlyResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

/**
 * @author wayshall
 * <br/>
 */
public class YlyApiClientResponseHandler extends DefaultApiClientResponseHandler<WechatMethod> {
	
	@Override
	public Class<?> getActualResponseType(WechatMethod invokeMethod){
		Class<?> responseType = invokeMethod.getMethodReturnType();
		return responseType;
	}
	
	@Override
	public Object handleResponse(WechatMethod invokeMethod, ResponseEntity<?> responseEntity, Class<?> actualResponseType){
		Object response = responseEntity.getBody();
		if(responseEntity.getStatusCode().is2xxSuccessful()){
			YlyResponse baseResponse = (YlyResponse)response;
			
			if(baseResponse!=null && !baseResponse.isSuccess() && invokeMethod.isAutoThrowIfErrorCode()){
				logger.error("api[{}] error response: {}", invokeMethod.getMethod().getName(), baseResponse);
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
	
	protected ApiClientException translateToApiClientException(ApiClientMethod invokeMethod, YlyResponse baseResponse, ResponseEntity<?> responseEntity){
		return new ApiClientException(baseResponse.getError(), baseResponse.getErrorDescription(), invokeMethod.getMethod());
	}

}
