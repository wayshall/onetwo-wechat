package org.onetwo.ext.apiclient.wxpay.core;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.onetwo.common.apiclient.ApiClientMethod;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.common.exception.ErrorTypes;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponsable;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClientResponseHandler;
import org.onetwo.ext.apiclient.wxpay.utils.WechatPayErrors;
import org.onetwo.ext.apiclient.wxpay.utils.WechatPayUtils.PayResponseFields;
import org.onetwo.ext.apiclient.wxpay.vo.response.WechatPayResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author wayshall
 * <br/>
 */
public class WechatPayApiClientResponseHandler extends WechatApiClientResponseHandler {
	
	@Override
	protected WechatResponsable createBaseResponseByMap(Map<String, ?> map) {
		return WechatPayResponse.baseBuilder()
								.returnCode(map.get(PayResponseFields.KEY_ERRCODE).toString())
								.returnMsg((String)map.get(PayResponseFields.KEY_ERRMSG))
								.resultCode((String)map.get(PayResponseFields.KEY_RESULT_CODE))
								.errCode((String)map.get(PayResponseFields.KEY_ERR_CODE))
								.errCodeDes((String)map.get(PayResponseFields.KEY_ERR_CODE_DES))
								.build();
	}

	@Override
	protected ApiClientException translateToApiClientException(ApiClientMethod invokeMethod,
			WechatResponsable wechatResponsable, ResponseEntity<?> responseEntity) {
		WechatPayResponse baseResponse = (WechatPayResponse)wechatResponsable;
		String errCode = baseResponse.getReturnCode();
		String errMsg = baseResponse.getReturnMsg();
		if (StringUtils.isNotBlank(baseResponse.getResultCode())) {
			errCode = baseResponse.getErrCode();
			errMsg = baseResponse.getErrCodeDes();
		}
		return WechatPayErrors.byErrcode(errCode)
						 .map(err->new ApiClientException(err, invokeMethod.getMethod(), null))
						 .orElse(new ApiClientException(ErrorTypes.of(errCode, 
								 										errCode + ":" + errMsg, 
								 										responseEntity.getStatusCodeValue())
								 									));
	}
	
}
