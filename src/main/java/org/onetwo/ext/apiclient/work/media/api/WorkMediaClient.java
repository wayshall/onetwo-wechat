package org.onetwo.ext.apiclient.work.media.api;

import org.onetwo.common.annotation.FieldName;
import org.onetwo.common.apiclient.annotation.ResponseHandler;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.handler.ByteArrayResponseHandler;
import org.onetwo.ext.apiclient.wechat.material.response.UploadNewsResponse;
import org.onetwo.ext.apiclient.wechat.media.response.UploadResponse;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MediaTypes;
import org.onetwo.ext.apiclient.work.core.WorkWechatApiClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * https://work.weixin.qq.com/api/doc#90000/90135/90254
 * @author weishao zeng
 * <br/>
 */
@WorkWechatApiClient
public interface WorkMediaClient {

	/***
	 * https://work.weixin.qq.com/api/doc#90000/90135/90253
	 * 
	 * @author weishao zeng
	 * @param accessToken
	 * @param type
	 * @param media
	 * @return
	 */
	@PostMapping(path="/media/upload", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	UploadNewsResponse upload(AccessTokenInfo accessToken, @RequestParam("type") MediaTypes type, @FieldName("media") Resource media);

    @GetMapping(path = "/media/get")
	@ResponseHandler(ByteArrayResponseHandler.class)
    ByteArrayResource get(AccessTokenInfo accessToken, @RequestParam("media_id") String mediaId);
    

	@PostMapping(path="/media/uploadimg", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	UploadResponse uploadimg(AccessTokenInfo accessToken, @FieldName("media") Resource media);
	

    @GetMapping(path = "/media/get/jssdk")
	@ResponseHandler(ByteArrayResponseHandler.class)
    ByteArrayResource getJssdk(AccessTokenInfo accessToken, @RequestParam("media_id") String mediaId);
    
}

