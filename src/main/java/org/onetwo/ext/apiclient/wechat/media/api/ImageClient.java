package org.onetwo.ext.apiclient.wechat.media.api;

import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.core.WechatRequestConfig;
import org.onetwo.ext.apiclient.wechat.media.response.UploadResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author wayshall
 * <br/>
 */
@WechatApiClient
public interface ImageClient {
	
	@PostMapping(path="/media/uploadimg", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	@WechatRequestConfig(accessToken=true)
	UploadResponse upload(Resource buffer);

}
