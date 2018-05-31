package org.onetwo.ext.apiclient.wechat.media.api;

import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.media.response.UploadResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wayshall
 * <br/>
 */
@WechatApiClient
public interface ImageClient {
	
	@PostMapping(path="/media/uploadimg?access_token={accessToken}", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	UploadResponse upload(@PathVariable("accessToken") String accessToken, @RequestBody Resource buffer);

}
