package org.onetwo.ext.apiclient.baidu.core;

import org.onetwo.common.apiclient.ApiClientMethod;
import org.onetwo.common.apiclient.RequestContextData;
import org.onetwo.common.apiclient.impl.DefaultApiClientResponseHandler;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.ext.apiclient.baidu.response.BaiduBaseResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClientFactoryBean.WechatMethod;
import org.springframework.http.ResponseEntity;

/**
 * @author wayshall
 * <br/>
 */
public class BaiduApiClientResponseHandler extends DefaultApiClientResponseHandler<WechatMethod> {
	
	@Override
	public Class<?> getActualResponseType(WechatMethod invokeMethod){
		Class<?> responseType = invokeMethod.getMethodReturnType();
		return responseType;
	}
	
	@Override
	public Object handleResponse(WechatMethod invokeMethod, ResponseEntity<?> responseEntity, RequestContextData context){
//		Class<?> actualResponseType = context.getResponseType();
		Object response = responseEntity.getBody();
		if(responseEntity.getStatusCode().is2xxSuccessful()){
			BaiduBaseResponse baseResponse = (BaiduBaseResponse)response;
//			
			boolean isAutoThrowIfError = true;
			if (context.getApiClientMethodConfig()!=null) {
				isAutoThrowIfError = context.getApiClientMethodConfig().isThrowIfError();
			}
			if(baseResponse!=null && !baseResponse.isSuccess() && isAutoThrowIfError){
				logger.error("api[{}] error response: {}", invokeMethod.getMethod().getName(), baseResponse);
				throw translateToApiClientException(invokeMethod, baseResponse, responseEntity);
//				throw new ApiClientException(ErrorTypes.of(baseResponse.getErrcode().toString(), baseResponse.getErrmsg(), responseEntity.getStatusCodeValue()));
			}
//			
//			if(invokeMethod.isReturnVoid()){
//				//返回值为void，并且请求没有返回错误，则返回null
//				return null;
//			}
			
			return response;
		}
		throw new ApiClientException("error response: " + responseEntity.getStatusCodeValue());
	}
	
	protected ApiClientException translateToApiClientException(ApiClientMethod invokeMethod, BaiduBaseResponse baseResponse, ResponseEntity<?> responseEntity){
		return new ApiClientException(baseResponse.getErrorCode(), baseResponse.getErrorMsg() + ", log_id: " + baseResponse.getLogId(), invokeMethod.getMethod());
	}

}
