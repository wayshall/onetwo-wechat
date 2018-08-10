package org.onetwo.ext.apiclient.wechat.handler;

import org.onetwo.common.apiclient.ApiClientMethod;
import org.onetwo.common.apiclient.CustomResponseHandler;
import org.onetwo.common.jackson.JsonMapper;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.wechat.utils.WechatUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * @author wayshall
 * <br/>
 */
public class ByteArrayResponseHandler implements CustomResponseHandler<ByteArrayResource> {
	@Override
	public Class<ByteArrayResource> getResponseType() {
		return ByteArrayResource.class;
	}

	@Override
	public Object handleResponse(ApiClientMethod apiMethod, ResponseEntity<ByteArrayResource> responseEntity) {
		MediaType mediaType = responseEntity.getHeaders().getContentType();
		if(isTextMedia(mediaType)){
			WechatResponse response = JsonMapper.IGNORE_NULL.fromJson(responseEntity.getBody().getByteArray(), WechatResponse.class);
			if(!response.isSuccess()){
				throw WechatUtils.translateToApiClientException(apiMethod, response, responseEntity);
			}
		}
		return responseEntity.getBody();
	}
	
	protected boolean isTextMedia(MediaType mediaType){
		return mediaType!=null && (mediaType.isCompatibleWith(MediaType.APPLICATION_JSON) || mediaType.isCompatibleWith(MediaType.TEXT_PLAIN));
	}
}
