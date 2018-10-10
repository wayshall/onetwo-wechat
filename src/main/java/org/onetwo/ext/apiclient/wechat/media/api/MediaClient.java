package org.onetwo.ext.apiclient.wechat.media.api;

import java.util.Map;

import org.onetwo.common.apiclient.ApiClientMethod;
import org.onetwo.common.apiclient.annotation.ResponseHandler;
import org.onetwo.common.jackson.JsonMapper;
import org.onetwo.common.utils.FieldName;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.handler.ByteArrayResponseHandler;
import org.onetwo.ext.apiclient.wechat.material.response.UploadNewsResponse;
import org.onetwo.ext.apiclient.wechat.media.request.AddNewsRequest;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MediaTypes;
import org.onetwo.ext.apiclient.wechat.utils.WechatUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * api地址：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1481187827_i0l21
 * @see ImageClient 
 * @author wayshall
 * <br/>
 */
@WechatApiClient
public interface MediaClient {
	
	/***
	 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738726
	 * @author wayshall
	 * @param accessToken
	 * @param type
	 * @param media
	 * @return
	 */
	@PostMapping(path="/media/upload", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	UploadNewsResponse upload(AccessTokenInfo accessToken, @RequestParam("type") MediaTypes type, @FieldName("media") Resource media);
	
	/**
     * 
     * 上传图文消息素材【订阅号与服务号认证后均可用】
     */
    @PostMapping(path = "/media/uploadnews", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    UploadNewsResponse uploadNews(AccessTokenInfo accessToken, @Validated @RequestBody AddNewsRequest body);
    
    /***
     * 获取临时素材
     * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738727
     * 
     * 如果返回的是视频消息素材，则内容如下：
		{
		 "video_url":DOWN_URL
		}
     * @author wayshall
     * @param accessToken
     * @param mediaId form-data中媒体文件标识
     * @return 如果是图片返回 ByteArrayResource，如果是视频，返回地址，类型为 String
     */
    @GetMapping(path = "/media/get", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseHandler(ByteArrayResponseHandler.class)
    <T> T get(AccessTokenInfo accessToken, @RequestParam("media_id") String mediaId);
    
    class MediaGetResponseHandler extends ByteArrayResponseHandler {
    	@Override
    	public Object handleResponse(ApiClientMethod apiMethod, ResponseEntity<ByteArrayResource> responseEntity) {
    		MediaType mediaType = responseEntity.getHeaders().getContentType();
    		if(isTextMedia(mediaType)){
    			byte[] bytes = responseEntity.getBody().getByteArray();
    			WechatResponse response = JsonMapper.IGNORE_NULL.fromJson(bytes, WechatResponse.class);
    			if(!response.isSuccess()){
    				throw WechatUtils.translateToApiClientException(apiMethod, response, responseEntity);
    			}
    			Map<String, Object> resMap = JsonMapper.IGNORE_NULL.fromJson(bytes, Map.class);
    			return resMap.get("video_url");
    		}
    		return responseEntity.getBody();
    	}
    }

}
