package org.onetwo.ext.apiclient.wechat.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.onetwo.common.apiclient.ApiClientMethod;
import org.onetwo.common.apiclient.CustomResponseHandler;
import org.onetwo.common.apiclient.resouce.FileNameByteArrayResource;
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
	private static final Pattern FILE_NAME_PATTERN = Pattern.compile("^.*filename[\\*]?=\"(.*)\".*$");
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
		ByteArrayResource byteArray = responseEntity.getBody();
		String filename = null;
		String disposition = responseEntity.getHeaders().getFirst("Content-disposition");
		if (StringUtils.isNotBlank(disposition)) {
			filename = extractFileName(disposition);
		}
		FileNameByteArrayResource fileRes = new FileNameByteArrayResource(filename, byteArray.getByteArray());
		return fileRes;
	}
	
	protected boolean isTextMedia(MediaType mediaType){
		return mediaType!=null && (mediaType.isCompatibleWith(MediaType.APPLICATION_JSON) || mediaType.isCompatibleWith(MediaType.TEXT_PLAIN));
	}
	
	public static String extractFileName(String disposition) {
		Matcher matcher = FILE_NAME_PATTERN.matcher(disposition);
		String fileName = null;
		if (matcher.matches()) {
			fileName = matcher.group(1);
		}
		return fileName;
	}
}
