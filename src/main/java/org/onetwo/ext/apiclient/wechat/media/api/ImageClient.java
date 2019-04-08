package org.onetwo.ext.apiclient.wechat.media.api;

import org.onetwo.common.utils.FieldName;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.media.response.UploadResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 文档： https://mp.weixin.qq.com/wiki?action=doc&id=mp1444378120&t=0.5452199142891914
 * @author wayshall
 * <br/>
 */
@WechatApiClient
public interface ImageClient {
	
	@PostMapping(path="/media/uploadimg", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	UploadResponse uploadimg(AccessTokenInfo accessToken, /*@RequestBody*/@FieldName("buffer") Resource buffer);

}
