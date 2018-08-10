package org.onetwo.ext.apiclient.wechat.media.api;

import org.onetwo.common.apiclient.annotation.ResponseHandler;
import org.onetwo.common.utils.FieldName;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.handler.ByteArrayResponseHandler;
import org.onetwo.ext.apiclient.wechat.material.response.UploadNewsResponse;
import org.onetwo.ext.apiclient.wechat.media.request.AddNewsRequest;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MediaTypes;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * api地址：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1481187827_i0l21
 * 
 * @author wayshall
 * <br/>
 */
@WechatApiClient
public interface MediaClient extends ImageClient {
	
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
     * @author wayshall
     * @param accessToken
     * @param mediaId
     * @return
     */
    @GetMapping(path = "/media/get", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseHandler(ByteArrayResponseHandler.class)
    ByteArrayResource get(AccessTokenInfo accessToken, @RequestParam("media_id") String mediaId);

}
