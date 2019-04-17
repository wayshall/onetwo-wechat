package org.onetwo.ext.apiclient.work.contact.api;

import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.work.contact.response.TagListResponse;
import org.onetwo.ext.apiclient.work.contact.response.TagMemberResponse;
import org.onetwo.ext.apiclient.work.core.WorkWechatApiClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author weishao zeng
 * <br/>
 */
@WorkWechatApiClient
public interface WorkTagClient {

	@GetMapping(path="/tag/list")
	TagListResponse getList(AccessTokenInfo accessTokenInfo);

	@GetMapping(path="/tag/get")
	TagMemberResponse get(AccessTokenInfo accessTokenInfo, Long tagid);
}

