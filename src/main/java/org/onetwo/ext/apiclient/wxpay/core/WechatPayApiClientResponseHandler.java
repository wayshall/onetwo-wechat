package org.onetwo.ext.apiclient.wxpay.core;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.onetwo.common.apiclient.ApiClientMethod;
import org.onetwo.common.apiclient.impl.DefaultApiClientResponseHandler;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.common.exception.ErrorTypes;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClientFactoryBean.WechatMethod;
import org.onetwo.ext.apiclient.wxpay.utils.WechatPayUtils.PayResponseFields;
import org.onetwo.ext.apiclient.wxpay.vo.response.WechatPayResponse;
import org.onetwo.ext.apiclient.wxpay.utils.WechatPayErrors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

/**
 * @author wayshall
 * <br/>
 */
public class WechatPayApiClientResponseHandler extends DefaultApiClientResponseHandler<WechatMethod> {
	
	@Override
	public Class<?> getActualResponseType(WechatMethod invokeMethod){
		Class<?> responseType = invokeMethod.getMethodReturnType();
		return responseType;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Object handleResponse(WechatMethod invokeMethod, ResponseEntity<?> responseEntity, Class<?> actualResponseType){
		Object resposne = responseEntity.getBody();
		if(responseEntity.getStatusCode().is2xxSuccessful()){
			WechatPayResponse baseResponse = null;
			if(WechatPayResponse.class.isInstance(resposne)){
				baseResponse = (WechatPayResponse) resposne;
			}else if(Map.class.isAssignableFrom(actualResponseType)){
				//reponseType have not define errcode and errmsg
				Map<String, ?> map = (Map<String, ?>) resposne;
				if(map.containsKey(PayResponseFields.KEY_ERRCODE)){
					baseResponse = WechatPayResponse.baseBuilder()
												.returnErrcode(map.get(PayResponseFields.KEY_ERRCODE).toString())
												.returnErrmsg((String)map.get(PayResponseFields.KEY_ERRMSG))
												.resultCode((String)map.get(PayResponseFields.KEY_RESULT_CODE))
												.errCode((String)map.get(PayResponseFields.KEY_ERR_CODE))
												.errCodeDes((String)map.get(PayResponseFields.KEY_ERR_CODE_DES))
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
				if(logger.isDebugEnabled()){
					logger.debug("Non-WechatPayResponse type: {}", resposne.getClass());
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
			
			return resposne;
		}
		throw new RestClientException("error response: " + responseEntity.getStatusCodeValue());
	}
	

	public static ApiClientException translateToApiClientException(ApiClientMethod invokeMethod, WechatPayResponse baseResponse, ResponseEntity<?> responseEntity){
		String errCode = baseResponse.getReturnErrcode();
		String errMsg = baseResponse.getReturnErrmsg();
		if (StringUtils.isNotBlank(baseResponse.getResultCode())) {
			errCode = baseResponse.getErrCode();
			errMsg = baseResponse.getErrCodeDes();
		}
		return WechatPayErrors.byErrcode(errCode)
						 .map(err->new ApiClientException(err, invokeMethod.getMethod(), null))
						 .orElse(new ApiClientException(ErrorTypes.of(errCode, 
								 										errMsg, 
								 										responseEntity.getStatusCodeValue())
								 									));
	}

}
